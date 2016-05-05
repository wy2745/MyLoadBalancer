package Request;

//在外部使用的时候，需要和map结合，svcReqMap的key为appName(String)，value为pathReqLogMap，该map的key是path(String),value是requestLog
public class RequestLog {

    private double requiredCpu;

    private double requiredMem;

    private int    requiredTime;

    public RequestLog(int requiredCpu, int requiredMem, int requiredTime) {
        this.requiredCpu = requiredCpu;
        this.requiredMem = requiredMem;
        this.requiredTime = requiredTime;
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

    public int getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

}
