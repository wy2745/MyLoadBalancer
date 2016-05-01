package Address;

import java.util.Map;
import java.util.TreeMap;

public class AddressController {
    //address的key是ip地址，value是该address对应的port信息的map，key是port，value是对应的pod的podname
    private Map<String, Map<Integer, String>> addressMap;

    private String                            funcPrefix = "Address-pod:  ";

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public void init() {
        addressMap = new TreeMap<String, Map<Integer, String>>();
    }

    public String findPod(String address, int port) {
        if (!addressMap.containsKey(address) || !addressMap.get(address).containsKey(port)) {
            printmsg("该地址的pod不存在");
            return null;
        }
        return addressMap.get(address).get(port);
    }

    public String createPod(String address, int port, String podName) {
        Map<Integer, String> portMap = addressMap.get(address);
        if (portMap == null) {
            portMap = new TreeMap<Integer, String>();
            portMap.put(port, podName);
            addressMap.put(address, portMap);
            printmsg("成功创建");
            return podName;
        } else {
            String pod = portMap.get(port);
            if (pod == null) {
                portMap.put(port, podName);
                pod = podName;
            } else {
                printmsg("该地址的Pod已存在");
            }
            return pod;
        }
    }

    public boolean deletePod(String address, int port) {
        Map<Integer, String> portMap = addressMap.get(address);
        if (portMap == null) {
            printmsg("该地址不存在该pod");
            return false;
        }
        String pod = portMap.get(port);
        if (pod == null) {
            printmsg("该地址不存在该pod");
            return false;
        }
        portMap.remove(port);
        printmsg("成功在该地址删除该pod");
        return true;
    }

    public Map<String, Map<Integer, String>> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<String, Map<Integer, String>> address) {
        this.addressMap = address;
    }

}
