package Pod;

//和pod通过一个map<podName,podStatus>连接
public class PodStatus {
    private int cpuCapacity;

    private int memCapacity;

    private int cpuAvailable;

    private int memAvailable;

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

}
