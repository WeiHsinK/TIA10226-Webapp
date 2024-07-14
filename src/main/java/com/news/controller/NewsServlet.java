package com.news.controller;

import java.io.*;
import java.util.*;

//import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.news.model.*;
@WebServlet("/news/news.do")
public class NewsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {



        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if("getOne_For_Display".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            // Store this set in the request scope, in case we need to
            // send the ErrorPage view.
            req.setAttribute("errorMsgs", errorMsgs);

            String str = req.getParameter("newsID");

            if (str == null || (str.trim().length() == 0)) {
                errorMsgs.add("請輸入消息編號");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/news/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            Integer newsID = null;
            try {
                newsID = Integer.valueOf(str);
            }catch (Exception e){
                errorMsgs.add("消息編號格式不正確");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/news/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            NewsService newsSvc = new NewsService();
            NewsVO newsVO = newsSvc.getNews(newsID);
            if (newsVO == null){
                errorMsgs.add("查無資料");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/news/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            req.setAttribute("newsVO", newsVO);
            String url = "/news/listOneNews.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);

        }

        if ("getOne_For_Update".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer newsID = Integer.valueOf(req.getParameter("newsID"));

            NewsService newsSvc = new NewsService();
            NewsVO newsVO = newsSvc.getNews(newsID);

            req.setAttribute("newsVO", newsVO);
            String url = "/news/update_news_input.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("update".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer newsID = Integer.valueOf(req.getParameter("newsID"));
            Integer administratorID = Integer.valueOf(req.getParameter("administratorID"));

            String newsTitle = req.getParameter("newsTitle");
//            String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]$";
//            if (newsTitle == null || newsTitle.trim().length() == 0) {
//                errorMsgs.add("消息標題:請勿空白");
//            } else if (!newsTitle.trim().matches(newsTitleReg)) {
//                errorMsgs.add("消息標題:只能是中、英文字母、數字和_ ,");
//            }

            String newsContent = req.getParameter("newsContent").trim();
            if (newsContent == null || newsContent.length() == 0) {
                errorMsgs.add("內容請勿空白");
            }

            Integer newsStatus = Integer.valueOf(req.getParameter("newsStatus"));
            if (newsStatus == null || newsStatus.intValue() > 1) {
                errorMsgs.add("狀態只能 0:隱藏;1:正常");
            }

//            java.sql.Timestamp newsCreateTime = null;
////            try {
//                newsCreateTime = java.sql.Timestamp.valueOf(req.getParameter("newsCreateTime"));
//            }catch (IllegalArgumentException e){
//                newsCreateTime = new java.sql.Timestamp(System.currentTimeMillis());
//                errorMsgs.add("請輸入日期");
//            }

//            if(newsID != null){
//                req.setAttribute("newsVO", newsID);
//            }
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/news/update_news.jsp");
//            dispatcher.forward(req, res);

            NewsVO newsVO = new NewsVO();
            newsVO.setAdministratorID(administratorID);
            newsVO.setNewsTitle(newsTitle);
            newsVO.setNewsContent(newsContent);
            newsVO.setNewsStatus(newsStatus);
//            newsVO.setNewsCreateTime(newsCreateTime);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("newsVO", newsVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/news/update_news_input.jsp");
                failureView.forward(req, res);
                return;
            }

            NewsService newsSvc = new NewsService();
            newsVO = newsSvc.updateNews(newsID, administratorID, newsTitle, newsContent, newsStatus);

            req.setAttribute("newsVO", newsVO);
            String url = "/news/listOneNews.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("insert".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer newsID = Integer.valueOf(req.getParameter("newsID"));
//            String newsIDParam = req.getParameter("newsID");
//            if (newsIDParam == null || newsIDParam.isEmpty()) {
//                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "newsID parameter is missing or empty");
//                return;
//            }
//            try {
//                Integer newsID = Integer.valueOf(req.getParameter("newsID"));
//            }catch (NumberFormatException e){
//                res.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid newsID");
//                return;
//            }

            Integer administratorID = Integer.valueOf(req.getParameter("administratorID"));

            String newsTitle = req.getParameter("newsTitle").trim();
//            String newsTitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]$";
//            if (newsTitle == null || newsTitle.trim().length() == 0) {
//                errorMsgs.add("消息標題:請勿空白");
//            } else if (!newsTitle.trim().matches(newsTitleReg)) {
//                errorMsgs.add("消息標題:只能是中、英文字母、數字和_ ,");
//            }

            String newsContent = req.getParameter("newsContent").trim();
            if (newsContent == null || newsContent.trim().length() == 0) {
                errorMsgs.add("內容請勿空白");
            }


            Integer newsStatus = Integer.valueOf(req.getParameter("newsStatus").trim());
            if (newsStatus == null || newsStatus.intValue() > 1) {
                errorMsgs.add("狀態只能 0:隱藏;1:正常");
            }

            java.sql.Timestamp newsCreateTime = null;
//            try {
                newsCreateTime = java.sql.Timestamp.valueOf(req.getParameter("newsCreateTime").trim());
//            }catch (IllegalArgumentException e){
//                newsCreateTime = new java.sql.Timestamp(System.currentTimeMillis());
//                errorMsgs.add("請輸入日期");
//            }

            NewsVO newsVO = new NewsVO();
            newsVO.setNewsID(newsID);
            newsVO.setAdministratorID(administratorID);
            newsVO.setNewsTitle(newsTitle);
            newsVO.setNewsContent(newsContent);
            newsVO.setNewsStatus(newsStatus);
            newsVO.setNewsCreateTime(newsCreateTime);


            if (!errorMsgs.isEmpty()) {
                req.setAttribute("newsVO", newsVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/news/addNews.jsp");
                failureView.forward(req, res);
                return;
            }

            NewsService newsSvc = new NewsService();
            newsVO = newsSvc.addNews(newsID, administratorID, newsTitle, newsContent, newsStatus, newsCreateTime);

            String url = "/news/listAllNews.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("delete".equals(action)){
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Integer newsID = Integer.valueOf(req.getParameter("newsID"));

            NewsService newsSvc = new NewsService();
            newsSvc.deleteNews(newsID);

            String url = "/news/listAllNews.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }
    }
}
