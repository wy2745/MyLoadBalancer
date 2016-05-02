package Pod;

import java.util.Map;
import java.util.TreeMap;

public class PodController {

    //用于关联pod和它对应的pod状态
    private Map<String, PodStatus> podStatusMap;

    private Map<String, Pod>       podSet;

    private String                 funcPrefix = "pod,podstatus-controller: ";

    private void init() {
        podSet = new TreeMap<String, Pod>();
        podStatusMap = new TreeMap<String, PodStatus>();
    }

    public PodController() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public Pod findPod(String podName) {
        Pod pod = podSet.get(podName);
        if (pod == null)
            printmsg("找不到对应的pod");
        return pod;
    }

    public PodStatus findPodStatus(String podName) {
        PodStatus podStatus = podStatusMap.get(podName);
        if (podStatus == null)
            printmsg("找不到对应的pod状态");
        return podStatus;
    }

    public Pod createPod(String podName, String address, int port, int cpuCapacity,
                         int memCapacity) {
        Pod pod = podSet.get(podName);
        if (pod != null) {
            printmsg("这个pod已存在");
            return pod;
        }
        PodStatus podStatus = new PodStatus(cpuCapacity, memCapacity);
        pod = new Pod(address, port, podName);
        podStatusMap.put(podName, podStatus);
        podSet.put(podName, pod);
        printmsg("成功创建");
        return pod;
    }

    public boolean deletePod(String podName) {
        if (podSet.containsKey(podName))
            podSet.remove(podName);
        else
            printmsg("找不到对应的pod");
        if (podStatusMap.containsKey(podName))
            podStatusMap.remove(podName);
        else
            printmsg("找不到对应的pod状态");
        return true;
    }

    public Map<String, Pod> getPodSet() {
        return podSet;
    }

    public void setPodSet(Map<String, Pod> podSet) {
        this.podSet = podSet;
    }

    public Map<String, PodStatus> getPodStatusMap() {
        return podStatusMap;
    }

    public void setPodStatusMap(Map<String, PodStatus> podStatus) {
        this.podStatusMap = podStatus;
    }

}
