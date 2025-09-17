package com.example.coding_test.여기어때;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * 입사 날짜가 담겨있는 문자열 join_date, 퇴사 날짜가 담겨있는 문자열 resign_date, 공휴일 날짜들이 담겨있는 문자열 배열 holidays가 매개변수로 주어집니다.
 * 이 때, 입사한 날부터 퇴사한 날까지 주말(토,일요일)과 공휴일을 제외한 실제 근무 일 수를 계산해서 return 하도록 solution 함수를 완성해주세요.
 * - 윤년의 2월은 29일까지 있으며, 아래의 두 조건중 하나에 해당하면 윤년입니다.
 *     - 조건(1) 400으로 나누어 떨어지는 해
 *     - 조건(2) 4로 나누어 떨어지지만 100으로는 나누어 떨어지지 않는 해
 *
 * 2000년은 조건(1)에 해당하므로 윤년이며, 2004년은 조건(2)에 해당하므로 윤년입니다.
 * 1800년은 조건 (1), (2) 어디에도 해당하지 않으므로 윤년이 아닙니다.
 *
 * 제한 사항
 * - join_date는 길이 14로 고정된 문자열 입니다.
 * - join_date는 "YYYY/MM/DD DAY" 형식이며, "0001/01/01 TUE"에서 "2999/12/31 TUE" 사이입니다.
 * - join_date에서 DAY는 요일을 의미하며, 아래와 같이 표기합니다.
 * 월요일 : MON, 화요일: TUE, 수요일: WED, 목요일: THU, 금요일: FRI, 토요일: SAT, 일요일: SUN
 * - resign_date는 길이 10으로 고정된 문자열입니다.
 * - resign_date는 "YYYY/MM/DD" 형식이며, "0001/01/01"에서 "2999/12/31"사이입니다.
 * - 잘못된 날짜(1801/02/29, 2019/15/01)나 resign_date가 join_date보다 이른 경우는 입력으로 주어지지않습니다.
 * - 요일은 항상 정확하게 주어집니다. 예를 들어, 2019년 11월 21일이 입사 날짜로 주어진다면 항상 "2019/11/21 THU"으로 주어집니다.
 * - join_date와 resign_date도 근무 기간에 포함됩니다. 단, join_date와 resign_date가 주말이나 공휴일이라면 근무 일 수에서 제외합니다.
 * - holidays는 길이 1이상 365이하의 문자열 배열이며, 공휴일이 담겨있습니다.
 * - holidays의 원소는 길이 5로 고정된 문자열 입니다.
 * - holidays의 원소는 "MM/DD"형식이며, "01/01"에서 "12/31" 사이입니다.
 * - 잘못된 날짜(01/32, 13/05)나 중복된 날짜는 holidays의 원소로 주어지지 않습니다.
 * - holidays의 원소는 모두 양력을 사용하는 공휴일입니다. 따라서 년도가 바뀌어도 공휴일의 날짜는 변하지 않습니다.
 */
public class FirstSolutionTest {

    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter MD  = DateTimeFormatter.ofPattern("MM/dd");


    @Test
    void test() {

        String first_join_date = "2019/12/01 SUN";
        String first_resign_date = "2019/12/31";
        String[] first_holidays = {"12/25"};
        int first_result = 21;

        String second_join_date = "2019/12/01 SUN";
        String second_resign_date = "20120/03/02";
        String[] second_holidays = {"01/02", "12/24", "03/01"};
        int second_result = 64;

        String third_join_date = "2019/11/21 THU";
        String third_resign_date = "2019/11/21";
        String[] third_holidays = {"12/23"};
        int third_result = 1;

        String fourth_join_date = "2025/12/01 MON";
        String fourth_resign_date = "2026/01/12";
        String[] fourth_holidays = {"12/25"};
        int fourth_result = 30;



        assertThat(solution(fourth_join_date, fourth_resign_date, fourth_holidays)).isEqualTo(fourth_result);
    }

    private int solution(String join_date, String resign_date, String[] holidays) {

        LocalDate joinDate = LocalDate.parse(join_date.split(" ")[0], YMD);
        LocalDate resignDate = LocalDate.parse(resign_date.split(" ")[0], YMD);

        // 전체 일수 (오버 플로우 방지 (int -> long)) -> 입사일을 포함해야해서 + 1
        long diff = ChronoUnit.DAYS.between(joinDate, resignDate) + 1;

        /*
         * 속도 개선
         * 일 단위로 검증하지않는다.
         * (전체 일수 - 주말 수) - 공휴일 수
         */

        // 주말 수 (전체 일수 / 7) * 2 -> 주말은 토,일이기 때문
        long weekendsCount = diff / 7 * 2;

        // 평일 수
        long weekCount = diff - weekendsCount;

        // 남은 일수 (전체 일수 / 7을 한 나머지)
        // ex)
        // 입사일: 12/1일, 퇴사일: 1/12일 인 경우
        // 전체 일수: 43일, 주말 수: 12개, 남은 일수: 1개
        long extraCount = diff % 7;

        DayOfWeek extraStartDow = joinDate.plusDays(weekendsCount * 7).getDayOfWeek();
        for (int i = 0; i < extraCount; i++) {
            DayOfWeek dow = DayOfWeek.of((extraStartDow.getValue() - 1 % 7) + 1);
            if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                weekendsCount++;
            }
        }

        Set<MonthDay> holidaySet =
            Arrays.stream(holidays).map(holiday -> MonthDay.parse(holiday, MD)).collect(Collectors.toSet());

        int holidayCount = 0;
        for (MonthDay holiday : holidaySet) {
            for (int year = joinDate.getYear(); year <= resignDate.getYear(); year++) {

                LocalDate holidayDate = holiday.atYear(year);

                if (holidayDate.isAfter(joinDate) && holidayDate.isBefore(resignDate)) {
                    holidayCount++;
                }
            }
        }

        return (int) weekCount - holidayCount;
    }

    /**
     * 주말 검증 (토요일, 일요일)
     */
    private boolean isWeekend(LocalDate localDate) {
        return DayOfWeek.SATURDAY == localDate.getDayOfWeek() || DayOfWeek.SUNDAY == localDate.getDayOfWeek();
    }

    /**
     * 공휴일 검증
     * MM/DD 형식
     * 중복된 날짜나 잘못된 날짜는 주어지지 않는다.
     * @return
     */
    private boolean isHoliday(LocalDate targetDate, Set<String> holidaySet) {
        String targetDateStr = String.format("%02d/%02d", targetDate.getMonthValue(), targetDate.getDayOfMonth());
        return holidaySet.contains(targetDateStr);
    }

    /**
     * LocalDate에는 내부적으로 아래 조건의 윤년을 isLeap으로 검증하기 때문에 해당 메서드는 사용하지않는다.
     * 조건(1) 400으로 나누어 떨어지는 해
     * 조건(2) 4로 나누어 떨어지지만 100으로는 나누어 떨어지지 않는 해
     */
    private boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }
}
