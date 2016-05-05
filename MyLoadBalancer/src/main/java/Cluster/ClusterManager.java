package Cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Algorithm.Request2PodMatch;
import Pod.Pod;
import Pod.PodManager;
import Pod.PodRunnable;
import Request.Request;
import Request.RequestController;
import Request.RequestLog;
import Service.Service;
import Service.ServiceManager;

public class ClusterManager {
    private PodManager                     podManager;

    private RequestController              requestController;

    private ServiceManager                 serviceManager;

    private boolean                        selfObserve;

    private List<ScheduledExecutorService> podRunner;

    private Request2PodMatch               request2PodMatch;

    private String                         funcPrefix = "cluster-controller:  ";

    private void init() {
        podManager = new PodManager();
        requestController = new RequestController();
        serviceManager = new ServiceManager();
        podRunner = new ArrayList<ScheduledExecutorService>();
        request2PodMatch = new Request2PodMatch();
        selfObserve = false;
    }

    public void ListService() {
        printmsg("所有service:");
        Map<String, Service> serviceset = serviceManager.findAllService();
        for (String svcName : serviceset.keySet()) {
            Service service = serviceset.get(svcName);
            System.out.println("service name: " + svcName + ", address: " + service.getIp() + ":"
                               + service.getPort());
            Map<String, Pod> podMap = service.getPods();
            for (Pod pod : podMap.values()) {
                System.out.println("    pod:" + pod.getPodName());
            }
        }
    }

    public void ListPod() {
        printmsg("所有pod:");
        Map<String, Pod> podset = podManager.findAllPod();
        for (Pod pod : podset.values()) {
            System.out.println("podName: " + pod.getPodName() + ",address: " + pod.getAddress()
                               + ":" + pod.getPort() + ",cpuStatus: " + pod.getCpuAvailable()
                               + ",memStatus: " + pod.getMemAvailable() + ",cpuCapacity: "
                               + pod.getCpuCapacity() + ",memCapacity: " + pod.getMemCapacity());
        }
    }

    public void ListPodUsage() {
        Map<String, Pod> podset = podManager.findAllPod();
        double cpuUsage = 0;
        double memUsage = 0;
        System.out.println("Pod资源使用率:\n\n");
        for (Pod pod : podset.values()) {
            System.out.println("podName: " + pod.getPodName() + ",cpuStatus: "
                               + pod.getCpuAvailable() + ",memStatus: " + pod.getMemAvailable()
                               + ",cpuCapacity: " + pod.getCpuCapacity() + ",memCapacity: "
                               + pod.getMemCapacity() + ",cpuUsage: "
                               + (1 - (double) pod.getCpuAvailable() / pod.getCpuCapacity())
                               + ",memUsage: "
                               + (1 - (double) pod.getMemAvailable() / pod.getMemCapacity()));
            cpuUsage += (1 - (double) pod.getCpuAvailable() / pod.getCpuCapacity());
            memUsage += (1 - (double) pod.getMemAvailable() / pod.getMemCapacity());
        }
        cpuUsage /= podset.size();
        memUsage /= podset.size();
        System.out.println("\n\n总体资源使用率: Cpu: " + cpuUsage + ",Mem: " + memUsage);
    }

    public void ListRequest() {
        printmsg("所有request:");
        List<Request> requests = requestController.findAllRequest();
        for (Request request : requests) {
            System.out
                .println("requestId: " + request.getRequestId() + ",address: " + request.getIp()
                         + ":" + request.getPort() + ",path: " + request.getPath()
                         + ",requiredCpu: " + request.getRequiredCpu() + ",requiredMem: "
                         + request.getRequiredMem() + ",requiredTime: " + request.getRequireTime());
        }
    }

    public void ListRequestLog() {
        printmsg("所有requestLog: ");
        Map<String, Map<String, RequestLog>> requestLogsMap = requestController.findAllRequestLog();
        for (String svcName : requestLogsMap.keySet()) {
            System.out.println("Service-" + svcName + ": ");
            Map<String, RequestLog> pathLogMap = requestLogsMap.get(svcName);
            for (String path : pathLogMap.keySet()) {
                RequestLog requestLog = pathLogMap.get(path);
                System.out
                    .println("    path:" + path + ",requiredCpu: " + requestLog.getRequiredCpu()
                             + ",requiredMem: " + requestLog.getRequiredMem() + ",requiredTime: "
                             + requestLog.getRequiredTime());
            }
        }
    }

