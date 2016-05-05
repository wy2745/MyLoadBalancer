package Request;

public class Request {

    private int    requestId;

    private String ip;

    private int    port;

    private String svcName;

    private String path;

    private int    status;

    private double requiredCpu;

    private double requiredMem;

    private int    requireTime;

    private int    handleTime;

    public Request(String ip, int port, String path, String svcName) {
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.svcName = svcName;
    }

    public void setRequestLog(RequestLog requestLog) {
        this.requiredCpu = requestLog.getRequiredCpu();
        this.requiredMem = requestLog.getRequiredMem();
        this.requireTime = requestLog.getRequiredTime();
    }

    public void startHandling() {
        if (this.status != Util.constrain.pending)
            return;
        this.status = Util.constrain.handling;
        this.handleTime = 0;
    }

    public void process() {
        if (this.status != Util.constrain.handling)
            return;
        this.handleTime += 1;
        if (this.handleTime == this.requireTime)
            this.status = Util.constrain.finished;
    }

    public boolean forwarding() {
        return (this.status == Util.constrain.forwarding);
    }

    public void setforwarding() {
        this.status = Util.constrain.forwarding;
    }

    public boolean pending() {
        return (this.status == Util.constrain.pending);
    }

    public void setpending() {
        this.status = Util.constrain.pending;
    }

    public boolean handling() {
        return (this.status == Util.constrain.handling);
    }

    public void sethandling() {
        this.status = Util.constrain.handling;
    }

    public boolean finish() {
        return (this.status == Util.constrain.finished);
    }

    public void setfinish() {
        this.status = Util.constrain.finished;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }

    public String getSvcName() {
        return svcName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public double getRequiredCpu() {
        return requiredCpu;
    }

    public void setRequiredCpu(double requiredCpu) {
        this.requiredCpu = requiredCpu;
    }

    public double getRequiredMem() {
        return requiredMem;
    }

    public void setRequiredMem(double requiredMem) {
        this.requiredMem = requiredMem;
    }

    public int getRequireTime() {
        return requireTime;
    }

    public void setRequireTime(int requireTime) {
        this.requireTime = requireTime;
    }

    public int getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(int handleTime) {
        this.handleTime = handleTime;
    }

}
