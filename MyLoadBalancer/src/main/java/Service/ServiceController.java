package Service;

import java.util.Map;

public class ServiceController {
    //key:svcName,value:对应的service
    private Map<String, Service> Services;

    public Map<String, Service> getServices() {
        return Services;
    }

    public void setServices(Map<String, Service> services) {
        Services = services;
    }

}
