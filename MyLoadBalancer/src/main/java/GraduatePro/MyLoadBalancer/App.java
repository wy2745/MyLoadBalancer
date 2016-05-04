package GraduatePro.MyLoadBalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Cluster.ClusterManager;
import Request.Request;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ClusterManager clusterManager = new ClusterManager();
        clusterManager.createService("service1", "101", 11);
        clusterManager.createRequestLog("service1", "/user", 2, 2, 3);
        clusterManager.createPod("pod1", "110.110.110.110", 11, 12, 12);
        clusterManager.createPod("pod2", "110.110.110.112", 11, 12, 12);
        clusterManager.createPodRunner("pod1");
        clusterManager.podRun("pod1");
        clusterManager.createPodRunner("pod2");
        clusterManager.podRun("pod2");

        List<Request> requests = new ArrayList<Request>();

        int i = 1;
        while (i < 7) {
            Request request = clusterManager.createRequest("101", 11, "/user");
            requests.add(request);
            i++;
        }
        clusterManager.selfObserveRun();
        clusterManager.startSelfObserve();
        i = 1;
        while (i < 7) {
            if (i % 2 == 0)
                clusterManager.assignRequestToPod(requests.get(i - 1), "pod1");
            else
                clusterManager.assignRequestToPod(requests.get(i - 1), "pod2");
            i++;
        }
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        clusterManager.podStop("pod1");
        clusterManager.podStop("pod2");
        return;
        //        Scanner sc = new Scanner(System.in);
        //
        //        String end = "q";
        //        String str = sc.nextLine();
        //        while (!str.equals(end)) {
        //            Request request = clusterManager.createRequest("101", 11, "/user");
        //            if (str.equals("1"))
        //                clusterManager.assignRequestToPod(request, "pod1");
        //            else
        //                clusterManager.assignRequestToPod(request, "pod2");
        //            str = sc.nextLine();
        //        }

    }
}
