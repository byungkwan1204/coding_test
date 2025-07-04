package com.example.coding_test.연습;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EatTheLand {

    @DisplayName("땅따먹기 게임을 하려고 합니다. 땅따먹기 게임의 땅(land)은 총 N행 4열로 이루어져 있고, 모든 칸에는 점수가 쓰여 있습니다. " +
                 "1행부터 땅을 밟으며 한 행씩 내려올 때, 각 행의 4칸 중 한 칸만 밟으면서 내려와야 합니다. 단, 땅따먹기 게임에는 한 행씩 내려올 때, 같은 열을 연속해서 밟을 수 없는 특수 규칙이 있습니다.\n" +
                 "\n" + "예를 들면,\n" + "\n" + "| 1 | 2 | 3 | 5 |\n" + "\n" + "| 5 | 6 | 7 | 8 |\n" + "\n" + "| 4 | 3 | 2 | 1 |\n" + "\n" +
                 "로 땅이 주어졌다면, 1행에서 네번째 칸 (5)를 밟았으면, 2행의 네번째 칸 (8)은 밟을 수 없습니다.\n" + "\n" +
                 "마지막 행까지 모두 내려왔을 때, 얻을 수 있는 점수의 최대값을 return하는 solution 함수를 완성해 주세요. " +
                 "위 예의 경우, 1행의 네번째 칸 (5), 2행의 세번째 칸 (7), 3행의 첫번째 칸 (4) 땅을 밟아 16점이 최고점이 되므로 16을 return 하면 됩니다.")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(int expected, int[][] land) {
        assertThat(solution2(land)).isEqualTo(expected);
    }

    private int solution2(int[][] land) {

        for (int i = 1; i < land.length; i++) {
            land[i][0] += Math.max(land[i-1][1], Math.max(land[i-1][2], land[i-1][3]));
            land[i][1] += Math.max(land[i-1][0], Math.max(land[i-1][2], land[i-1][3]));
            land[i][2] += Math.max(land[i-1][0], Math.max(land[i-1][1], land[i-1][3]));
            land[i][3] += Math.max(land[i-1][0], Math.max(land[i-1][1], land[i-1][2]));
        }

        return Arrays.stream(land[land.length-1]).max().orElse(0);
    }

    private int solution(int[][] land) {

        // 행 반복 (0번째 행은 더할 값이 없기 때문에 건너뛴다.)
        for (int i = 1; i < land.length; i++) {

            // 열 반복
            for (int j = 0; j < 4; j++) {

                int max = 0;

                // 이전 열 반복
                for (int k = 0; k < 4; k++) {

                    // 이전 열과 현재 열의 인덱스가 동일하지 않을 경우에만
                    if (k != j) {
                        // 이전 열의 가장 큰 값 저장
                        max = Math.max(max, land[i - 1][k]);
                    }
                }

                // 이전 행의 가장 큰 값을 현재 열에 더해준다.
                land[i][j] += max;

                System.out.println("land = " + land[i][j]);
            }
        }

        // 마지막 행이 곧 최종 값이므로 마지막 행의 가장 큰 값을 반환한다.
        return Arrays.stream(land[land.length - 1]).max().orElse(0);
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(16, new int[][] { {1,2,3,5}, {5,6,7,8}, {4,3,2,1}}));
    }
}
