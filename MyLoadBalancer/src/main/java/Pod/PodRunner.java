package Pod;

import java.util.Timer;
import java.util.TimerTask;

public class PodRunner extends TimerTask {

    private Pod pod;

    public PodRunner(Pod pod) {
        this.pod = pod;
    }

    public void run() {
        pod.test();
        new Timer().schedule(new PodRunner(pod), 1000);
    }

}
