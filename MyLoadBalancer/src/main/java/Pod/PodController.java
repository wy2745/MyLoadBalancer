package Pod;

import java.util.Map;
import java.util.TreeMap;

public class PodController {

    private Map<String, Pod> podSet;

    private String           funcPrefix = "pod,podstatus-controller: ";

    private void init() {
        podSet = new TreeMap<String, Pod>();
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

    public Pod createPod(String podName, String address, int port, int cpuCapacity,
                         int memCapacity) {
        Pod pod = podSet.get(podName);
        if (pod != null) {
            printmsg("这个pod已存在");
            return pod;
        }
        pod = new Pod(address, port, podName, cpuCapacity, memCapacity);
        podSet.put(podName, pod);
        printmsg("成功创建");
        return pod;
    }

    public double getCpuUsage() {
        double cpuUsage = 0;
        for (Pod pod : podSet.values()) {
            cpuUsage += (1 - pod.getCpuAvailable() / pod.getCpuCapacity());
        }
        return cpuUsage / podSet.size();
    }

    public double getMenUsage() {
        double memUsage = 0;
        for (Pod pod : podSet.values()) {
            memUsage += (1 - pod.getMemAvailable() / pod.getMemCapacity());
        }
        return memUsage / podSet.size();
    }

    public boolean deletePod(String podName) {
        if (podSet.containsKey(podName))
            podSet.remove(podName);
        else
            printmsg("找不到对应的pod");
        return true;
    }

    public Map<String, Pod> getPodSet() {
        return podSet;
    }

    public void clean() {
        podSet.clear();
    }

}
