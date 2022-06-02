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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>


    <!-- CSS 정의 -->
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

        .location_form {
            width: fit-content;
            padding: 10px;
            border: 1px solid rgba(94, 84, 84, 0.28);
            margin-bottom: 10px;
        }

        .wifi_info_table .header {
            border: 1px solid gray;
            background-color: #3fc83f;
            text-align: center;
            color: white;
        }

        .wifi_info_table td {
            padding: 0 30px 0 30px;
            min-width: 100px;
            border: 0.1px solid #29e595;
        }
    </style>
</head>

<body>
<div class="container">
    <h1>와이파이 정보 구하기</h1>

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

    <form class="location_form" action="/location" method="POST">
        <label for="x-pos-name">LAT(X):</label>

        <input type="number" id="x-pos-name" name="x-pos-name"
               class="input_form" placeholder="x좌표를 입력하세요."/>

        <label for="y-pos-name">LNT(Y):</label>

        <input type="number" id="y-pos-name" name="y-pos-name"
               class="input_form" placeholder="y좌표를 입력하세요."/>

        <button type="button" id="locationButton">내 위치 가져오기</button>
        <button type="button" id="wifiButton">근처 WIFI 정보 가져오기</button>
    </form>

    <table class="wifi_info_table">
        <!--
        <tr> wifi-info-template </tr>
        -->
    </table>
</div>

