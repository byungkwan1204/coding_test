package com.example.coding_test.펫프렌즈;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FirstSolution {

    @Test
    void test() {

        int[] arrA = new int[] {7, 8, 10};
        int[] arrB = new int[] {10, 7, 8};

        boolean expected = true;

        assertThat(solution2(arrA, arrB)).isEqualTo(expected);
    }

    private boolean solution2(int[] arrA, int[] arrB) {

        int n = arrA.length;

        for (int i = 0; i < n; i++) {

            boolean isMatch = true;

            for (int j = 0; j < n; j++) {

                if (arrA[(i + j) % n] != arrB[j]) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                return true;
            }
        }

        return false;
    }

    private boolean solution(int[] arrA, int[] arrB) {

        // arrA 배열을 회전시켰을 때 arrB 배열과 같아지는지 비교

        if (arrA.length != arrB.length) {
            return false;
        }

        for (int i = 0; i < arrA.length; i++) {

            if (isSame(arrA, arrB)) {
                return true;
            }

            reLocation(arrA);
        }

        return false;
    }

    private boolean isSame(int[] arrA, int[] arrB) {

        for (int i = 0; i < arrA.length; i++) {
            if (arrA[i] != arrB[i]) {
                return false;
            }
        }

        return true;
    }

    private void reLocation(int[] arrA) {

        int lastValue = arrA[arrA.length - 1];

        for (int i = arrA.length - 1; i >= 1; i--) {
               arrA[i] = arrA[i - 1];
        }

        arrA[0] = lastValue;
    }
}
