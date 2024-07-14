<%--
  Created by IntelliJ IDEA.
  User: T14 Gen 3
  Date: 2024/7/14
  Time: 下午 08:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>IBM News: Home</title>

    <style>
        table#table-1 {
            width: 450px;
            background-color: #CCCCFF;
            margin-top: 5px;
            margin-bottom: 10px;
            border: 3px ridge Gray;
            height: 80px;
            text-align: center;
        }

        table#table-1 h4 {
            color: red;
            display: block;
            margin-bottom: 1px;
        }

        h4 {
            color: blue;
            display: inline;
        }
    </style>
</head>
<body bgcolor="#ffe4c4">

<table id="table-1">
    <tr>
        <td><h3>IBM News: Home</h3>
            <h4><a><img src="images/anyujin.png" width="100" height="100" border="0"></a>( MVC )</h4>
        </td>
    </tr>
</table>

<p>This is the Home page for IBM News: Home</p>

<h3>資料查詢:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<ul>
    <li><a href='listAllNews.jsp'>List</a> all News. <br><br></li>


    <li>
        <FORM METHOD="post" ACTION="news.do">
            <b>輸入消息編號 (如1001):</b>
            <input type="text" name="newsID">
            <input type="hidden" name="action" value="getOne_For_Display">
            <input type="submit" value="送出">
        </FORM>
    </li>

    <jsp:useBean id="newsSvc" scope="page" class="com.news.model.NewsService"/>

    <li>
        <FORM METHOD="post" ACTION="news.do">
            <b>選擇消息編號:</b>
            <select size="1" name="newsID">
                <c:forEach var="newsVO" items="${newsSvc.allNews}">
                <option value="${newsVO.newsID}">${newsVO.newsID}
                    </c:forEach>
            </select>
            <input type="hidden" name="action" value="getOne_For_Display">
            <input type="submit" value="送出">
        </FORM>
    </li>

    <li>
        <FORM METHOD="post" ACTION="news.do">
            <b>選擇消息標題:</b>
            <select size="1" name="newsID">
                <c:forEach var="newsVO" items="${newsSvc.allNews}">
                <option value="${newsVO.newsID}">${newsVO.newsTitle}
                    </c:forEach>
            </select>
            <input type="hidden" name="action" value="getOne_For_Display">
            <input type="submit" value="送出">
        </FORM>
    </li>
</ul>

<h3>消息管理</h3>

<ul>
    <li><a href='addNews.jsp'>Add</a> a new News.</li>
</ul>

</body>
</html>
