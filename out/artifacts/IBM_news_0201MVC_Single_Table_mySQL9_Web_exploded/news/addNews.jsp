<%--
  Created by IntelliJ IDEA.
  User: T14 Gen 3
  Date: 2024/7/14
  Time: 下午 08:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.news.model.*" %>

<%
    NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>消息資料新增 - addNews.jsp</title>

    <style>
        table#table-1 {
            background-color: #CCCCFF;
            border: 2px solid black;
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

    <style>
        table {
            width: 450px;
            background-color: white;
            margin-top: 1px;
            margin-bottom: 1px;
        }

        table, th, td {
            border: 0px solid #CCCCFF;
        }

        th, td {
            padding: 1px;
        }
    </style>

</head>
<body bgcolor="#ffe4c4">

<table id="table-1">
    <tr>
        <td>
            <h3>消息資料新增 - addNews.jsp</h3></td>
        <td>
            <h4><a href="select_page.jsp"><img src="images/anyujin.png" width="100" height="100" border="0">回首頁</a>
            </h4>
        </td>
    </tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<FORM METHOD="post" ACTION="news.do" name="form1">
    <table>


        <tr>
            <td>消息編號:</td>
            <td><input type="TEXT" name="newsID"
                       value="<%= (newsVO==null)? "1001" : newsVO.getNewsID()%>" size="45"/>
            </td>
        </tr>
        <tr>
            <td>管理員編號:</td>
            <td><input type="TEXT" name="administratorID"
                       value="<%= (newsVO==null)? "5487" : newsVO.getAdministratorID()%>" size="45"/>
            </td>
        </tr>
        <tr>
            <td>標題:</td>
            <td><input type="TEXT" name="newsTitle"
                       value="<%= (newsVO==null)? "0201究竟做得完嗎" : newsVO.getNewsTitle()%>" size="45"/></td>
        </tr>
        <tr>
            <td>內容:</td>
            <td><input type="TEXT" name="newsContent"
                       value="<%= (newsVO==null)? "好像做得完又好像做不完,但重點是能開啟網頁嗎" : newsVO.getNewsContent()%>"
                       size="45"/></td>
        </tr>
        <tr>
            <td>狀態:</td>
            <td><input type="TEXT" name="newsStatus" value="<%= (newsVO==null)? "1" : newsVO.getNewsStatus()%>"
                       size="45"/></td>
        </tr>
        <tr>
            <td>發布時間:</td>
            <td><input name="newsCreateTime" id="f_date1" type="text"></td>
        </tr>

        <%--        <jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService"/>--%>
        <%--        <tr>--%>
        <%--            <td>部門:<font color=red><b>*</b></font></td>--%>
        <%--            <td><select size="1" name="deptno">--%>
        <%--                <c:forEach var="deptVO" items="${deptSvc.all}">--%>
        <%--                <option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname}--%>
        <%--                    </c:forEach>--%>
        <%--            </select></td>--%>
        <%--        </tr>--%>

    </table>
    <br>
    <input type="hidden" name="action" value="insert">
    <input type="submit" value="送出新增"></FORM>
</body>

<%
    java.sql.Timestamp newsCreateTime = null;
    try {
        newsCreateTime = newsVO.getNewsCreateTime();
    } catch (Exception e) {
//        newsCreateTime = new java.sql.Date(System.currentTimeMillis());
        newsCreateTime = new java.sql.Timestamp(System.currentTimeMillis());
    }
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css"/>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
    .xdsoft_datetimepicker .xdsoft_datepicker {
        width: 300px; /* width:  300px; */
    }

    .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
        height: 151px; /* height:  151px; */
    }
</style>

<script>
    $.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
        theme: '',              //theme: 'dark',
        timepicker: false,       //timepicker:true,
        step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
        format: 'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
        value: '<%=newsCreateTime%>', // value:   new Date(),
        //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
        //startDate:	            '2017/07/10',  // 起始日
        //minDate:               '-1970-01-01', // 去除今日(不含)之前
        //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });


    // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

    //      1.以下為某一天之前的日期無法選擇
    //      var somedate1 = new Date('2017-06-15');
    //      $('#f_date1').datetimepicker({
    //          beforeShowDay: function(date) {
    //        	  if (  date.getYear() <  somedate1.getYear() ||
    //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) ||
    //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
    //              ) {
    //                   return [false, ""]
    //              }
    //              return [true, ""];
    //      }});


    //      2.以下為某一天之後的日期無法選擇
    //      var somedate2 = new Date('2017-06-15');
    //      $('#f_date1').datetimepicker({
    //          beforeShowDay: function(date) {
    //        	  if (  date.getYear() >  somedate2.getYear() ||
    //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) ||
    //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
    //              ) {
    //                   return [false, ""]
    //              }
    //              return [true, ""];
    //      }});


    //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
    //      var somedate1 = new Date('2017-06-15');
    //      var somedate2 = new Date('2017-06-25');
    //      $('#f_date1').datetimepicker({
    //          beforeShowDay: function(date) {
    //        	  if (  date.getYear() <  somedate1.getYear() ||
    //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) ||
    //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
    //		             ||
    //		            date.getYear() >  somedate2.getYear() ||
    //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) ||
    //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
    //              ) {
    //                   return [false, ""]
    //              }
    //              return [true, ""];
    //      }});

</script>

</html>
