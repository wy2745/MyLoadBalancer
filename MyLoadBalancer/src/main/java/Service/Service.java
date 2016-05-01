package Service;

import java.util.List;
import java.util.Map;

//podMap是用appName作为key，value为appName对应的podName集
public class Service {

    private String                    ip;

    private int                       port;

    private Map<String, List<String>> podMap;

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

}
