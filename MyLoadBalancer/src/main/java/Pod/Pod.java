package Pod;

public class Pod {
    private String address;

    private int    port;

    private String podName;

    public Pod(String address, int port, String podName) {
        this.address = address;
        this.port = port;
        this.podName = podName;
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

}
