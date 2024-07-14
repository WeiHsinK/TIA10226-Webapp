package com.news.model;

import util.Util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl implements NewsDAO {
    private static final String INSERT =
            "insert into newsVO (newsID, administratorID, newsTitle, newsContent, newsStatus, newsCreateTime)values(?,?,?,?,?,?)";
    private static final String UPDATE =
            "update newsVO set administratorID = ?, newsTitle = ?, newsContent = ?, newsStatus = ? where newsID = ?";
    private static final String DELETE =
            "delete from newsVO where newsID = ?";
    private static final String FIND_BY_PK =
            "select * from newsVO where newsID = ?";
    private static final String GET_ALL =
            "select * from newsVO order by newsID desc";

    static {
        try {
            Class.forName(Util.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    // 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
//    private static DataSource ds = null;
//    static {
//        try {
//            Context ctx = new InitialContext();
//            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void insert(NewsVO news) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            ps = con.prepareStatement(INSERT);

            ps.setInt(1, news.getNewsID());
            ps.setInt(2, news.getAdministratorID());
            ps.setString(3, news.getNewsTitle());
            ps.setString(4, news.getNewsContent());
            ps.setInt(5, news.getNewsStatus());
            ps.setTimestamp(6, new Timestamp(news.getNewsCreateTime().getTime()));

            ps.executeUpdate();
            System.out.println("insert success");


        } catch (SQLException e) {
            throw new RuntimeException("A database error occured" + e.getMessage());
        } finally {
            closeResourses(con, ps, null);
        }
    }

    @Override
    public void update(NewsVO news) {
        Connection con = null;
        PreparedStatement ps = null;


        try {
            con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            ps = con.prepareStatement(UPDATE);

            ps.setInt(1, news.getAdministratorID());
            ps.setString(2, news.getNewsTitle());
            ps.setString(3, news.getNewsContent());
            ps.setInt(4, news.getNewsStatus());
            ps.setInt(5, news.getNewsID());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("A database error occured" + e.getMessage());
        } finally {
            closeResourses(con, ps, null);
        }
    }

    @Override
    public void delete(Integer newsID) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            ps = con.prepareStatement(DELETE);

            ps.setInt(1, newsID);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("A database error occured" + e.getMessage());
        }finally {
            closeResourses(con, ps, null);
        }
    }

    @Override
    public NewsVO findByPK(Integer newsID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        NewsVO newsVO = null;

        try {
            con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            ps = con.prepareStatement(FIND_BY_PK);

            ps.setInt(1, newsID);
            rs = ps.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNewsID(rs.getInt("newsID"));
                newsVO.setAdministratorID(rs.getInt("administratorID"));
                newsVO.setNewsTitle(rs.getString("newsTitle"));
                newsVO.setNewsContent(rs.getString("newsContent"));
                newsVO.setNewsStatus(rs.getInt("newsStatus"));
                newsVO.setNewsCreateTime(rs.getTimestamp("newsCreateTime"));

            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occured" + e.getMessage());
        }finally {
            closeResourses(con, ps, rs);
        }

        return newsVO;
    }

    @Override
    public List<NewsVO> getAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NewsVO> list = new ArrayList<NewsVO>();
        NewsVO newsVO = null;

        try {
            con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
            ps = con.prepareStatement(GET_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                newsVO = new NewsVO();
                newsVO.setNewsID(rs.getInt("newsID"));
                newsVO.setAdministratorID(rs.getInt("administratorID"));
                newsVO.setNewsTitle(rs.getString("newsTitle"));
                newsVO.setNewsContent(rs.getString("newsContent"));
                newsVO.setNewsStatus(rs.getInt("newsStatus"));
                newsVO.setNewsCreateTime(rs.getTimestamp("newsCreateTime"));
                list.add(newsVO);
            }
        } catch (SQLException e) {
            throw new RuntimeException("A database error occured" + e.getMessage());
        }finally {
            closeResourses(con, ps, rs);
        }
        return list;
    }

    private void closeResourses(Connection con, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public static void main(String[] args) {
        NewsDAOImpl dao = new NewsDAOImpl();

        NewsVO newsVO1 = new NewsVO();
        newsVO1.setNewsID(1001);
        newsVO1.setAdministratorID(5487);
        newsVO1.setNewsTitle("究竟0201能完成嗎");
        newsVO1.setNewsContent("不曉得實在不曉得!!網頁出現500嗚嗚");
        newsVO1.setNewsStatus(1);
        newsVO1.setNewsCreateTime(java.sql.Timestamp.valueOf("2024-07-14 22:58:00"));
        dao.insert(newsVO1);


//        NewsVO newsVO2 = new NewsVO();
//        newsVO2.setAdministratorID(5487);
//        newsVO2.setNewsTitle("還能睡覺嗎");
//        newsVO2.setNewsContent("5555555555555555哭哭");
//        newsVO2.setNewsStatus(0);
//        newsVO2.setNewsID(1001);
//
//        dao.update(newsVO2);
//
//
//        dao.delete(1001);
//
//
//        NewsVO newsVO3 = dao.findByPK(1001);
//        System.out.println(newsVO3.getNewsID() + ",");
//        System.out.println(newsVO3.getAdministratorID() + ",");
//        System.out.println(newsVO3.getNewsTitle() + ",");
//        System.out.println(newsVO3.getNewsContent() + ",");
//        System.out.println(newsVO3.getNewsStatus() + ",");
//        System.out.println(newsVO3.getNewsCreateTime() + ",");
//        System.out.println("-----------------");
//
//
//        List<NewsVO> list = dao.getAll();
//        for (NewsVO newsVO : list) {
//            System.out.println(newsVO.getNewsID() + ",");
//            System.out.println(newsVO.getAdministratorID() + ",");
//            System.out.println(newsVO.getNewsTitle() + ",");
//            System.out.println(newsVO.getNewsContent() + ",");
//            System.out.println(newsVO.getNewsStatus() + ",");
//            System.out.println(newsVO.getNewsCreateTime() + ",");
//            System.out.println();
//        }
    }
}
