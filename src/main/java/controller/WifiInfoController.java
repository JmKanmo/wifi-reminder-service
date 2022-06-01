package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.WifiInfo;
import service.PublicWifiService;
import util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WifiInfoController extends HttpServlet {
    private final PublicWifiService publicWifiService = new PublicWifiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Double xPos = Double.parseDouble(req.getParameter("xPos"));
            Double yPos = Double.parseDouble(req.getParameter("yPos"));
            Integer offSet = Integer.parseInt(req.getParameter("offSet"));
            Integer cnt = Integer.parseInt(req.getParameter("cnt"));

            // Connection Pool 을 통해 Connection 가져올 때, 예외 발생(db 연결 close 되었거나, etc), db에 저장 된 데이터 개수가 0개일 경우, API 직접 요청, 필터링 후 데이터 반환
            List<WifiInfo> wifiInfoList = publicWifiService.searchWifiNearestInfosByDB(
                    xPos == null ? 0.f : xPos,
                    yPos == null ? 0.f : yPos,
                    offSet == null ? 0 : offSet,
                    cnt == null ? 0 : 20);

            if (wifiInfoList.isEmpty()) {
                // API 직접 요청
                wifiInfoList = publicWifiService.searchWifiNearestInfosDirectly(
                        xPos == null ? 0.f : xPos,
                        yPos == null ? 0.f : yPos,
                        offSet == null ? 0 : offSet,
                        cnt == null ? 0 : 20
                );
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(Util.gson.toJson(wifiInfoList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
