<%--
  Created by IntelliJ IDEA.
  User: apdh1
  Date: 2022-05-27
  Time: 오후 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="description"
          content="wifi 정보 제공 뷰입니다.">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <title>wifi 정보 페이지</title>
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>

    <style type="text/css">
        /*기본스타일 정의*/
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

        .location_form {
            width: fit-content;
            padding: 10px;
            border: 1px solid rgba(94, 84, 84, 0.28);
            margin-bottom: 10px;
        }
    </style>
</head>

<body>
<div class="container">
    <h1>와이파이 정보 구하기</h1>

    <nav class="menu clear_fix">
        <ul>
            <li>
                <a href="">
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
                    Open API 와이파이 정보 가져오기
                </a>
            </li>
        </ul>
    </nav>

    <form class="location_form">
        <label for="x-pos-name">LAT:</label>

        <input type="number" id="x-pos-name" name="x-pos-name"
               class="input_form" placeholder="x좌표를 입력하세요."/>

        <label for="y-pos-name">LAT:</label>

        <input type="number" id="y-pos-name" name="y-pos-name"
               class="input_form" placeholder="y좌표를 입력하세요."/>

        <button type="button" id="locationButton">내 위치 가져오기</button>
        <button type="button" id="wifiButton">근처 WIFI 정보 가져오기</button>
    </form>
</div>

<!-- main.js -->
<script>
    class LocationFormController {
        constructor() {
            this.locationForm = document.querySelector(".location_form");
            this.locationButton = document.querySelector("#locationButton");
            this.wifiButton = document.querySelector("#wifiButton");
        }

        initLocationFormController() {
            const locationButton = this.locationButton;

            locationButton.addEventListener("click", () => {
                let xhr = new XMLHttpRequest();
                const formData = new FormData(this.locationForm);
                const params = "posX=" + formData.get("x-pos-name") + "&" + "posY=" + formData.get("y-pos-name");

                xhr.open("GET", '/wifi?' + params, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;
                    // let responseJSON = JSON.parse(event.target.responseText);
                    // let responseText = JSON.stringify(responseJSON, null, 4);
                    console.log(status);
                });
                xhr.send();
            });
        }
    }

    document.addEventListener("DOMContentLoaded", () => {
        const locationFormController = new LocationFormController();
        locationFormController.initLocationFormController();
    });
</script>

</body>
</html>
