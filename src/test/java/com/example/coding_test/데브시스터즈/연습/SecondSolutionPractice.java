package com.example.coding_test.데브시스터즈.연습;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SecondSolutionPractice {

    @DisplayName("블루베리 새가 최대로 들 수 있는 선물의 개수가 정수 Capacity로, 배달 경로가 배열 routes로 입력이 주어집니다. " +
                 "routes의 각 요소는 [giftNum, from, to]의 형태일 떄, 블루베리 새가 모든 경로를 단방향으로 거쳐 배달할 수 있을까요?" +
                 "예시는 다음과 같습니다." +
                 "capacity = 9, routes = [[3,2,6], [5,1,4], [1,7,13]] 이라면 블루베리 새는 한번에 선물을 최대 9개 까지 들 수 있으므로 다음과 같이 배달합니다." +
                 "1. 1번 지점에서 5개의 선물을 픽업합니다." +
                 "2. 2번 지점에서 3개의 선물을 픽업합니다." +
                 "3. 4번 지점에서 5개의 선물을 배달 완료합니다." +
                 "4. 6번 지점에서 3개의 선물을 배달 완료합니다." +
                 "5. 7번 지점에서 1개의 선물을 픽업합니다." +
                 "6. 13번 지점에서 1개의 선물을 배달 완료합니다." +
                 "블루베리 새는 4번 지점에 도착할 떄까지 총 8개의 선물을 들고 이동해야 합니다." +
                 "1번 지점에서 5개의 선물을, 2번 지점에서 3개의 선물을 픽업 했기 떄문입니다." +
                 "이때 블루베리 새는 선물을 최대 9개까지 들수 있으므로 능히 옮길 수 있습니다.\n" +
                 "7번 지점에서 13번 지점까지는 1개의 선물만 들공 있으면 되므로, 이 역시 문제가 없습니다." +
                 "따라서 블루베리 새는 모든 경로를 단방향으로 거쳐 선물을 배달할 수 있으므로 return 하는 값은 true 입니다." +
                 "블루베리 새가 최대로 들 수 있는 선물의 개수가 정수 capacity, 배달 경로가 배열 routes로 입력이 주어질 떄, " +
                 "블루베리 새가 선물을 배달할 수 있는 여부를 return 하도록 solution 함수를 완성해주세요." +
                 "입출력 예시 : capacity : 9, routes = [[3,2,6], [5,1,4], [1,7,13]], return : true")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int capacity, int[][] routes, boolean expected) {
        assertThat(solution(capacity, routes)).isEqualTo(expected);
    }

    public boolean solution(int capacity, int[][] routes) {
        
        /**
         * 	1.	구간별로 선물 픽업(from) → +giftNum, 배달(to) → -giftNum 이벤트를 만든다.
         * 	2.	좌표 순으로 정렬한 뒤 누적합을 계산하면서, capacity를 초과하면 false
         * 	3.	끝까지 capacity 초과하지 않으면 true
         */

        // 경로별 gift 의 변화를 기록할 리스트
        Map<Integer, Integer> giftMap = new HashMap<>();

        for (int[] route : routes) {

            int giftNum = route[0];
            int from = route[1];
            int to = route[2];

            // from 지점에서 gift 픽업 (+giftNum)
            giftMap.put(from, giftMap.getOrDefault(from,0) + giftNum);

            // to 지점에서 gift 배달 (-giftNum);
            giftMap.put(to, giftMap.getOrDefault(to,0) - giftNum);
        }

        // 경로 오름차순 정렬
        List<Integer> sortedGiftList = new ArrayList<>(giftMap.keySet());

        int giftNum = 0;
        for (int giftKey : sortedGiftList) {

            // 보유하고있는 선물 누적 수
            giftNum += giftMap.get(giftKey);
            if (giftNum > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    public static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(9, new int[][] {{3,2,6}, {5,1,4}, {1,7,13}}, true));
    }
}
