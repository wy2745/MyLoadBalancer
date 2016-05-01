package Pod;

import Address.AddressController;

public class PodManager {
    private AddressController addressController;

    private PodController     podController;

    public void init() {
        addressController = new AddressController();
        podController = new PodController();
    }

    public PodManager() {
        init();
    }

    public String findPodByAddress(String address, int port) {
        return addressController.findPod(address, port);
    }

    public Pod findPodByName(String podName) {
        return podController.findPod(podName);
    }

    public PodStatus findPodStatusByName(String podName) {
        return podController.findPodStatus(podName);
    }

    public Pod createPod(String podName, String appName, String address, int port, int cpuCapacity,
                         int memCapacity) {
        addressController.createPod(address, port, podName);
        Pod pod = podController.createPod(podName, appName, address, port, cpuCapacity,
            memCapacity);
        return pod;
    }

    public boolean deletePod(String podName) {
        Pod pod = podController.findPod(podName);
        addressController.deletePod(pod.getAddress(), pod.getPort());
        return podController.deletePod(podName);

    }
}
