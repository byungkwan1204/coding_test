package com.example.programmers.ai_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Third {

    @DisplayName("△△ 게임대회가 개최되었습니다. " +
                 "이 대회는 N명이 참가하고, 토너먼트 형식으로 진행됩니다. " +
                 "N명의 참가자는 각각 1부터 N번을 차례대로 배정받습니다. " +
                 "그리고, 1번↔2번, 3번↔4번, ... , N-1번↔N번의 참가자끼리 게임을 진행합니다. " +
                 "각 게임에서 이긴 사람은 다음 라운드에 진출할 수 있습니다. " +
                 "이때, 다음 라운드에 진출할 참가자의 번호는 다시 1번부터 N/2번을 차례대로 배정받습니다. " +
                 "만약 1번↔2번 끼리 겨루는 게임에서 2번이 승리했다면 다음 라운드에서 1번을 부여받고, " +
                 "3번↔4번에서 겨루는 게임에서 3번이 승리했다면 다음 라운드에서 2번을 부여받게 됩니다. " +
                 "게임은 최종 한 명이 남을 때까지 진행됩니다." +
                 "이때, 처음 라운드에서 A번을 가진 참가자는 경쟁자로 생각하는 B번 참가자와 몇 번째 라운드에서 만나는지 궁금해졌습니다. " +
                 "게임 참가자 수 N, 참가자 번호 A, 경쟁자 번호 B가 함수 solution의 매개변수로 주어질 때, " +
                 "처음 라운드에서 A번을 가진 참가자는 경쟁자로 생각하는 B번 참가자와 몇 번째 라운드에서 만나는지 return 하는 solution 함수를 완성해 주세요. " +
                 "단, A번 참가자와 B번 참가자는 서로 붙게 되기 전까지 항상 이긴다고 가정합니다.")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int n, int a, int b, int expected) {
        assertThat(solution(n, a, b)).isEqualTo(expected);
    }

    private int solution(int n, int a, int b) {

        int round = 0;

        // A와 B가 붙을 때 까지 반복
        while(a != b) {

            /*
             * 1 vs 2 는 그룹 1이고 3 vs 4는 그룹 2 이다.
             * 2가 이기면 2는 그룹 1이고, 4가 이기면 4는 그룹 2이다.
             * 만약 + 1을 하지 않을 경우
             * 3 vs 4 에서 3이 이길 경우 3은 그룹 1이 되어 버린다.
             * 따라서 각각의 승리한 번호에 +1을 함으로써 번호에 맞는 그룹 번호가 매겨진다.
             */
            a = (a + 1) / 2;
            b = (b + 1) / 2;

            round++;
        }

        return round;
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(8, 4, 7, 3));
    }
}
