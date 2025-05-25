package com.example.coding_test.카카오모빌리티;

import org.junit.jupiter.api.Test;

public class SecondSolution {

    @Test
    void test() {

        int[] A = {3, 2, 3, 2, 3};

        int maxLength = 1;

        for (int i = 0; i < A.length; i++) {

            int even = i % 2 == 0 ? A[i] : Integer.MIN_VALUE;
            int odd = i % 2 == 1 ? A[i] : Integer.MIN_VALUE;

            for (int j = i; j < A.length; j++) {

                if (j % 2 == 0 && A[j] != even || j % 2 == 1 && A[j] != odd) {
                    break;
                }

                maxLength = Math.max(maxLength, j - i + 1);
            }
        }

        System.out.println("maxLength = " + maxLength);
    }
}
