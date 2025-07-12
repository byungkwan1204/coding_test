package com.example.coding_test.데브시스터즈;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FifthSolution {

    @Test
    void test() {

        // Y = 노란곰젤리, P = 분홍곰젤리, I는 얼음곰젤리
        // 얼음곰젤리로 만들기 위해서는 P는 1회 시약, Y는 2회 시약이 필요

        // 얼음-분홍-노랑-얼음-노랑-분홍
        String bears = "IPYIYP";
        int k = 3;
        int result = 3;

        assertThat(solution(bears, k)).isEqualTo(result);
    }

    private int solution(String bears, int k) {

        int n = bears.length();

        int[] intBears = new int[n];
        for (int i = 0; i < n; i++) {
            intBears[i] = mapChar(bears.charAt(i));
        }

        int[] diff = new int[n+1];
        int result = 0;
        int current = 0;

        for (int i = 0; i < n; i++) {
            current = (current + diff[i]) % 3;
            int currentStatus = (intBears[i] + current) % 3;

            if (currentStatus != 2) {
                int need = (2 - currentStatus + 3) % 3;
                if (i + k > n) {
                    return -1;
                }

                result += need;
                current = (current + need) % 3;
                diff[i] = (diff[i] + need) % 3;
                diff[i + k] = (diff[i + k] - need + 3) % 3;
            }
        }
        return result;
    }

    private static int mapChar(char c) {
        return switch (c) {
            case 'Y' -> 0;
            case 'P' -> 1;
            case 'I' -> 2;
            default -> -1;
        };
    }
}
