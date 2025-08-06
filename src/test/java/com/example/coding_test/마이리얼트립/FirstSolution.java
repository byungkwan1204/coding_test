package com.example.coding_test.마이리얼트립;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 카데인 알고리즘
 * 배열에서 음수를 제외한 양수 값들의 합
 */
public class FirstSolution {

    @Test
    void test() {

        String[] arg = new String[] {"3","2","-2","5","-1","7","9"};

        assertThat(solution(arg)).isEqualTo(17);
    }

    private int solution(String[] args) {
        return maxPulseSum(args);
    }

    private int maxPulseSum(String[] args) {
        return Math.max(
            kadaneWithPulse(args, true), kadaneWithPulse(args, false));
    }

    private int kadaneWithPulse(String[] args, boolean flag) {

        int max = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = 0; i < args.length; i++) {

            int arg = Integer.parseInt(args[i]);

            int pulse = (i % 2 == 0) == flag ? 1 : -1;

            int multiply = pulse * arg;

            sum = Math.max(multiply, sum + multiply);
            max = Math.max(max, sum);
        }

        return max;
    }
}
