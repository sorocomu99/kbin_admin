// 에디터 스크립트 로딩, summernote 초기화
document.fonts.ready.then(function () {
    const script = document.createElement('script');
    const scriptLang = document.createElement('script');
    script.src = window.staticPath + "js/summernote-lite.min.js";
    scriptLang.src = window.staticPath + "js/summernote-ko-KR.min.js";
    document.body.appendChild(script);
    
    script.onload = function () {
        document.body.appendChild(scriptLang);
        setTimeout(function () {
            $('#summernote').summernote({
                lang: 'ko-KR',
                height: 300,
                styleTags: ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6'],
                fontNames: ['KBFG Text', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'Helvetica', 'Impact', 'Tahoma', 'Times New Roman', 'Verdana'],
                fontNamesIgnoreCheck: ['KBFG Text'],
            });
          }, 100);
    };
});

$(function(){
    // icon set
    feather.replace();

    // datepicker
    $.fn.datepicker.dates['kr'] = {
        days: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"],
        daysShort: ["일", "월", "화", "수", "목", "금", "토", "일"],
        daysMin: ["일", "월", "화", "수", "목", "금", "토", "일"],
        months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        monthsShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        dateToLocalizedText: function(year, month){
            var date_str = '';
            date_str = year + '년 ';
            date_str = date_str + this.months[month];
            return date_str;
        }
    };

    $('.input-group.date').datepicker({
        calendarWeeks: false,
        todayHighlight: true,
        autoclose: true,
        toggleActive: false,
        format: "yyyy-mm-dd",
        language: "kr"
    });

    // clockpicker
    $('.clockpicker').clockpicker({
        autoclose: true
    });

    // 물음표
    tippy('#helpPopup', {
        content: '<p><b>경로: </b>메인 화면</p><p><b>내용: </b>메인 화면 내 노출되는 팝업을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/popup.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpMenu', {
        content: '<p><b>경로: </b>메인 화면</p><p><b>내용: </b>메인 화면 내 노출되는 2depth 메뉴명을 등록/수정하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/menu.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpMain', {
        content: '<p><b>경로: </b>메인 화면</p><p><b>내용: </b>메인 화면 내 상단 비주얼을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/main.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpResult', {
        content: '<p><b>경로: </b>메인 화면</p><p><b>내용: </b>메인 화면 내 육성 현황 관리 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/result.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpRecruit', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 채용지원 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/recruit.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpHistory', {
        content: '<p><b>경로: </b>메인 화면</p><p><b>내용: </b>메인 화면 내 연혁 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/history.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpPromoteGraph', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 육성기업 그래프 내용을 등록/수정하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/promote-graph.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpHubIntroduce', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 제휴 사례를 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/hub-introduce.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpInvestmentGraph', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 투자 그래프 내용을 등록/수정하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/investment-graph.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpGrowth', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 협력기관 로고를 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/growth.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpSpace', {
        content: '<p><b>경로: </b>스타트업 육성 -> 국내 프로그램</p><p><b>내용: </b>국내 프로그램 화면 내 육성공간을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/space.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpInterchange', {
        content: '<p><b>경로: </b>스타트업 육성 -> 글로벌</p><p><b>내용: </b>글로벌 화면 내 현지교류 지원 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/interchange.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpSurvey', {
        content: '<p><b>경로: </b>KB스타터스 -> 지원하기</p><p><b>내용: </b>지원하기 화면 내 이미지를 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/survey.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpSurveyQuestion', {
        content: '<p><b>경로: </b>KB스타터스 -> 지원하기</p><p><b>내용: </b>지원하기 화면 내 설문 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/survey-question.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpSurveyGuide', {
        content: '<p><b>경로: </b>KB스타터스 -> 지원하기</p><p><b>내용: </b>지원하기 화면 내 모집 개요 내용을 등록/수정하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/survey-guide.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpStarters', {
        content: '<p><b>경로: </b>KB스타터스 -> 포트폴리오</p><p><b>내용: </b>포트폴리오 화면 내 연도별 로고 및 텍스트를 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/starters.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpNotice', {
        content: '<p><b>경로: </b>커뮤니티 -> 공지사항</p><p><b>내용: </b>공지사항 화면 내 게시글을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/notice.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpDay', {
        content: '<p><b>경로: </b>커뮤니티 -> HUB센터 소식</p><p><b>내용: </b>HUB센터 소식 화면 내 게시글을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/day.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpFaq', {
        content: '<p><b>경로: </b>커뮤니티 -> FAQ</p><p><b>내용: </b>FAQ 화면 내 게시글을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/faq.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
    tippy('#helpStartup', {
        content: '<p><b>경로: 스타트업 정보 -> 정보검색</b></p><p><b>내용: </b>정보검색 화면 내 KB스타터스 내용을 등록/수정/삭제하는 메뉴입니다.</p><img src="' + window.staticPath + 'images/help/startup.jpg" alt="">',
        placement: 'bottom',
        allowHTML: true,
        arrow: false,
    });
});
