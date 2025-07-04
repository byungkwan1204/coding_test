package com.example.coding_test.데브시스터즈.연습;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ThirdSolutionPractice {

    @DisplayName("벚꽃맛 쿠키는 소풍가는것을 참 좋아합니다." +
                 "소풍에 가면 친구들과 홍차를 마시고 젤리를 나누어 먹으며 수다를 떨며 놀곤합니다. " +
                 "그래서 소풍을 갈 떄 마다 젤리를 쉽게 가져갈 수 있도록 주머니에 미리 나누어 담아둡니다. " +
                 "벚꽃맛 쿠키는 젤리에 대해서는 까다로운 입맛을 가지고 있어서, 다섯가지 종류의 젤리만을 준비해놓습니다." +
                 "어느날, 언제나와 같이 소풍을 가려고 준비하던 벚꽃맛 쿠키는 소풍에 여느떄 보다 많은 친구들이 참석하게 된다는 것을 알게 되었습니다." +
                 "그래서 최대한 많은 젤리 주머니를 가져가고 싶었던 동시에 모든 주머니의 젤리를 꺼내서 모았을 떄, " +
                 "어떤 한가지 맛의 젤리를 다른 젤리의 개수를 모두 합친 것보다 많이 가져가고 싶어졌습니다. \n" +
                 "벚꽃맛 쿠키는 젤리 주머니를 담아가야 했기 때문에 얼마나 많은 젤리 주머니를 가져가야할지 문득 궁금해졌습니다." +
                 "얼마나 많은 젤리 주머니를 가져갈 수 있는지 solution을 완성해주세요." +
                 "예시는 다음과 같습니다." +
                 "['cab', 'adaaa', 'e'] 라는 입력값이 있고" +
                 "여기서 세개의 주머니를 선택하면 a 젤리가 5개, 나머지 젤리는 4개가 되므로 조건에 부합합니다." +
                 "그러므로 return 값은 3입니다. ")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int expected, String[] pouches) {
        assertThat(solution(pouches)).isEqualTo(expected);
    }

    private int solution(String[] pouches) {

        char[] baseJellys = new char[] {'a', 'b', 'c', 'd', 'e'};

        int maxCount = 0;

        // 기준 젤리와 젤리 주머니 안에 있는 모든 젤리들을 비교한다.
        for (char baseJelly : baseJellys) {

            List<Integer> scores = new ArrayList<>();

            for (String pouch : pouches) {

                int[] count = new int[baseJellys.length];

                for (char jelly : pouch.toCharArray()) {
                    count[jelly - 'a']++;
                }


                // 'cab' : count = [1,1,1,0,0]
                // 'adaaa' : count = [4,0,0,1,0]
                // 'e' : count = [0,0,0,0,1]

                // 젤리 주머니 안에 있는 모든 젤리 수의 수 (기준 젤리 수 포함)
                int total = Arrays.stream(count).sum();

                // 기준 젤리 수 - 나머지 젤리 수
                // total은 기준 젤리 수도 포함되어있기 때문에 total - count[baseJelly - 'a'] 를 해준다.
                int score = count[baseJelly - 'a'] - (total - count[baseJelly - 'a']);

                // 'cab' : count = [0,1,1,0,0]
                // 'adaaa' : count = [4,0,0,1,0]
                // 'e' : count = [0,0,0,0,1]

                scores.add(score);
            }

            // 기준 젤리 수 - 나머지 젤리 수 가 많은 순 ( = 기준 젤리가 유리한 순)
            scores.sort(Comparator.reverseOrder());

            int sum = 0;
            int picked = 0;

            // a ~ e 까지 기준 젤리를 시도하면서 기준젤리가 우세한 수 저장
            for (int score : scores) {
                sum += score;
                if (sum > 0) {
                    picked++;
                } else {
                    break;
                }
            }

            maxCount = Math.max(maxCount, picked);
        }

        return maxCount;
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(3, new String[] {"cab", "adaaa", "e"}));
    }


}
