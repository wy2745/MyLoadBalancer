package Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//podMap是用appName作为key，value为appName对应的podName集
public class Service {

    private String                    ip;

    private int                       port;

    private String                    svcName;

    private Map<String, List<String>> podMap;

    private void init(String ip, int port, String svcName) {
        this.ip = ip;
        this.port = port;
        this.svcName = svcName;
        this.podMap = new TreeMap<String, List<String>>();
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

    public Map<String, List<String>> getPodMap() {
        return podMap;
    }

    public void setPodMap(Map<String, List<String>> podMap) {
        this.podMap = podMap;
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

}
