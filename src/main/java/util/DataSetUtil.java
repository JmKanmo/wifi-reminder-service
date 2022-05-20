package util;

import domain.WifiInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataSetUtil {
    private static final ConcurrentLinkedQueue<WifiInfo> queue = new ConcurrentLinkedQueue<>();

    public static void addWifiInfo(WifiInfo wifiInfo) {
        queue.offer(wifiInfo);
    }

    public static void addWifiInfo(List<WifiInfo> wifiInfoList) {
        for (WifiInfo wifiInfo : wifiInfoList) {
            queue.offer(wifiInfo);
        }
    }

    public static List<WifiInfo> clearAndGetTotalWifiInfoList() {
        List<WifiInfo> list = new ArrayList<>();

        while (queue.isEmpty() != true) {
            list.add(queue.poll());
        }
        return list;
    }

    public static int getSize() {
        return queue.size();
    }

    public static void clear() {
        queue.clear();
    }
}
