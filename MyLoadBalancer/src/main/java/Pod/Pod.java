package Pod;

import java.util.ArrayList;
import java.util.List;

import Request.Request;

public class Pod {
    private String        address;

    private int           port;

    private String        podName;

    private List<Request> waittingrequests;

    private List<Request> handlingRequest;

    private List<Request> finishedRequest;

    private boolean       running;

    private int           cpuCapacity;

    private int           memCapacity;

    private int           cpuAvailable;

    private int           memAvailable;

    private int           cpuLoad;

    private int           memLoad;

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

        waittingrequests = new ArrayList<Request>();
        handlingRequest = new ArrayList<Request>();
        finishedRequest = new ArrayList<Request>();
    }

    public boolean receiveWaittingRequest(Request request) {
        if (waittingrequests.contains(request)) {
            printmsg("request已被接收");
            return false;
        }
        request.setStatus(Util.constrain.pending);
        waittingrequests.add(request);
        this.cpuLoad += request.getRequiredCpu();
        this.memLoad += request.getRequiredMem();
        return true;
    }

    private void receiveHandlingRequest(Request request) {
        if (waittingrequests.contains(request)) {
            waittingrequests.remove(request);
            printmsg("requestId: " + request.getRequestId() + "开始运行于pod: " + this.podName);
            request.startHandling();
            this.handlingRequest.add(request);
            this.cpuAvailable -= request.getRequiredCpu();
            this.memAvailable -= request.getRequiredMem();
            printmsg("开始处理requestId: " + request.getRequestId());
        }
    }

    private void receiveFinishRequest(Request request) {
        if (handlingRequest.contains(request) && request.finish()) {
            handlingRequest.remove(request);
            printmsg("requestId: " + request.getRequestId() + "完成于pod: " + this.podName);
            this.finishedRequest.add(request);
        }
    }

    public void process() {
        if (this.running) {
            for (Request request : this.handlingRequest) {
                request.process();
                if (request.finish()) {
                    reduceLoad(request.getRequiredCpu(), request.getRequiredMem());
                    receiveFinishRequest(request);
                }
            }
            for (Request request : this.waittingrequests) {
                if (candigest(request.getRequiredCpu(), request.getRequiredMem()))
                    receiveHandlingRequest(request);
            }
        }
    }

    private boolean candigest(int cpu, int mem) {
        return this.cpuAvailable >= cpu && this.memAvailable >= mem;
    }

    private void reduceLoad(int cpu, int mem) {
        this.cpuAvailable += cpu;
        this.memAvailable += mem;
        this.cpuLoad -= cpu;
        this.memLoad -= mem;
    }

    public void run() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCpuCapacity() {
        return cpuCapacity;
    }

    public void setCpuCapacity(int cpuCapacity) {
        this.cpuCapacity = cpuCapacity;
    }

    public int getMemCapacity() {
        return memCapacity;
    }

    public void setMemCapacity(int memCapacity) {
        this.memCapacity = memCapacity;
    }

    public int getCpuAvailable() {
        return cpuAvailable;
    }

    public void setCpuAvailable(int cpuAvailable) {
        this.cpuAvailable = cpuAvailable;
    }

    public int getMemAvailable() {
        return memAvailable;
    }

    public void setMemAvailable(int memAvailable) {
        this.memAvailable = memAvailable;
    }

    public List<Request> getWaittingrequests() {
        return waittingrequests;
    }

    public void setWaittingrequests(List<Request> waittingrequests) {
        this.waittingrequests = waittingrequests;
    }

    public List<Request> getHandlingRequest() {
        return handlingRequest;
    }

    public void setHandlingRequest(List<Request> handlingRequest) {
        this.handlingRequest = handlingRequest;
    }

    public int getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(int cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public int getMemLoad() {
        return memLoad;
    }

    public void setMemLoad(int memLoad) {
        this.memLoad = memLoad;
    }

    public List<Request> getFinishedRequest() {
        return finishedRequest;
    }

    public void setFinishedRequest(List<Request> finishedRequest) {
        this.finishedRequest = finishedRequest;
    }

}
