package Pod;

import Address.PodAddressController;

public class PodManager {
    private PodAddressController addressController;

    private PodController        podController;

    private void init() {
        addressController = new PodAddressController();
        podController = new PodController();
    }

    public PodManager() {
        init();
    }

    public Pod findPodByAddress(String address, int port) {
        return podController.findPod(addressController.findPod(address, port));
    }

    public Pod findPodByName(String podName) {
        return podController.findPod(podName);
    }

    public PodStatus findPodStatusByName(String podName) {
        return podController.findPodStatus(podName);
    }

    public Pod createPod(String podName, String address, int port, int cpuCapacity,
                         int memCapacity) {
        addressController.createPod(address, port, podName);
        Pod pod = podController.createPod(podName, address, port, cpuCapacity, memCapacity);
        return pod;
    }

    public boolean deletePod(String podName) {
        Pod pod = podController.findPod(podName);
        addressController.deletePod(pod.getAddress(), pod.getPort());
        return podController.deletePod(podName);

    }
}
