<%--
  Created by IntelliJ IDEA.
  User: apdh1
  Date: 2022-05-27
  Time: 오후 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이로드뷰</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<h2 id="wifi-load-result"></h2>
<a href="/">홈으로 가기</a>

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

    function submitXMLHttpRequestLoadWifiInfo() {
        const xhr = new XMLHttpRequest();

        xhr.open("GET", "/load-wifi", true);

        xhr.addEventListener("loadend", (evt) => {
            const resultTag = document.querySelector("#wifi-load-result");
            const responseJSON = JSON.parse(evt.target.responseText);
            const reqCount = responseJSON["response"];

            if (reqCount < 0) {
                resultTag.textContent = "현재 데이터베이스 네트워크 상황이 불안정하여 연결이 종료된 상태이므로 와이파이 정보를 저장할 수 없습니다.";
            } else if (reqCount == 0) {
                resultTag.textContent = "데이터베이스 네트워크 문제 등으로 정보가 저장되지 않았습니다.";
            } else {
                resultTag.textContent = reqCount + "개의 wifi 정보를 정상적으로 저장하였습니다."
            }
            closeLoadingWithMask();
        });
        xhr.send();
        loadingWithMask();
    }

    document.addEventListener("DOMContentLoaded", () => {
        submitXMLHttpRequestLoadWifiInfo();
    });
</script>
</body>
</html>
