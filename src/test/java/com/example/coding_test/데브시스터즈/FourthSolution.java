package com.example.coding_test.데브시스터즈;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class FourthSolution {

    @Test
    void test() {
        int[] s1 = new int[] {4, 99, 2, 6, 7, 13, 88, 76};
        int[] s2 = new int[] {6,88,13,4,99,2,7};
        int result = 76;

        assertThat(solution2(s1, s2)).isEqualTo(result);
    }

    private int solution2(int[] s1, int[] s2) {

        Set<Integer> set = new HashSet<>();

        for (int num : s2) {
            set.add(num);
        }

        for (int num : s1) {
            if (!set.contains(num)) {
                return num;
            }
        }

        return 0;
    }

    private int solution(int[] s1, int[] s2) {

        Arrays.sort(s1);
        Arrays.sort(s2);

        for (int i = 0; i < s2.length; i++) {
            if (s1[i] != s2[i]) {
                return s1[i];
            }
        }

        return s1[s1.length - 1];
    }


}
