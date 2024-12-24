package com.kb.inno.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static Calendar cal = Calendar.getInstance();

    /**
     * 현재 연도 반환
     */
    public String getYear() {
        return Integer.toString(cal.get(Calendar.YEAR));
    }

    /**
     * 현재 월 반환
     */
    public String getMonth() {
        int mon = cal.get(Calendar.MONTH) + 1;
        if (mon < 10) {
            return "0" + mon;
        } else {
            return Integer.toString(mon);
        }
    }

    /**
     * 현재 날자 반환
     */
    public String getDay() {
        int date = cal.get(Calendar.DATE);
        if (date < 10) {
            return "0" + date;
        } else {
            return Integer.toString(date);
        }
    }

    /**
     * 현재 시간 반환
     */
    public String getHour() {
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            return "0" + hour;
        } else {
            return Integer.toString(hour);
        }
    }

    /**
     * 현재 분 반환
     */
    public String getMinute() {
        int min = cal.get(Calendar.MINUTE);
        if (min < 10) {
            return "0" + min;
        } else {
            return Integer.toString(min);
        }
    }

    /**
     * 현재날짜를 지정된 포맷으로 만들어 리턴
     * @param pattern
     * @return
     */
    public static String getToDay(String pattern) {
        String toDay = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
            Date d = new Date();
            toDay = sdf.format(d);
        } catch (Exception e) {

        }

        return toDay;
    }

    /**
     * 전월 구해오기
     * @param yyyymm : 년월
     * @return String : 전월
     */
    public static String getLastMonth(String yyyymm) {
        String lastMonth = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREA);
            Calendar cal = Calendar.getInstance();
            Date sMonth = sdf.parse(yyyymm);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, -1);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lastMonth;
    }

    /**
     * 전년 구해오기
     * @param yyyyMMdd
     * @param pattern
     * @return 전년도 날자
     * 사용 예
     * getLastYear("20200101", "yyyyMMdd") > '20190101'
     * getLastYear("2020", "yyyy") > '2019'
     */
    public static String getLastYear(String yyyyMMdd, String pattern) {
        String lastYear = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(yyyyMMdd));
            cal.add(Calendar.YEAR, -1);

            lastYear = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lastYear;
    }

    /**
     * 월의 시작일자를 구해오는 함수
     * @param yyyymm
     * @return 해당월의 시작 일자
     */
    public static String getFirstDayOfMonth(String yyyymm) {
        String firstOfMonth = "";

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(yyyymm.substring(0, 4)), Integer.valueOf(yyyymm.substring(4, 6)) -1,1);
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        firstOfMonth = yyyymm + String.valueOf(firstDay);

        return firstOfMonth;
    }

    /**
     * 월의 마지막일자를 구해오는 함수
     * @param yyyymm
     * @return 해당월의 마지막 날짜
     * 사용 예
     * getLastDayOfMonth("202001") --> "20200131"
     * getLastDayOfMonth("202004") --> "20200430"
     */
    public static String getLastDayOfMonth(String yyyymm) {
        String endOfMonth = "";

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(yyyymm.substring(0, 4)), Integer.valueOf(yyyymm.substring(4, 6)) -1,1);
        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        endOfMonth = yyyymm + String.valueOf(endDay);

        return endOfMonth;
    }

    /**
     * 파라미터로 넘기는 수 이후의 월 구해오기
     * @param yyyymm 년월
     * @param month 개월수
     * @return String : yyyymm으로 부터 month가 지난 년월
     * 사용 예
     * getMonthByDiffMonth("202012", 3) > "202103"
     * getMonthByDiffMonth("202012", -3) > "202009"
     */
    public static String getMonthByDiffMonth(String yyyymm, int month) {
        String lastMonth = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREA);
            Calendar cal = Calendar.getInstance();
            Date sMonth = sdf.parse(yyyymm);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, month);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lastMonth;
    }

    /**
     * 해당일자로 부터 몇일 뒤 또는 몇일 후 를 구하는 함수(yyyyMMdd)
     * @author swoh1227
     * @param baseDate 기준일자
     * @param diffDate 일수
     * @return
     */
    public static String getDateByDiffDate(String baseDate, int diffDate) {
        String result = "";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            date = sdf.parse(baseDate);
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, diffDate);
            date = cal.getTime();
            result = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 두 날자의 차이를 리턴한다
     * @param startDate
     * @paran endDate
     * @return
     */
    public static int getDiffDay(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate;
        Date eDate;
        int rtnDiff = 0;

        try {
            sDate = sdf.parse(startDate);
            eDate = sdf.parse(endDate);

            rtnDiff = (int)((eDate.getTime() - sDate.getTime()) / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            rtnDiff = 0;
        }

        return rtnDiff;
    }

    /**
     * 입력된 날자가 올바른지 확인
     * @param yyyymmdd
     * @return boolean
     * 사용 예
     * boolean b = DateUtil.isCorrect("20080101")
     */
    public static boolean isCorrect(String yyyymmdd) {
        boolean flag = false;
        if (yyyymmdd.length() < 8) {
            return false;
        }

        try {
            int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
            int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
            int dd = Integer.parseInt(yyyymmdd.substring(6));
            flag = isCorrect(yyyy, mm, dd);
        } catch (Exception e) {
            return false;
        }

        return flag;
    }

    /**
     * 입력된 날자가 올바른지 확인
     * @param yyyy
     * @param mm
     * @param dd
     * @return boolean
     * 사용 예
     * boolean b = DateUtil.isCorrect(2008,1,1)
     */
    public static boolean isCorrect(int yyyy, int mm, int dd){
        if (yyyy < 0 || mm < 0 || dd < 0) {
            return false;
        }

        if (mm > 12 || dd > 31) {
            return false;
        }

        String year = "" + yyyy;
        String month = "00" + mm;
        String year_str = year + month.substring(month.length() - 2);
        int endDay = Integer.parseInt(getLastDayOfMonth(year_str));

        if (dd > endDay) {
            return false;
        }

        return true;
    }

    /**
     * 입력된 일자를 포맷 구분의 형태로 변환하여 반환한다.
     * @param yyyymmdd
     * @param formatGbn 1 : "-", 2 : ".", 3 : 년 월 일
     * @return String
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     */
    public static String changeDateFormat(String yyyymmdd, int formatGbn) {
        String rtnDate = null;
        String yyyy = yyyymmdd.substring(0, 4);
        String mm = yyyymmdd.substring(4, 6);
        String dd = yyyymmdd.substring(6, 8);

        if (formatGbn == 1) {
            rtnDate = yyyy + "-" + mm + "-" + dd;
        } else if (formatGbn == 2) {
            rtnDate = yyyy + "." + mm + "." + dd;
        } else {
            rtnDate = yyyy + " 년 " + mm + " 월 " + dd + " 일";
        }

        return rtnDate;
    }

    /**
     * 현재의 요일을 숫자값으로 리턴한다
     * @param
     * @return 요일
     * 사용 예
     * int day = DateUtil.getDayOfWeek();
     * SUNDAY = 1, MONDAY = 2, TUESDAY = 3, WEDNESDAY = 4, THURSDAY = 5, FRIDAY = 6, SATURDAY = 7
     */
    public static int getDayOfWeek() {
        Calendar week = Calendar.getInstance();
        int dayOfWeek = week.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }

    /**
     * 현재의 요일을 한글 또는 영문으로 리턴한다.
     * @prarm langGbn kor : 한글, eng : 영문
     * @return
     */
    public static String getDayOfWeekLang(String langGbn) {
        String weekLang = "";
        String[] dayKor = {"일", "월", "화", "수", "목", "금", "토"};
        String[] dayEng = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        Calendar week = Calendar.getInstance();
        int dayOfWeek = week.get(Calendar.DAY_OF_WEEK);

        if (langGbn.equals("kor")) {
            weekLang = dayKor[dayOfWeek];
        } else if (langGbn.equals("eng")) {
            weekLang = dayEng[dayOfWeek];
        }

        return weekLang;
    }

    /**
     * 현재주가 올해 전체의 몇째주에 해당되는지 계산한다.
     * @param
     * @return 요일
     *  - 사용 예
     * int day = DateUtil.getWeekOfYear()
     */
    public static int getWeekOfYear() {
        Locale localCountry = Locale.KOREA;
        Calendar now = Calendar.getInstance(localCountry);
        int weekOfYear = now.get(Calendar.WEEK_OF_YEAR);

        return weekOfYear;
    }

    /**
     * 현재 주가 현재월에 몇째주에 해당되는지 계산한다.
     * @param
     * @return
     * 사용 예
     * int day = DateUtil.getWeekOfMonth()
     */
    public static int getWeekOfMonth() {
        Locale localCountry = Locale.KOREA;
        Calendar now = Calendar.getInstance(localCountry);
        int weekOfMonth = now.get(Calendar.WEEK_OF_MONTH);

        return weekOfMonth;
    }

    /**(
     * 윤년 체크
     * @param baseDt
     * @return boolena 윤년이면 true 아니면 false
     */
    public static boolean leapYearCheck(String baseDt) {
        cal.set(Calendar.YEAR, Integer.parseInt(baseDt.substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(baseDt.substring(4, 6)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(baseDt.substring(6, 8)));

        if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == 29) {
            return true;
        } else {
            return false;
        }
    }
}
