package com.example.coding_test.펫프렌즈;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SecondSolution {

    @Test
    void test() {

        String s = "aabbbccd";
        int expected = 2;

        assertThat(solution(s)).isEqualTo(expected);
    }

    private int solution(String s) {

        // s 문자열에 포함된 문자가 홀수개인 문자를 추출

        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            System.out.println("map = " + map);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int result = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {

            if (entry.getValue() % 2 != 0) {
                result++;
            }
        }

        return result;
    }
}
