package com.example.programmers.ai_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import net.bytebuddy.asm.MemberSubstitution.Argument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Second {

    int answer = 0;

    @DisplayName("주어진 숫자 중 3개의 수를 더했을 때 소수가 되는 경우의 개수를 구하려고 합니다. " +
                 "숫자들이 들어있는 배열 nums가 매개변수로 주어질 때, " +
                 "nums에 있는 숫자들 중 서로 다른 3개를 골라 더했을 때 " +
                 "소수가 되는 경우의 개수를 return 하도록 solution 함수를 완성해주세요.")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int[] nums, int expected) {
        assertThat(solution(nums)).isEqualTo(expected);
    }

    private int solution(int[] nums) {
        dfs(nums, 0, 0, 0);
        return answer;
    }

    /**
     * DFS란, 깊이 우선 탐색 (재귀)
     * @param nums
     * @param depth
     * @param start
     * @param sum
     */
    private void dfs(int[] nums, int depth, int start, int sum) {

        // 숫자 3개가 더해졌을 경우
        if (depth == 3) {

            // 소수인 경우
            if (isPrime(sum)) {
                answer++;
            }

            return;
        }

        // 재귀 (start 파라미터로 index를 받지않으면 계속 start index가 0이다.)
        for (int i = start; i < nums.length; i++) {
            dfs(nums, depth + 1, i + 1, sum + nums[i]);
        }
    }

    /**
     * 소수인지 판별하는 함수
     * 소수 : 1과 자기 자신으로만 나누어 떨어지는 수
     * 2부터가 소수이다.
     */
    private boolean isPrime(int num) {

        // 2보다 작으면 소수가 아님
        if (num < 2) {
            return false;
        }

        /*
         * isPrime(13)을 예로 들어보자.
         * Math.sqrt : num의 제곱근을 구하는 함수 ex) Math.sqrt(13) = 3.6055.... (double)
         * i = 2 부터 i = 12까지 하나씩 확인하면 효율성이 떨어진다.
         * 따라서, i <= Math.sqrt(13) 까지만 확인한다.
         */
        for (int i = 2; i <= Math.sqrt(num); i++) {

            // 1과 자기 자신으로만 나누어 떨어져야하기 때문에 다른 숫자로 나누어 떨어지면 안된다.
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(new int[] {1,2,3,4}, 1),
                         Arguments.of(new int[] {1,2,7,6,4}, 4));
    }
}
