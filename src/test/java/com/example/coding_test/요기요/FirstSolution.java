package com.example.coding_test.요기요;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class FirstSolution {

    @Test
    void test() {

//        String s = "123-123-123";
        String ss = "123 123 123";
        String sss = "123-abc-123";

        boolean result = solution(sss);

        System.out.println("result = " + result);
    }

    private boolean solution(String s) {

        String[] split = s.split("-");

        if (split.length != 3 ) {
            return false;
        }

        for (String str : split) {
            if (str.length() != 3 || !str.matches("\\d+")) {
                return false;
            }
        }

        return true;
    }
}
