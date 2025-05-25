package com.example.coding_test.카카오모빌리티;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

// 깊은 탐색
public class ThirdSolution {

    @Test
    void test() {

        int[] T = {0, 0, 0, 0, 2, 3, 3};
        int[] A = {2, 5, 6};

        Set<Integer> learned = new HashSet<>();

        for (int skill : A) {

            int currentSkill = skill;

            while (!learned.contains(currentSkill)) {

                learned.add(currentSkill);  // currentSkill = 2, 0, 5, 3, 0, 6, 3, 0

                if (T[currentSkill] == currentSkill) {
                    break;
                }

                currentSkill = T[currentSkill];
            }
        }

        System.out.println("learned = " + learned.size());
    }
}
