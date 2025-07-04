package com.example.coding_test.연습;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task {

    @DisplayName("과제를 받은 루는 다음과 같은 순서대로 과제를 하려고 계획을 세웠습니다.\n" + "\n" +
                 "과제는 시작하기로 한 시각이 되면 시작합니다.\n" +
                 "새로운 과제를 시작할 시각이 되었을 때, 기존에 진행 중이던 과제가 있다면 진행 중이던 과제를 멈추고 새로운 과제를 시작합니다.\n" +
                 "진행중이던 과제를 끝냈을 때, 잠시 멈춘 과제가 있다면, 멈춰둔 과제를 이어서 진행합니다.\n" +
                 "만약, 과제를 끝낸 시각에 새로 시작해야 되는 과제와 잠시 멈춰둔 과제가 모두 있다면, 새로 시작해야 하는 과제부터 진행합니다.\n" +
                 "멈춰둔 과제가 여러 개일 경우, 가장 최근에 멈춘 과제부터 시작합니다.\n" +
                 "과제 계획을 담은 이차원 문자열 배열 plans가 매개변수로 주어질 때, 과제를 끝낸 순서대로 이름을 배열에 담아 return 하는 solution 함수를 완성해주세요.")
    @Test
    void test() {

        String[][] inputs= new String[][] {{"science", "12:40", "50"}, {"music", "12:20", "40"}, {"history", "14:00", "30"}, {"computer", "12:30", "100"}};
        String[] expected = new String[] {"science", "history", "computer", "music"};

        assertThat(solution(inputs)).isEqualTo(expected);
    }

    private String[] solution(String[][] inputs) {

        List<String> result = new ArrayList<>();

        Arrays.sort(inputs, Comparator.comparing(a -> toMinutes(a[1])));

        Stack<int[]> stack = new Stack<>();

        for (int i = 0 ; i < inputs.length ; i++) {

            String name = inputs[i][0];
            int startTime = toMinutes(inputs[i][1]);
            int playTime = Integer.parseInt(inputs[i][2]);

            int nextTime = i + 1 < inputs.length ? toMinutes(inputs[i + 1][1]) : Integer.MAX_VALUE;

            // 현 과제가 다음 과제 시작 시간 전에 끝날 경우
            if (startTime + playTime <= nextTime) {
                result.add(name);

                int now = startTime + playTime;

                // 다음 과제 시작 시간 전까지의 공백 시간 동안만 이전 과제 진행
                while(!stack.isEmpty() && nextTime > now) {

                    int[] pop = stack.pop();

                    // 못다한 과제의 남은 수행 시간
                    int stackPlayTime = pop[1];

                    // 남은 수행 시간을 더해도 다음 과제 시작 시간 이전에 끝날 경우
                    if (now + stackPlayTime <= nextTime) {
                        result.add(inputs[pop[0]][0]);
                        now += stackPlayTime;

                    // 남은 수행 시간 동안 과제를 끝내지 못했을 경우
                    } else {
                        stack.push(new int[] {pop[0], now + stackPlayTime - nextTime});
                        now = nextTime;
                    }
                }

            // 현 과제가 다음 과제 시작 시간 전에 끝나지 않은 경우
            } else {
                // i = 0 : music, 30
                // i = 1 : computer, 90
                // i = 3 : science, 0 | computer, 60
                // i = 4 : history, 0 | computer, 0, | music, 0
                stack.push(new int[] {i, (startTime + playTime - nextTime)});
            }
        }

        while (!stack.isEmpty()) {

            int[] pop = stack.pop();

            result.add(inputs[pop[0]][0]);
        }


        return result.toArray(new String[0]);
    }

    private int toMinutes(String time) {
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 60  + Integer.parseInt(t[1]);
    }
}
