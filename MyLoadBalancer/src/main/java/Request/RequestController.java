package Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Util.constrain;

public class RequestController {
    //key:svcName,value:svcName对应的requestMap(key:path,value:path对应的requestLog)
    private Map<String, Map<String, RequestLog>> requestsLogMap;

    private List<Request>                        requests;

    private String                               funcPrefix = "request-controller: ";

    private void init() {
        requestsLogMap = new TreeMap<String, Map<String, RequestLog>>();
        requests = new ArrayList<Request>();
    }

    public RequestController() {
        init();
    }

    private void printmsg(String msg) {
        System.out.println(funcPrefix + msg);
    }

    public List<Request> findAllRequest() {
        return this.requests;
    }

    public Map<String, Map<String, RequestLog>> findAllRequestLog() {
        return this.requestsLogMap;
    }

    public Request findRequest(int requestId) {
        if (requestId < requests.size())
            return requests.get(requestId);
        printmsg("不存在该request");
        return null;
    }

    public RequestLog findRequestLog(String svcName, String path) {
        if (!requestsLogMap.containsKey(svcName)
            || !requestsLogMap.get(svcName).containsKey(path)) {
            printmsg("不存在对应的requestLog");
            return null;
        }
        return requestsLogMap.get(svcName).get(path);
    }

    public Request createRequest(String ip, int port, String svcName, String path) {
        RequestLog requestLog = findRequestLog(svcName, path);
        if (requestLog == null) {
            printmsg("不存在该类request的requestLog");
            return null;
        }
        Request request = new Request(ip, port, path, svcName);
        request.setRequestLog(requestLog);
        request.setRequestId(this.requests.size());
        request.setStatus(constrain.forwarding);
        this.requests.add(request);
        return request;
    }

    public RequestLog createRequestLog(String svcName, String path, int requiredCpu,
                                       int requiredMem, int requiredTime) {
        Map<String, RequestLog> pathLogMap = requestsLogMap.get(svcName);
        if (pathLogMap == null) {
            pathLogMap = new TreeMap<String, RequestLog>();
            printmsg("正在创建新的pathLogMap");
            RequestLog requestLog = new RequestLog(requiredCpu, requiredMem, requiredTime);
            printmsg("正在创建新的requestLog");
            pathLogMap.put(path, requestLog);
            requestsLogMap.put(svcName, pathLogMap);
            return requestLog;
        }
        if (!pathLogMap.containsKey(path)) {
            printmsg("正在创建新的requestLog");
            RequestLog requestLog = new RequestLog(requiredCpu, requiredMem, requiredTime);
            requestsLogMap.get(svcName).put(path, requestLog);
            return requestLog;
        }
        printmsg("requestLog已存在");
        return pathLogMap.get(path);
    }

    public boolean deleteRequestLog(String svcName, String path) {
        if (!requestsLogMap.containsKey(svcName)
            || !requestsLogMap.get(svcName).containsKey(path)) {
            printmsg("不存在对应的requestLog");
            return true;
        }
        requestsLogMap.get(svcName).remove(path);
        return true;
    }

    public Map<String, Map<String, RequestLog>> getRequestsLog() {
        return requestsLogMap;
    }

    public void setRequestsLog(Map<String, Map<String, RequestLog>> requestsLog) {
        requestsLogMap = requestsLog;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}
