package Algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Pod.Pod;
import Request.Request;

public class Request2PodMatch {

    private int rate1 = 1;

    private int rate2 = 3;

    private int generateRanNum(int max) {
        Random random = new Random();
        int start = random.nextInt(max);
        return (random.nextInt(max) + start) % max;
    }

    private List<Integer> generateRanNumLst(int max, int num) {
        List<Integer> numList = new ArrayList<Integer>();
        Random random = new Random();
        int start = random.nextInt(max);
        while (numList.size() < num) {
            int target = (random.nextInt(max) + start) % max;
            if (numList.contains(target))
                continue;
            numList.add(target);
        }
        return numList;
    }

    public String randomPick(Request request, Map<String, Pod> podMap) {

        int max = podMap.size();
        int target = generateRanNum(max);
        int i = 0;
        for (Pod pod : podMap.values()) {
            if (i == target)
                return pod.getPodName();
            i++;
        }
        return null;
    }

    //选择pod中匹配后剩余资源最少的pod，这样来确保优秀pod的请求处理能力
    public String choicePick1(Request request, Map<String, Pod> podMap) {
        List<Integer> numLst = generateRanNumLst(podMap.size(),
            (int) (this.rate1 * podMap.size()) / this.rate2);
        int highestScore = -10000;
        String podName = "";
        for (int num : numLst) {
            int i = 0;
            for (Pod pod : podMap.values()) {
                if (num == i) {
                    int score = pod.choicePick1Scorer(request);
                    if (highestScore < score) {
                        highestScore = score;
                        podName = pod.getPodName();
                    }
                }
                i++;
            }
        }
        return podName;
    }

    public String choicePick2(Request request, Map<String, Pod> podMap) {
        List<Integer> numLst = generateRanNumLst(podMap.size(),
            (int) (this.rate1 * podMap.size()) / this.rate2);
        int highestScore = -10000;
        String podName = "";
        for (int num : numLst) {
            int i = 0;
            for (Pod pod : podMap.values()) {
                if (num == i) {
                    int score = pod.choicePick2Scorer(request);
                    if (highestScore < score) {
                        highestScore = score;
                        podName = pod.getPodName();
                    }
                }
                i++;
            }
        }
        return podName;
    }
}