<script id="wifi-info-template" type="text/x-handlebars-template">
    <tr class="wifi_info_table header">
        <td>
            <p>거리 <br> (Km)</p>
        </td>

        <td>
            <p>관리번호</p>
        </td>

        <td>
            <p>자치구</p>
        </td>

        <td>
            <p>와이파이명</p>
        </td>

        <td>
            <p>도로명주소</p>
        </td>

        <td>
            <p>상세주소</p>
        </td>

        <td>
            <p>설치위치(층)</p>
        </td>

        <td>
            <p>설치유형</p>
        </td>

        <td>
            <p>설치기관</p>
        </td>

        <td>
            <p>서비스구분</p>
        </td>

        <td>
            <p>망종류</p>
        </td>

        <td>
            <p>설치년도</p>
        </td>

        <td>
            <p>실내외구분</p>
        </td>

        <td>
            <p>WIFI접속환경</p>
        </td>

        <td>
            <p>X좌표</p>
        </td>

        <td>
            <p>Y좌표</p>
        </td>

        <td>
            <p>작업일자</p>
        </td>
    </tr>

    {{#each wifiInfoList}}
    <tr>
        <td> {{distance}}</td>
        <td> {{adminNumber}}</td>
        <td> {{borough}}</td>
        <td> {{wifiName}}</td>
        <td> {{loadName}}</td>
        <td> {{detailAddress}}</td>
        <td> {{installPosition}}</td>
        <td> {{installType}}</td>
        <td> {{installAgency}}</td>
        <td> {{serviceType}}</td>
        <td> {{netType}}</td>
        <td> {{installYear}}</td>
        <td> {{inOutDoorType}}</td>
        <td> {{wifiConnEnv}}</td>
        {{#with locationDate}}
        <td> {{posX}}</td>
        <td> {{posY}}</td>
        <td> {{dateTime}}</td>
        {{/with}}
    </tr>
    {{/each}}
</script>

<!-- JS 정의 -->
<script type="text/javascript">
    class WifiServiceFormController {
        constructor() {
            this.locationForm = document.querySelector(".location_form");
            this.locationButton = document.querySelector("#locationButton");
            this.wifiButton = document.querySelector("#wifiButton");
            this.wifiInfoTable = document.querySelector(".wifi_info_table");
            this.flagNumber = 0;
        }

        /** 오픈소스 참조 (로딩 중 화면 만들기) **/
        loadingWithMask() {
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
        closeLoadingWithMask() {
            $('#mask, #loadingImg').hide();
            $('#mask, #loadingImg').empty();
        }

        initWifiServiceFormController() {
            this.initWifiServiceTable();
            this.initLocationHistoryButton();
            this.initWifiButtonListener();
        }

        initWifiServiceTable() {
            const responseJSON = {};
            const wifiInfoTemplate = document.querySelector("#wifi-info-template").innerHTML;
            const template = Handlebars.compile(wifiInfoTemplate);
            const templateHTML = template({wifiInfoList: responseJSON});
            this.wifiInfoTable.innerHTML = templateHTML;
        }

        initLocationHistoryButton() {
            const locationButton = this.locationButton;

            locationButton.addEventListener("click", () => {
                let xhr = new XMLHttpRequest();

                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(position => {
                        this.locationForm["x-pos-name"].value = position.coords.latitude;
                        this.locationForm["y-pos-name"].value = position.coords.longitude;
                    })
                } else {
                    alert('위치 기능이 현재 활성화되어있지 않습니다.');
                }
            });
        }

        initWifiButtonListener() {
            const wifiButton = this.wifiButton;

            wifiButton.addEventListener("click", () => {
                const formData = new FormData(this.locationForm);

                let xPos = formData.get("x-pos-name");
                let yPos = formData.get("y-pos-name");

                this.loadingWithMask();

                if (!xPos || !yPos) {
                    if (navigator.geolocation) {
                        navigator.geolocation.getCurrentPosition(position => {
                            this.locationForm["x-pos-name"].value = position.coords.latitude;
                            this.locationForm["y-pos-name"].value = position.coords.longitude;

                            xPos = this.locationForm["x-pos-name"].value;
                            yPos = this.locationForm["y-pos-name"].value;

                            const params = "xPos=" + xPos + "&"
                                + "yPos=" + yPos + "&offSet=0" + "&cnt=20";
                            this.submitXMLHttpRequestWifiInfo(params);
                            this.submitXMLHttpRequestLocationInfo("xPos=" + xPos + "&yPos=" + yPos);
                        })
                    }
                } else {
                    const params = "xPos=" + xPos + "&"
                        + "yPos=" + yPos + "&offSet=0" + "&cnt=20";
                    this.submitXMLHttpRequestWifiInfo(params);
                    this.submitXMLHttpRequestLocationInfo("xPos=" + xPos + "&yPos=" + yPos)
                }
            });
        }

        submitXMLHttpRequestLocationInfo(params) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", '/location?' + params, true);

            xhr.addEventListener("loadend", event => {
                let status = event.target.status;
                if (status > 400 || status > 500) {
                    alert('위치 정보가 데이터 베이스에 저장되지 않았습니다.')
                }

                this.flagNumber++;
                if (this.flagNumber >= 2) {
                    this.closeLoadingWithMask();
                    this.flagNumber = 0;
                }
            });
            xhr.send();
        }

        submitXMLHttpRequestWifiInfo(params) {
            const xhr = new XMLHttpRequest();
            xhr.open("GET", '/wifi?' + params, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.addEventListener("loadend", event => {
                const responseJSON = JSON.parse(event.target.responseText).map(wifi => {
                    let temp = wifi;
                    temp["distance"] = temp["distance"].toFixed(4);
                    return temp;
                });
                const wifiInfoTemplate = document.querySelector("#wifi-info-template").innerHTML;
                const template = Handlebars.compile(wifiInfoTemplate);
                const templateHTML = template({wifiInfoList: responseJSON});
                this.wifiInfoTable.innerHTML = templateHTML;

                this.flagNumber++;
                if (this.flagNumber >= 2) {
                    this.closeLoadingWithMask();
                    this.flagNumber = 0;
                }
            });
            xhr.send();
        }
    }

    document.addEventListener("DOMContentLoaded", () => {
        const wifiServiceFormController = new WifiServiceFormController();
        wifiServiceFormController.initWifiServiceFormController();
    });
</script>
</body>
</html>
