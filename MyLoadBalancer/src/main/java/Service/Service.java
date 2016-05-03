package Service;

import java.util.Map;
import java.util.TreeMap;

//
public class Service {

    private String               ip;

    private int                  port;

    private String               svcName;

    private Map<String, Integer> pods;

    private void init(String ip, int port, String svcName) {
        this.ip = ip;
        this.port = port;
        this.svcName = svcName;
        this.pods = new TreeMap<String, Integer>();
    }

    public Service(String ip, int port, String svcName) {
        init(ip, port, svcName);
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

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public Map<String, Integer> getPods() {
        return pods;
    }

    public void setPods(Map<String, Integer> pods) {
        this.pods = pods;
    }

}
