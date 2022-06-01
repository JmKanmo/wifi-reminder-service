<%--
  Created by IntelliJ IDEA.
  User: apdh1
  Date: 2022-05-27
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>위치 히스토리 뷰</title>
    <meta charset="utf-8">
    <meta name="description"
          content="wifi 정보 제공 뷰입니다.">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <style type="text/css">
        html,
        body {
            margin: 0;
            padding: 0;
            min-width: 1080px;
            min-height: 100%;
            font-family: '나눔 고딕', NanumGothic, '돋움', Dotum, sans-serif;
        }

        ul,
        ol,
        li {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        a {
            text-decoration: none;
            color: #000;
        }

        .clear_fix::after {
            content: "";
            display: block;
            clear: both;
        }


        .menu {
            width: fit-content;
            padding: 10px;
            border: 1px solid rgba(94, 84, 84, 0.28);
            margin-bottom: 20px;
        }

        .menu ul li {
            margin-top: 5px;
        }

        .location_info_table .header {
            border: 1px solid gray;
            background-color: #3fc83f;
            text-align: center;
            color: white;
        }

        .location_info_table td {
            padding: 10px;
            min-width: 100px;
            border: 0.1px solid #29e595;
            text-align: center;
        }
    </style>
</head>
<body>
<h1>위치 히스토리 목록</h1>

<nav class="menu clear_fix">
    <ul>
        <li>
            <a href="/">
                홈
            </a>
        </li>

        <li>
            <a href="/location">
                위치 히스토리 목록
            </a>
        </li>

        <li>
            <a href="/load-wifi">
                Open API 와이파이 정보 요청 및 저장
            </a>
        </li>
    </ul>
</nav>

<table class="location_info_table">
    <tr class="location_info_table header">
        <td>
            <p>id</p>
        </td>

        <td>
            <p>posX</p>
        </td>

        <td>
            <p>posY</p>
        </td>

        <td>
            <p>조회일자</p>
        </td>

        <td>
            <p>비고</p>
        </td>
    </tr>

    <c:forEach var="locationHistory" items="${location_history}">
        <tr>
            <td> ${locationHistory.id}</td>
            <td> ${locationHistory.posX}</td>
            <td> ${locationHistory.posY}</td>
            <td> ${locationHistory.dateTime}</td>
            <td>
                <button onclick="initLocationColumnButtonListener(this)" type="button" id="locationButton"
                        button-id="${locationHistory.id}">삭제
                </button>
            </td>
        </tr>
    </c:forEach>
</table>

<script type="text/javascript">
    function initLocationColumnButtonListener(clickedBtn) {
        if (confirm("해당 위치 칼럼을 삭제 하시겠습니까?")) {
            const locationInfoTable = document.querySelector(".location_info_table tbody");
            const params = 'id=' + clickedBtn.getAttribute("button-id");
            this.submitXMLHttpRequest(params);
            locationInfoTable.removeChild(clickedBtn.closest("tr"))
        }
    }

    function submitXMLHttpRequest(params) {
        const xhr = new XMLHttpRequest();
        xhr.open("DELETE", '/location?' + params, true);
        xhr.send();
    }
</script>
</body>
</html>
