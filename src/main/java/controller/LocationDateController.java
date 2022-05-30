package controller;

import service.LocHistoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocationDateController extends HttpServlet {
    private final LocHistoryService locHistoryService = new LocHistoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO service 수행 후, 결과 리스트 값 반환
        // TODO 위치 히스토리 목록 jsp 뷰 반환
        System.out.println("hello world");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/location-history.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 내 위치 가져오기 후, 위치(x,y) 값 insert 로직 수행
    }
}
