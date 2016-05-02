package Cluster;

import Pod.PodManager;
import Request.RequestController;
import Service.ServiceManager;

public class ClusterManager {
    private PodManager        podManager;

    private RequestController requestController;

    private ServiceManager    serviceManager;

    private String            funcPrefix = "cluster-controller:  ";

    private void init() {
        podManager = new PodManager();
        requestController = new RequestController();
        serviceManager = new ServiceManager();
    }

    public ClusterManager() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }
}
