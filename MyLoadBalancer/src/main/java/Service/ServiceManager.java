package Service;

import java.util.Map;

import Address.ServiceAddressController;

public class ServiceManager {

    private ServiceAddressController addressController;

    private ServiceController        serviceController;

    private void init() {
        addressController = new ServiceAddressController();
        serviceController = new ServiceController();
    }

    public ServiceManager() {
        init();
    }

    public Map<String, Service> findAllService() {
        return serviceController.getServices();
    }

    public Service findServiceByAddress(String ip, int port) {
        return serviceController.findService(addressController.findService(ip, port));
    }

    public Service findServiceByName(String svcName) {
        return serviceController.findService(svcName);
    }

    public Service createService(String svcName, String ip, int port) {
        addressController.createService(ip, port, svcName);
        Service svc = serviceController.createService(ip, port, svcName);
        return svc;
    }

    public boolean deleteService(String svcName) {
        Service svc = serviceController.findService(svcName);
        addressController.deleteService(svc.getIp(), svc.getPort());
        return serviceController.deleteService(svcName);
    }

    public boolean addPodToService(String svcName, String podName) {
        return serviceController.addPodToService(svcName, podName);
    }

    public boolean deletePodFromService(String svcName, String podName) {
        return serviceController.deletePodFromService(svcName, podName);
    }
}