    public Service createService(String svcName, String ip, int port) {
        return serviceManager.createService(svcName, ip, port);
    }

    public Pod createPod(String podName, String address, int port, int cpuCapacity,
                         int memCapacity) {
        return podManager.createPod(podName, address, port, cpuCapacity, memCapacity);
    }

    public boolean addPodToService(String svcName, String podName) {
        if (serviceManager.findServiceByName(svcName) != null
            && podManager.findPodByName(podName) != null)
            return serviceManager.addPodToService(svcName, podManager.findPodByName(podName));
        printmsg("pod或者service不存在");
        return false;
    }

    public boolean deletePodFromService(String svcName, String podName) {
        if (serviceManager.findServiceByName(svcName) != null
            && podManager.findPodByName(podName) != null)
            return serviceManager.deletePodFromService(svcName, podName);
        printmsg("pod或者service不存在");
        return false;
    }

    public void assignRequestToPod(Request request, String podName) {
        podManager.findPodByName(podName).receiveWaittingRequest(request);
    }

    public Request createRequest(String ip, int port, String path) {
        Service service = serviceManager.findServiceByAddress(ip, port);
        if (service == null) {
            printmsg("service不存在");
            return null;
        }
        return requestController.createRequest(ip, port, service.getSvcName(), path);
    }

    public RequestLog createRequestLog(String svcName, String path, int requiredCpu,
                                       int requiredMem, int requiredTime) {
        if (serviceManager.findServiceByName(svcName) != null)
            return requestController.createRequestLog(svcName, path, requiredCpu, requiredMem,
                requiredTime);
        printmsg("service不存在");
        return null;
    }

    public void scheduleRequest(String mode) {
        if (mode.equals("random")) {
            for (Request request : requestController.getRequests()) {
                Service service = serviceManager.findServiceByAddress(request.getIp(),
                    request.getPort());
                if (service == null) {
                    printmsg("找不到对应的service");
                    continue;
                }
                assignRequestToPod(request,
                    request2PodMatch.randomPick(request, service.getPods()));
            }
        } else {
            for (Request request : requestController.getRequests()) {
                Service service = serviceManager.findServiceByAddress(request.getIp(),
                    request.getPort());
                if (service == null) {
                    printmsg("找不到对应的service");
                    continue;
                }
                assignRequestToPod(request,
                    request2PodMatch.choicePick1(request, service.getPods()));
            }
        }
    }

    public void podRun(String podName) {
        podManager.findPodByName(podName).run();
    }

    public void podStop(String podName) {
        podManager.findPodByName(podName).stop();
    }

    public void createPodRunner(String podName) {
        Pod pod = podManager.findPodByName(podName);
        if (pod == null)
            return;
        //        new Timer().schedule(new PodRunner(pod), 1000);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        PodRunnable task = new PodRunnable(pod);
        //1秒后开始执行任务，以后每隔2秒执行一次
        executorService.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MILLISECONDS);
        podRunner.add(executorService);
    }

    public boolean startObserveRequest() {
        boolean finish = this.requestController.observerRequest();
        //        if (finish == false)
        //            ListPodUsage();
        if (finish == true) {
            for (ScheduledExecutorService scheduledExecutorService : podRunner) {
                scheduledExecutorService.shutdown();
            }
            clean();
        }
        return finish;

    }

    public void selfObserveRun() {
        this.selfObserve = true;
        requestController.startObserve();
    }

    public void selfObserveStop() {
        this.selfObserve = false;
        requestController.stopObserver();
    }

    public boolean whetherselfObserve() {
        return this.selfObserve;
    }

    public void startSelfObserve() {
        if (selfObserve) {
            //new Timer().schedule(new ClusterSelfObserver(this), 1000);
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
            ClusterSelfObRunnable task = new ClusterSelfObRunnable(this);
            //1秒后开始执行任务，以后每隔2秒执行一次
            executorService.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MILLISECONDS);
            podRunner.add(executorService);
        }
    }

    public ClusterManager() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    private void clean() {
        podRunner.clear();
        this.selfObserve = false;
        podManager.clean();
        serviceManager.clean();
        requestController.clean();
        printmsg("所有内容清理完毕");
    }

    public void serfunc(String string) {
        funcPrefix += string;
    }

}
