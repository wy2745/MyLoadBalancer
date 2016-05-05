package Pod;

public class PodRunnable implements Runnable {

    private Pod pod;

    public PodRunnable(Pod pod) {
        this.pod = pod;
    }

    public void run() {
        try {
            if (pod.isRunning()) {
                //System.out.println("pod: " + pod.getPodName() + " is running");
                pod.process();
                //new Timer().schedule(new PodRunner(pod), 1000);
            }
        } catch (Throwable e) {
            // donothing
        }
    }

}
