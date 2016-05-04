package Pod;

import java.util.Timer;
import java.util.TimerTask;

public class PodRunner extends TimerTask {

    private Pod pod;

    public PodRunner(Pod pod) {
        this.pod = pod;
    }

    public void run() {
        if (pod.isRunning()) {
            System.out.println("pod: " + pod.getPodName() + " is running");
            pod.process();
            new Timer().schedule(new PodRunner(pod), 1000);
        }
    }

}
