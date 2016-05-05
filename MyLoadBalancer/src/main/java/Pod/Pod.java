package Pod;

import java.util.ArrayList;
import java.util.List;

import Request.Request;

public class Pod {
    private String        address;

    private int           port;

    private String        podName;

    private List<Request> podRequests;

    private boolean       running;

    private double        cpuCapacity;

    private double        memCapacity;

    private double        cpuAvailable;

    private double        memAvailable;

    private double        cpuLoad;

    private double        memLoad;

    private String        funcPrefix = "pod:  ";

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public Pod(String address, int port, String podName, int cpuCapacity, int memCapacity) {
        this.address = address;
        this.port = port;
        this.podName = podName;
        this.cpuCapacity = this.cpuAvailable = cpuCapacity;
        this.memCapacity = this.memAvailable = memCapacity;
        this.running = false;
        this.cpuLoad = this.memLoad = 0;

        this.podRequests = new ArrayList<Request>();
    }

    public boolean receiveWaittingRequest(Request request) {
        if (!request.forwarding())
            return false;
        if (podRequests.contains(request)) {
            printmsg("request已被接收");
            return false;
        }
        request.setpending();
        printmsg("已接受request: " + request.getRequestId() + "到" + podName);
        podRequests.add(request);
        this.cpuLoad += request.getRequiredCpu();
        this.memLoad += request.getRequiredMem();
        return true;
    }

    private void receiveHandlingRequest(Request request) {
        if (!request.pending())
            return;
        if (podRequests.contains(request)) {
            //waittingrequests.remove(request);
            printmsg("requestId: " + request.getRequestId() + "开始运行于pod: " + this.podName);
            request.startHandling();
            this.cpuAvailable -= request.getRequiredCpu();
            this.memAvailable -= request.getRequiredMem();
            printmsg("开始处理requestId: " + request.getRequestId());
        }
    }

    private void receiveFinishRequest(Request request) {
        if (request.finish())
            printmsg("requestId: " + request.getRequestId() + "完成于pod: " + this.podName);
    }

    public void process() {
        if (this.running) {
            for (Request request : this.podRequests) {
                if (!request.handling())
                    continue;
                request.process();
                //                System.out.println("requestId: " + request.getRequestId() + ",handleTime:"
                //                                   + request.getHandleTime() + ",requiredTime: "
                //                                   + request.getRequireTime());
                if (request.finish()) {
                    reduceLoad(request.getRequiredCpu(), request.getRequiredMem());
                    receiveFinishRequest(request);
                }
            }
            for (Request request : this.podRequests) {
                if (!request.pending())
                    continue;
                //                System.out
                //                    .println("requestId: " + request.getRequestId() + ",cpu: "
                //                             + request.getRequiredCpu() + ",mem: " + request.getRequiredMem());
                //                System.out.println("PodName: " + this.podName + ",cpua: " + this.cpuAvailable
                //                                   + ",mema: " + this.memAvailable);
                if (candigest(request.getRequiredCpu(), request.getRequiredMem())) {
                    receiveHandlingRequest(request);
                }
            }
        }
    }

    private boolean candigest(double d, double e) {
        return this.cpuAvailable >= d && this.memAvailable >= e;
    }

    private void reduceLoad(double d, double e) {
        this.cpuAvailable += d;
        this.memAvailable += e;
        this.cpuLoad -= d;
        this.memLoad -= e;
    }

    public int choicePick1Scorer(Request request) {
        int score = 0;
        boolean cpuOk = (this.cpuLoad < this.cpuCapacity)
                        && (this.cpuCapacity - this.cpuLoad > request.getRequiredCpu());
        boolean memOk = (this.memLoad < this.memCapacity)
                        && (this.memCapacity - this.memLoad > request.getRequiredMem());
        if (cpuOk && memOk) {
            int cpuscore = (int) ((1 - (double) ((this.cpuLoad + request.getRequiredCpu())
                                                 / cpuCapacity))
                                  * 100);//最多100
            int memscore = (int) ((1 - (double) ((this.memLoad + request.getRequiredMem())
                                                 / memCapacity))
                                  * 100);//最多100
            return score - cpuscore - memscore;
        } else if (!cpuOk && memOk) {
            int cpuscore = (int) (((double) ((this.cpuLoad + request.getRequiredCpu()) / cpuLoad)
                                   - 1)
                                  * 5);
            int memscore = (int) ((1 - (double) ((this.memLoad + request.getRequiredMem())
                                                 / memCapacity))
                                  * 100);
            return score + cpuscore - memscore - 100;
        } else if (!memOk && cpuOk) {
            int memscore = (int) (((double) ((this.memLoad + request.getRequiredMem()) / memLoad)
                                   - 1)
                                  * 5);
            int cpuscore = (int) ((1 - (double) ((this.cpuLoad + request.getRequiredCpu())
                                                 / cpuCapacity))
                                  * 100);
            return score - cpuscore + memscore - 100;
        } else {
            int memscore = (int) (((double) ((this.memLoad + request.getRequiredMem()) / memLoad)
                                   - 1)
                                  * 5);
            int cpuscore = (int) (((double) ((this.cpuLoad + request.getRequiredCpu()) / cpuLoad)
                                   - 1)
                                  * 5);
            return score + cpuscore + memscore - 200;
        }
    }

    public void run() {
        this.running = true;
        printmsg(podName + "is running");
    }

    public void stop() {
        this.running = false;
        printmsg(podName + "is stoped!");
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getPodName() {
        return podName;
    }

    public boolean isRunning() {
        return running;
    }

    public List<Request> getPodRequests() {
        return podRequests;
    }

    public double getCpuCapacity() {
        return cpuCapacity;
    }

    public double getMemCapacity() {
        return memCapacity;
    }

    public double getCpuAvailable() {
        return cpuAvailable;
    }

    public double getMemAvailable() {
        return memAvailable;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public double getMemLoad() {
        return memLoad;
    }

    public String getFuncPrefix() {
        return funcPrefix;
    }

}
