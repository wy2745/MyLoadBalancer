package Request;

import java.util.Map;

public class RequestController {
    //key:appName,value:appName对应的requestMap(key:path,value:path对应的requestLog)
    private Map<String, Map<String, RequestLog>> Requests;

    public Map<String, Map<String, RequestLog>> getRequests() {
        return Requests;
    }

    public void setRequests(Map<String, Map<String, RequestLog>> requests) {
        Requests = requests;
    }

}
