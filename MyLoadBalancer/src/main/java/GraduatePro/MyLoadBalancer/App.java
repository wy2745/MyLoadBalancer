package GraduatePro.MyLoadBalancer;

import java.util.Scanner;

import Cluster.ClusterManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //        System.out.println( "Hello World!" );
        ClusterManager clusterManager = new ClusterManager();
        clusterManager.createPod("pod1", "110.110.110.110", 11, 12, 12);
        clusterManager.createPod("pod2", "110.110.110.110", 12, 12, 12);
        clusterManager.createPodRunner("pod1");
        clusterManager.createPodRunner("pod2");
        Scanner sc = new Scanner(System.in);

        String end = "q";
        String str = sc.nextLine();
        while (!str.equals(end)) {
            String podName = str;
            str = sc.nextLine();
            if (str == "1") {
                clusterManager.podRun(podName);
                clusterManager.ListPod();
            } else {
                clusterManager.podStop(podName);
                clusterManager.ListPod();
            }
            str = sc.nextLine();
        }
    }
}
