package GraduatePro.MyLoadBalancer;

import Cluster.ClusterManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //        System.out.println( "Hello World!" );
        ClusterManager clusterManager = new ClusterManager();
        clusterManager.createService("service1", "102.120.201", 12);
        clusterManager.ListService();
        clusterManager.createRequestLog("service1", "/user", 10, 10, 10);
        clusterManager.ListRequestLog();
        clusterManager.createRequest("102.120.201", 12, "/user");
        clusterManager.ListRequest();
        clusterManager.createPod("pod1", "110.110.110.110", 11, 12, 12);
        clusterManager.addPodToService("service1", "pod1");
        clusterManager.ListService();
        clusterManager.ListPod();
        clusterManager.deletePodFromService("service1", "pod1");
        clusterManager.ListService();
    }
}
