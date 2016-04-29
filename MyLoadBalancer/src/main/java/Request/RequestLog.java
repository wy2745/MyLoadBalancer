package Request;

//在外部使用的时候，需要和map结合，svcReqMap的key为appName(String)，value为pathReqLogMap，该map的key是path(String),value是requestLog
public class RequestLog {

    private int requiredCpu;

    private int requiredMem;

    private int requireTime;

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
