package com.news.model;

import java.sql.Timestamp;
import java.util.List;

public class NewsService {

    private NewsDAO dao;

    public NewsService() {
        dao = new NewsDAOImpl();
    }

    public NewsVO addNews(Integer newsID, Integer administratorID, String newsTitle, String newsContent, Integer newsStatus, Timestamp newsCreateTime) {
        NewsVO newsVO = new NewsVO();
        newsVO.setNewsID(newsID);
        newsVO.setAdministratorID(administratorID);
        newsVO.setNewsTitle(newsTitle);
        newsVO.setNewsContent(newsContent);
        newsVO.setNewsStatus(newsStatus);
        newsVO.setNewsCreateTime(newsCreateTime);
        dao.insert(newsVO);
        return newsVO;
    }

    public NewsVO updateNews(Integer newsID, Integer administratorID, String newsTitle, String newsContent, Integer newsStatus) {
        NewsVO newsVO = new NewsVO();
        newsVO.setAdministratorID(administratorID);
        newsVO.setNewsTitle(newsTitle);
        newsVO.setNewsContent(newsContent);
        newsVO.setNewsStatus(newsStatus);
        newsVO.setNewsID(newsID);
        dao.update(newsVO);
        return newsVO;
    }

    public void deleteNews(Integer newsID) {dao.delete(newsID);}
    public NewsVO getNews(Integer newsID) {return dao.findByPK(newsID);}
    public List<NewsVO> getAllNews() {return dao.getAll();}
}
