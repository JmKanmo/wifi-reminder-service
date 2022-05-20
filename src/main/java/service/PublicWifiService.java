package service;

import util.ConfigUtil;
import util.HttpUtil;
import util.ThreadUtil;

import java.util.concurrent.ExecutionException;

public class PublicWifiService {

    public void requestWifiApiService() {
        int workCount = ConfigUtil.getWorkConfig().getWorkCount();

        for (int i = 0, start = 1, end = 1000; i < workCount; i++, start += 1000, end += 1000) {
            final int finalStart = start;
            final int finalEnd = end;

            ThreadUtil.startWork(() -> {
                try {
                    // TODO JsoupParserUtil 클래스를 이용 + DB, 파일 ,etc 에 해당 정보(WifiInfo) 저장
                    return HttpUtil.sendHttpRequest(finalStart, finalEnd);
                } catch (Exception e) {
                    throw new ExecutionException(e);
                }
            });
        }
    }
}
