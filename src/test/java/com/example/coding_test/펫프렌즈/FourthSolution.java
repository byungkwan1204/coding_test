package com.example.coding_test.펫프렌즈;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FourthSolution {

    @Test
    void test() {

        int[] waiting = new int[] {1,5,8,2,10,5,4,6,4,8};
        int[] expected = new int[] {1,5,8,2,10,4,6};

        assertThat(solution2(waiting)).isEqualTo(expected);
    }

    private int[] solution(int[] waiting) {

        LinkedHashSet<Integer> set = new LinkedHashSet<>();

        for (int num : waiting) {
            set.add(num);
        }

        return set.stream().mapToInt(i -> i).toArray();
    }

    private int[] solution2(int[] waiting) {

       int maxVal = Arrays.stream(waiting).max().orElse(0);

       boolean[] seen = new boolean[maxVal + 1];
       int[] temp = new int[waiting.length];
       int idx = 0;

       for (int num : waiting) {
           if (!seen[num]) {
               seen[num] = true;
               temp[idx++] = num;
           }
       }

       return Arrays.copyOf(temp, idx);
    }
}
