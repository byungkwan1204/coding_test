package com.example.coding_test.데브시스터즈;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SixthSolution {

    @Test
    void test() {

        int D = 1;
        int[] cakes = new int[]{1,2,3};
        int result = 3;

        assertThat(solution(D,cakes)).isEqualTo(result);
    }

    private int solution(int D, int[] cakes) {

        int total = 0;

        for (int i = D; i >= 0; i--) {

            for (int j = 0; j < cakes.length; j++) {
                if (cakes[j] > 0) {
                    cakes[j]--;
                    total++;
                    break;
                }
            }

            for (int j = 0; j < cakes.length - 1; j++) {
                cakes[j] = cakes[j + 1];
            }
            cakes[cakes.length - 1] = 0;
        }

        for (int cake : cakes) {
            if (cake > 0) {
                return -1;
            }
        }

        return total;
    }
}
