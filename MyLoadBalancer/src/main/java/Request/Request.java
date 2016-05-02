package Request;

public class Request {

    private int    requestId;

    private String ip;

    private int    port;

    private String svcName;

    private String path;

    private int    status;

    private int    requiredCpu;

    private int    requiredMem;

    private int    requireTime;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getRequiredCpu() {
        return requiredCpu;
    }

    public void setRequiredCpu(int requiredCpu) {
        this.requiredCpu = requiredCpu;
    }

    public int getRequiredMem() {
        return requiredMem;
    }

    public void setRequiredMem(int requiredMem) {
        this.requiredMem = requiredMem;
    }

    public int getRequireTime() {
        return requireTime;
    }

    public void setRequireTime(int requireTime) {
        this.requireTime = requireTime;
    }

}
