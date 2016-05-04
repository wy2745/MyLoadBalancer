package Service;

import java.util.Map;
import java.util.TreeMap;

import Pod.Pod;

//
public class Service {

    private String           ip;

    private int              port;

    private String           svcName;

    private Map<String, Pod> pods;

    private void init(String ip, int port, String svcName) {
        this.ip = ip;
        this.port = port;
        this.svcName = svcName;
        this.pods = new TreeMap<String, Pod>();
    }

    public Service(String ip, int port, String svcName) {
        init(ip, port, svcName);
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getSvcName() {
        return svcName;
    }

    public Map<String, Pod> getPods() {
        return pods;
    }

}
