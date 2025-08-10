package com.example.coding_test.이베이재팬;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INTEGER;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * X 주차장에서는 자동차를 주차한 시간에 따라 요금을 받는 비공개 요금제를 시행하고 있습니다.
 * - 기본 요금은 b원으로 시작합니다.
 * - 주차한 시간이 a분 지날 때 마다 b원씩 요금이 상승합니다.
 * - 0분부터 a-1분까지 주차한 경우 요금은 b원
 * - a분부터 2a-1분까지 주차한 경우 2xb원,
 * - 2a분부터 3a-1분까지 주차한 경우 3xb원 ... 이런 식으로 계산됩니다.
 * 단 a와 b는 양의 정수이지만 구체적인 값은 공개되지 않았습니다.
 *
 * 문제
 * - 당신은 자동차를 t분간 주차할 예정입니다.
 * - 이전에 주차 시간과 요금이 기록된 데이터가 있습니다.
 * - 이 기록을 바탕으로, 가능한 모든 (a,b) 조합을 찾고,
 * - 그 중에서 자동차를 t분 주차했을 때 내야 하는 요금의 최솟값과 최댓값을 계산하려 합니다.
 * - 만약 가능한 (a,b) 조합이 없으면, -1을 반환합니다.
 *
 * 주차 요금 기록 예시
 * 주차 시간 : 4, 6, 21, 16, 26
 * 요금 : 1000, 1000, 3000, 2000, 3000
 * 1. a = 10, b = 1000일 때, 27분 주차 시 기본 요금 1000원, 이후 10분과 20에 각각 1000원의 요금이 추가되어 총 요금은 3000원 입니다.
 * 2. a = 9, b = 1000일 때, 27분 주차 시 기본 요금 1000원, 이후 9, 18, 27분에 각각 1000원의 요금이 추가되어 총 요금은 4000원 입니다.
 * a, b로 가능한 값은 위 두가지 경우 중 하나 입니다. 따라서 자동차를 27분 주차했을 떄 내야하는 최소 요금은 3000원, 최대 요금은 4000원 입니다.
 *
 * 주차 시간에 따른 요금 기록이 담긴 2차원 정수 배열 fees와 자동차를 주차하려는 시간을 나타내는 정수 t가 매개변수로 주어집니다.
 * 이 떄 자동차를 t분 주차했을 때 내야하는 최소 요금과 최대 요금을 1차원 정수 배열에 순서대로 담아 return 하도록 solution 함수를 완성해주세요.
 * 단, a, b로 가능한 값이 없을 경우 -1을 배열에 담아 return 합니다.
 */
public class FirstSolution {

    @Test
    void test() {

        int[][] fees = new int[][] { {4,1000}, {6,1000}, {21,3000}, {16,2000}, {26, 3000}};
        int t = 27;

        long[] result = new long[] {3000, 4000};

        assertThat(solution(fees, t)).isEqualTo(result);
    }

    private long[] solution(int[][] fees, int t) {

        long maxFee = Long.MIN_VALUE;
        long minFee = Long.MAX_VALUE;

        int maxTime = Arrays.stream(fees)
            .mapToInt(f -> f[0])
            .max()
            .orElse(t);

        for (int a = 1; a <= maxTime; a++) {
            Integer b = getB(fees, a);
            System.out.println("a = " + a + ", b = " + b);
            if (b != null) {
                int myFee = calculateSection(t, a) * b;
                minFee = Math.min(minFee, myFee);
                maxFee = Math.max(maxFee, myFee);
            }
        }

        if (minFee == Long.MAX_VALUE) {
            return new long[] {-1};
        }

        return new long[] {minFee, maxFee};

    }

    private Integer getB(int[][] fees, int a) {

        Integer b = null;

        for (int[] record : fees) {
            int time = record[0];
            int fee = record[1];

            int section = calculateSection(time, a);

            // fee 가 section으로 나누어 떨어져야만 b가 정수일 수 있다.
            if (fee % section != 0) {
                return null;
            }

            int currentB = fee / section;

            // b는 양의 정수 여야 하므로 0이하는 불가능
            if (currentB <= 0) {
                return null;
            }

            if (b == null) {
                b = currentB;
            } else if (!b.equals(currentB)) {
                return null;
            }

            System.out.println("a = " + a + ", b = " + b + ", fee = " + fee + ", section = " + section + ", currentB = " + currentB);
        }

        return b;
    }


    private int calculateSection(int t, int a) {
        return t / a + 1;
    }
}
