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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/location-history.jsp");
        req.setAttribute("location_history", locHistoryService.getLocationHistories());
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        locHistoryService.insertLocHistoryInfo(Double.parseDouble(req.getParameter("xPos")),
                Double.parseDouble(req.getParameter("yPos")));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        locHistoryService.deleteLocHistoryInfo(Integer.parseInt(req.getParameter("id")));
    }
}
