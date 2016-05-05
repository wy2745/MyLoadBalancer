package Cluster;

public class ClusterSelfObRunnable implements Runnable {

    private ClusterManager clusterManager;

    public ClusterSelfObRunnable(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    public void run() {
        System.out.println("cluster self observing...");
        if (this.clusterManager.whetherselfObserve()) {
            if (this.clusterManager.startObserveRequest())
                this.clusterManager.selfObserveStop();
            //new Timer().schedule(new ClusterSelfObserver(clusterManager), 1000);
        }

    }

}
