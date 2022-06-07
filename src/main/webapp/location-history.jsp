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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>

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
            <a href="/load-wifi.jsp">
                Open API 와이파이 정보 요청 및 저장
            </a>
        </li>
    </ul>
</nav>

<table class="location_info_table">
    <%--  script template  --%>
</table>

<script id="location-history-template" type="text/x-handlebars-template">
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

    {{#each locationHistoryList}}
    <tr>
        <td> {{id}}</td>
        <td> {{posX}}</td>
        <td> {{posY}}</td>
        <td> {{dateTime}}</td>
        <td>
            <button onclick="initLocationColumnButtonListener(this)" type="button" id="locationButton"
                    button-id="{{id}}">삭제
            </button>
        </td>
    </tr>
    {{/each}}
</script>

<script type="text/javascript">
    /** 오픈소스 참조 (로딩 중 화면 만들기) **/
    function loadingWithMask() {
        //화면 높이와 너비를 구합니다.
        let maskHeight = $(document).height();
        let maskWidth = window.document.body.clientWidth;
        //출력할 마스크를 설정해준다.
        let mask = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
        // 로딩 이미지 주소 및 옵션
        let loadingImg = '';
        loadingImg += "<div id='loadingImg' style='position:absolute; top: calc(50% - (200px / 2)); width:100%; z-index:99999999;'>";
        loadingImg += " <img src='https://loadingapng.com/animation.php?image=4&fore_color=000000&back_color=FFFFFF&size=128x128&transparency=1&image_type=0&uncacher=75.5975991029623' style='position: relative; display: block; margin: 0px auto;'/>";
        loadingImg += "</div>";
        //레이어 추가
        $('body')
            .append(mask)
            .append(loadingImg)
        //마스크의 높이와 너비로 전체 화면을 채운다.
        $('#mask').css({
            'width': maskWidth,
            'height': maskHeight,
            'opacity': '0.3'
        });
        //마스크 표시
        $('#mask').show();
        //로딩 이미지 표시
        $('#loadingImg').show();
    }

    /** 오픈소스 참조 (로딩 중 화면 닫기) **/
    function closeLoadingWithMask() {
        $('#mask, #loadingImg').hide();
        $('#mask, #loadingImg').empty();
    }

    function initLocationColumnButtonListener(clickedBtn) {
        if (confirm("해당 위치 칼럼을 삭제 하시겠습니까?")) {
            this.loadingWithMask();
            const locationInfoTable = document.querySelector(".location_info_table tbody");
            const params = 'id=' + clickedBtn.getAttribute("button-id");
            this.submitXMLHttpRequest(params);
            locationInfoTable.removeChild(clickedBtn.closest("tr"));
        }
    }

    function submitXMLHttpRequest(params) {
        const xhr = new XMLHttpRequest();
        xhr.open("DELETE", '/location?' + params, true);
        xhr.addEventListener("loadend", () => {
            this.closeLoadingWithMask();
        })
        xhr.send();
    }

    class LocationTableController {
        constructor() {
            this.locationInfoTable = document.querySelector(".location_info_table");
        }

        initLocationTableController() {
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "/location", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            loadingWithMask();

            xhr.addEventListener("loadend", evt => {
                let status = evt.target.status;
                closeLoadingWithMask();
                if (status > 400 || status > 500) {
                    alert('위치 정보 액세스에 실패하였습니다.')
                } else {
                    // TODO
                    const responseJSON = JSON.parse(evt.target.responseText);
                    const locationHistoryTemplate = document.querySelector("#location-history-template").innerHTML;
                    const template = Handlebars.compile(locationHistoryTemplate);
                    const templateHTML = template({locationHistoryList: responseJSON});
                    this.locationInfoTable.innerHTML = templateHTML;
                }
            });
            xhr.send();
        }
    }

    document.addEventListener("DOMContentLoaded", () => {
        const locationTableController = new LocationTableController();
        locationTableController.initLocationTableController();
    });
</script>
</body>
</html>
