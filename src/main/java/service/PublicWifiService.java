package service;

import com.google.gson.JsonObject;
import repository.SqliteDao;
import util.ConfigUtil;
import util.HttpUtil;
import util.JsonUtil;
import util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PublicWifiService {
    private final SqliteDao sqliteDao = new SqliteDao();

    public int requestWifiApiService() {
        try {
            Future<Integer> search = CompletableFuture.supplyAsync(() -> {
                List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
                int searchCount = 0;

                for (int i = 0, start = 1, end = 1000; i < ConfigUtil.getWorkConfig().getWorkCount(); i++, start += 1000, end += 1000) {
                    final int _start = start;
                    final int _end = end;

                    completableFutures.add(
                            CompletableFuture.supplyAsync(() -> {
                                        // Wifi 서비스 HTTP 요청 및 파싱
                                        try {
                                            String jsonStr = HttpUtil.sendHttpRequest(_start, _end);
                                            JsonObject jsonObject = JsonUtil.parseStrToJsonObject(jsonStr);
                                            return jsonObject;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    }, ThreadUtil.getThreadPoolExecutor()).thenApply(JsonUtil::parseJsonToWifiInfo)
                                    .thenCompose(future -> CompletableFuture.supplyAsync(() -> {
                                        // DB에 해당 List(WifiInfo) 정보 저장, 저장 횟수 반환
                                        if (future.isPresent()) {
                                            // TODO
                                            return sqliteDao.insertWifiInfos(future.get());
                                        }
                                        return 0;
                                    }, ThreadUtil.getThreadPoolExecutor())));
                }
                searchCount = completableFutures.stream().map(CompletableFuture::join).mapToInt(i -> i).sum();
                return searchCount;
            });
            return search.get(ConfigUtil.getWorkConfig().getTimeUnit(), TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkWifiInfoExist() {
        return sqliteDao.checkWifiInfoExist();
    }

    public int deleteWifiInfo() {
        return sqliteDao.deleteWifiInfo();
    }
}
