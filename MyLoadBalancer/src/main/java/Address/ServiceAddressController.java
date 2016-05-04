package Address;

import java.util.Map;
import java.util.TreeMap;

public class ServiceAddressController {

    //address的key是ip地址，value是该address对应的port信息的map，key是port，value是对应的service的svcName
    private Map<String, Map<Integer, String>> addressMap;

    private String                            funcPrefix = "Address-service:  ";

    private void init() {
        addressMap = new TreeMap<String, Map<Integer, String>>();
    }

    public ServiceAddressController() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public String findService(String ip, int port) {
        if (!addressMap.containsKey(ip) || !addressMap.get(ip).containsKey(port)) {
            printmsg("该地址的service不存在");
            return null;
        }
        return addressMap.get(ip).get(port);
    }

    public String createService(String ip, int port, String svcName) {
        Map<Integer, String> portMap = addressMap.get(ip);
        if (portMap == null) {
            portMap = new TreeMap<Integer, String>();
            portMap.put(port, svcName);
            addressMap.put(ip, portMap);
            printmsg("成功创建");
            return svcName;
        } else {
            String svc = portMap.get(port);
            if (svc == null) {
                portMap.put(port, svcName);
                svc = svcName;
            } else {
                printmsg("该地址的Service已存在");
            }
            return svc;
        }
    }

    public boolean deleteService(String ip, int port) {
        Map<Integer, String> portMap = addressMap.get(ip);
        if (portMap == null) {
            printmsg("该地址不存在该service");
            return false;
        }
        String svc = portMap.get(port);
        if (svc == null) {
            printmsg("该地址不存在该service");
            return false;
        }
        portMap.remove(port);
        printmsg("成功在该地址删除该service");
        return true;
    }

    public Map<String, Map<Integer, String>> getAddressMap() {
        return addressMap;
    }
}
