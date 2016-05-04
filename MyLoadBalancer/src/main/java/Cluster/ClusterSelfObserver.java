package Cluster;

import java.util.Timer;
import java.util.TimerTask;

public class ClusterSelfObserver extends TimerTask {

    private ClusterManager clusterManager;

    public ClusterSelfObserver(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    @Override
    public void run() {
        System.out.println("cluster self observing...");
        if (this.clusterManager.whetherselfObserve()) {
            if (this.clusterManager.startObserveRequest())
                this.clusterManager.selfObserveStop();
            new Timer().schedule(new ClusterSelfObserver(clusterManager), 1000);
        }

    }

}
