package GraduatePro.MyLoadBalancer;

import java.util.Random;
import java.util.Scanner;

import Cluster.ClusterManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while (!str.equals("q")) {
            str = sc.nextLine();
            ClusterManager clusterManager = new ClusterManager();
            ClusterManager clusterManager2 = new ClusterManager();
            ClusterManager clusterManager3 = new ClusterManager();
            clusterManager.createService("service1", "101", 11);
            clusterManager2.createService("service1", "101", 11);
            clusterManager3.createService("service1", "101", 11);
            Random random = new Random();
            int j = 0;
            while (j < 10) {

                int cpu = random.nextInt(10) + 1;
                int mem = random.nextInt(10) + 1;
                int time = random.nextInt(10) + 1 + 20;
                clusterManager.createRequestLog("service1", "/user" + String.valueOf(j), cpu, mem,
                    time);
                clusterManager2.createRequestLog("service1", "/user" + String.valueOf(j), cpu, mem,
                    time);
                clusterManager3.createRequestLog("service1", "/user" + String.valueOf(j), cpu, mem,
                    time);
                j++;
            }
            j = 1;
            while (j < 10) {
                clusterManager.createPod("pod" + String.valueOf(j),
                    "110.110.110.11" + String.valueOf(j), 11, 12, 12);
                clusterManager.addPodToService("service1", "pod" + String.valueOf(j));
                clusterManager.createPodRunner("pod" + String.valueOf(j));
                clusterManager.podRun("pod" + String.valueOf(j));

                clusterManager2.createPod("pod" + String.valueOf(j),
                    "110.110.110.11" + String.valueOf(j), 11, 12, 12);
                clusterManager2.addPodToService("service1", "pod" + String.valueOf(j));
                clusterManager2.createPodRunner("pod" + String.valueOf(j));
                clusterManager2.podRun("pod" + String.valueOf(j));

                clusterManager3.createPod("pod" + String.valueOf(j),
                    "110.110.110.11" + String.valueOf(j), 11, 12, 12);
                clusterManager3.addPodToService("service1", "pod" + String.valueOf(j));
                clusterManager3.createPodRunner("pod" + String.valueOf(j));
                clusterManager3.podRun("pod" + String.valueOf(j));
                j++;
            }
            int i = 1;
            while (i < 2000) {
                clusterManager.createRequest("101", 11, "/user" + String.valueOf(i % 10));
                clusterManager2.createRequest("101", 11, "/user" + String.valueOf(i % 10));
                clusterManager3.createRequest("101", 11, "/user" + String.valueOf(i % 10));
                i++;
            }
            clusterManager.serfunc("choice1");
            clusterManager.selfObserveRun();
            clusterManager.startSelfObserve();
            clusterManager.scheduleRequest("choice1");

            str = sc.nextLine();

            clusterManager3.serfunc("choice2");
            clusterManager3.selfObserveRun();
            clusterManager3.startSelfObserve();
            clusterManager3.scheduleRequest("choice2");

            str = sc.nextLine();
            clusterManager2.serfunc("origin");
            clusterManager2.selfObserveRun();
            clusterManager2.startSelfObserve();
            clusterManager2.scheduleRequest("random");
            System.out.println("their");
        }
        //clusterManager.scheduleRequest("random");
        //        i = 1;
        //        while (i < 7) {
        //            if (i % 2 == 0)
        //                clusterManager.assignRequestToPod(requests.get(i - 1), "pod1");
        //            else
        //                clusterManager.assignRequestToPod(requests.get(i - 1), "pod2");
        //            i++;
        //        }

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
