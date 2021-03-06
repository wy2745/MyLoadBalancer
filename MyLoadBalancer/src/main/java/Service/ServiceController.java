package Service;

import java.util.Map;
import java.util.TreeMap;

import Pod.Pod;

public class ServiceController {
    //key:svcName,value:对应的service
    private Map<String, Service> Services;

    private String               funcPrefix = "controller-service:  ";

    private void init() {
        Services = new TreeMap<String, Service>();
    }

    public ServiceController() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public Service findService(String svcName) {
        Service service = Services.get(svcName);
        if (service == null)
            printmsg("找不到对应的service");
        return service;
    }

    public Service createService(String ip, int port, String svcName) {
        Service svc = Services.get(svcName);
        if (svc != null) {
            printmsg("这个service已存在");
            return svc;
        }
        svc = new Service(ip, port, svcName);
        Services.put(svcName, svc);
        printmsg("成功创建");
        return svc;
    }

    public boolean deleteService(String svcName) {
        if (Services.containsKey(svcName))
            Services.remove(svcName);
        else
            printmsg("找不到对应的service");
        return true;
    }

    public boolean addPodToService(String svcName, Pod pod) {
        if (!Services.containsKey(svcName)) {
            printmsg("不存在该service");
            return false;
        }
        if (Services.get(svcName).getPods().containsKey(pod.getPodName())) {
            printmsg("该pod已经在service内");
            return false;
        }
        Services.get(svcName).getPods().put(pod.getPodName(), pod);
        return true;
    }

    public boolean deletePodFromService(String svcName, String podName) {
        if (!Services.containsKey(svcName)) {
            printmsg("不存在该service");
            return false;
        }
        if (!Services.get(svcName).getPods().containsKey(podName)) {
            printmsg("该pod不在service内");
            return false;
        }
        Services.get(svcName).getPods().remove(podName);
        return true;
    }

    public Map<String, Service> getServices() {
        return Services;
    }

    public Map<String, Pod> getPodBySvcName(String svcName) {
        Service service = Services.get(svcName);
        if (service == null) {
            printmsg("不存在相应的service");
            return null;
        }
        return service.getPods();
    }

    public void clean() {
        Services.clear();
    }
}
