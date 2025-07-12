package com.example.coding_test.펫프렌즈;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ThirdSolution {

    @Test
    void test() {

        int s = 99;
        int expected = 10;

        assertThat(solution(s)).isEqualTo(expected);
    }

    private int solution(int s) {

        int min = Integer.MAX_VALUE;

        // Math.sqrt 은 제곱근을 구하는 함수
        // s = 6 인 경우 2 리턴, s = 10 인경우 3 리턴

        double d = Math.sqrt(s);
        System.out.println("d = " + d);

        for (int i = 1; i <= Math.sqrt(s); i++) {

            if (s % i == 0) {

                int width = i;  // 1, 2
                int height = s / i; // 6/1 = 6, 6/2=3

                int meter = 2 * (width + height);

                // 결국 1x6, 2x3 직사각형이 생성

                System.out.println("meter = " + meter);

                // 둘레 = 2 * (가로 + 세로)
                min = Math.min(min, meter);
            }
        }

        return min;
    }
}
