package Cluster;

import java.util.List;
import java.util.Map;

import Pod.Pod;
import Pod.PodManager;
import Request.Request;
import Request.RequestController;
import Request.RequestLog;
import Service.Service;
import Service.ServiceManager;

public class ClusterManager {
    private PodManager        podManager;

    private RequestController requestController;

    private ServiceManager    serviceManager;

    private String            funcPrefix = "cluster-controller:  ";

    private void init() {
        podManager = new PodManager();
        requestController = new RequestController();
        serviceManager = new ServiceManager();
    }

    public void ListService() {
        printmsg("所有service:");
        Map<String, Service> serviceset = serviceManager.findAllService();
        for (String svcName : serviceset.keySet()) {
            Service service = serviceset.get(svcName);
            System.out.println("service name: " + svcName + ", address: " + service.getIp() + ":"
                               + service.getPort());
            Map<String, Integer> podMap = service.getPods();
            for (String podName : podMap.keySet()) {
                System.out.println("    pod:" + podName);
            }
        }
    }

    public void ListPod() {
        printmsg("所有pod:");
        Map<String, Pod> podset = podManager.findAllPod();
        for (String key : podset.keySet()) {
            Pod pod = podset.get(key);
            System.out.println("podName: " + pod.getPodName() + ",address: " + pod.getAddress()
                               + ":" + pod.getPort() + ",cpuStatus: " + pod.getCpuAvailable()
                               + ",memStatus: " + pod.getMemAvailable() + ",cpuCapacity: "
                               + pod.getCpuCapacity() + ",memCapacity: " + pod.getMemCapacity());
        }
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
            return serviceManager.addPodToService(svcName, podName);
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

    public ClusterManager() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

}
