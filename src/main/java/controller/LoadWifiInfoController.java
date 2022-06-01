package controller;

import service.PublicWifiService;
import util.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadWifiInfoController extends HttpServlet {
    private final PublicWifiService publicWifiService = new PublicWifiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();

        if (publicWifiService.checkDBConnection()) {
            if (publicWifiService.checkWifiInfoExist()) {
                publicWifiService.deleteWifiInfo();
            }
            int request = publicWifiService.requestWifiApiService();
            map.put("response", request);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
        } else {
            map.put("response", -1);
        }
        resp.getWriter().write(Util.gson.toJson(map));
    }
}
