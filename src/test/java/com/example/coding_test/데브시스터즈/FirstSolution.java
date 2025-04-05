package com.example.coding_test.데브시스터즈;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FirstSolution {

    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int score, int expected) {
        assertThat(solution(score)).isEqualTo(expected);
    }

    private int solution(int score) {

        int[] divides = {100, 50, 5, 1};

        int count = 0;

        for (int divide : divides) {
            count += score / divide;
            score = score % divide;
        }

        return count;
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(156, 4));
    }
}
