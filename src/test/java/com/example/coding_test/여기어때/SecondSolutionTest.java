package com.example.coding_test.여기어때;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SecondSolutionTest {

    @Test
    void test() {

        int[] first_classes = {30, 29, 35, 25, 20, 30, 20, 45, 48};
        int first_k = 3;
        int first_result = 95;
        int[] second_classes = {100, 1, 50, 77, 4854};
        int second_k = 5;
        int second_result = 4854;
        int[] third_classes = {1056, 999, 74};
        int third_k = 1;
        int third_result = 2129;

        int n = 300000;
        int[] fourth_classes = new int[n];
        Arrays.fill(fourth_classes, 1);
        int fourth_k = 2;

        long fourth_result = 150000L;

        assertThat(solution(fourth_classes, fourth_k)).isEqualTo(fourth_result);
    }


    private long solution(int[] classes, int k) {

        long min = Arrays.stream(classes).asLongStream().max().orElse(0);
        long max = Arrays.stream(classes).asLongStream().sum();

        while (min <= max) {

            // 그리디 방식으로 분할
            if (isGreedy(classes, k, min)) {
                return min;
            }
            min++;
        }

        return max;
    }

    private boolean isGreedy(int[] classes, int k, long max) {

        int server = 1;
        long sum = 0;

        for (int c : classes) {

            if (c > max) { return false;}

            if (sum + c <= max) {
                sum += c;
            } else {
                server++;
                sum = c;
                if (server > k) {
                    return false;
                }
            }
        }

        return true;
    }


}
