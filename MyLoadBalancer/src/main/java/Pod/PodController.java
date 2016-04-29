package Pod;

import java.util.Map;

public class PodController {

    //用于关联pod和它对应的pod状态
    private Map<String, PodStatus> podStatus;

    public Map<String, PodStatus> getPodStatus() {
        return podStatus;
    }

    public void setPodStatus(Map<String, PodStatus> podStatus) {
        this.podStatus = podStatus;
    }

}
