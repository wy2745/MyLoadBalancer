package Service;

import java.util.Map;

import Pod.Pod;

//podMap是用appName作为key，value为appName对应的pod集
public class Service {

    private String             ip;

    private int                port;

    private Map<String, Pod[]> podMap;

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

    public Map<String, Pod[]> getPodMap() {
        return podMap;
    }

    public void setPodMap(Map<String, Pod[]> podMap) {
        this.podMap = podMap;
    }

}
