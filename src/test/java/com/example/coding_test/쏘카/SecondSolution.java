package com.example.coding_test.쏘카;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Test;

/**
 * n x m 개의 발판이 세로로 n줄, 가로로 m줄 형태로 나열되어있습니다. 가장 왼쪽 위 발판의 좌표는 (1,1), 가장 오른쪽 아래 발판의 좌표는 (n,m)입니다. 한 발판에서 다른 발판으로 이동할 떄는 상하좌우 방향의 인접한 발판으로 이동 가능합니다. 대각선 방향으로는 이동할 수 없습니다.
 * (1,1) 발판과 (n,m)발판을 제외한 모든 발판이 주어진 순서대로 하나씩 사라집니다.
 * 당신은 몇번째 발판이 사라졌을 떄부터 (1,1) 발판에서 출발해 (n,m) 발판으로 이동할 수 없게되는지를 알고 싶습니다.
 * 발판이 나열된 격자의 세로 길이와 가로 길이를 나타내는 정수 n,m과 발판의 좌표를 먼저 사라지는 순서대로 담은 2차원 배열 order가 배개 변수로 주어집니다. 이 때 몇번째 발판이 사라지는 순간 (1,1) 발판에서 (n,m) 발판으로 이동할 수 없게 되는지 return 하도록 solution 함수를 완성해주세요.
 */
public class SecondSolution {


    @Test
    void test() {
        /**
         * 1. n =3, m = 4, order = {{3,2}, {3,1}, {1,4}, {1,2}, {2,4}, {2,3}, {2,2}, {1,3}, {2,1}, {3,3}}, retunr 6
         * 2. n =4, m = 4, order = {{1,2}, {1,3}, {1,4}, {2,4}, {2,3}, {2,2}, {3,2}, {3,3}, {3,4}, {4,3}, {4,2}, {4,1}, {3,1}, {2,1}}, return 10
         * 3. n=4, m = 5, order = {{4,1}, {4,2}, {4,3}, {4,4}, {3,4}, {2,4}, {1,2}, {1,4}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2}, {3,3}, {1,5}, {2,5}, {3,5}, {1,3}}, return 8
         */

        int firstN = 3, firstM = 4;
        int[][] firstOrder = {{3,2}, {3,1}, {1,4}, {1,2}, {2,4}, {2,3}, {2,2}, {1,3}, {2,1}, {3,3}};
        int firstAnswer = 6;

        int secondN = 4, secondM = 4;
        int[][] secondOrder = {{1,2}, {1,3}, {1,4}, {2,4}, {2,3}, {2,2}, {3,2}, {3,3}, {3,4}, {4,3}, {4,2}, {4,1}, {3,1}, {2,1}};
        int secondAnswer = 10;

        int thirdN = 4, thirdM = 5;
        int[][] thirdOrder = {{4,1}, {4,2}, {4,3}, {4,4}, {3,4}, {2,4}, {1,2}, {1,4}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2}, {3,3}, {1,5}, {2,5}, {3,5}, {1,3}};
        int thirdAnswer = 8;

        assertThat(solution(firstN, firstM, firstOrder)).isEqualTo(firstAnswer);
        assertThat(solution(secondN, secondM, secondOrder)).isEqualTo(secondAnswer);
        assertThat(solution(thirdN, thirdM, thirdOrder)).isEqualTo(thirdAnswer);
    }

    private int solution(int n, int m, int[][] order) {

        // false = 존재, true = 사라짐
        boolean[][] disappeared = new boolean[n][m];

        // (1,1) 과 (n,m) 은 order에 없음 (항상 발판이 존재)

        // 모든 발판이 사라질 때까지 반복한다.
        for (int i = 0; i < order.length; i++) {

            // 발판은 1부터 시작되지만 배열에서 사용할 수 있도록 -1하여 0부터 시작되도록 한다.
            int x = order[i][0] - 1;
            int y = order[i][1] - 1;

            // x, y 칸 사라짐 체크
            disappeared[x][y] = true;

            // 몇번째 발판이 사라질 때 더이상 움직일 수 없게되는지 계산
            if (!reachable(n, m, disappeared)) {
                // i는 0번째부터 시작했으므로 + 1 한다.
                return i + 1;
            }
        }

        // 모든 발판이 사라진 뒤에도 (1,1) -> (n,m)으로 갈 수 있을 경우
        return order.length + 1;

    }

    private boolean reachable(int n, int m, boolean[][] disappeared) {

        // 시작과끝이 지워졌으면 애초에 경로가 없음
        if (disappeared[0][0] || disappeared[n-1][m-1]) return false;

        // 도달 여부 배열
        boolean[][] visited = new boolean[n][m];

        Deque<int[]> queue = new ArrayDeque<>();

        // 시작점을 넣고 도달 여부 true 표시
        queue.add(new int[] {0, 0});
        visited[0][0] = true;

        /**
         * BFS 루프 시작
         * 현재 칸을 기준으로 상하좌우 네방향을 확인하면서
         * 이미 사라졌거나 방문한 칸을 제외하고 계속해서 이동한다.
         * 최종적으로 더이상 이동할 칸이 없으면 false를 반환한다.
         * 특정 발판이 사라졌음에도 도착 지점에 도착할 수 있다면 true를 반환하여 다음 사라진 발판에 대해 다시 확인한다.
         */

        while (!queue.isEmpty()) {

            // 현재 칸
            int[] current = queue.poll();

            int x = current[0];
            int y = current[1];

            // 좌표는 1부터 시작이기 때문에 -1을 해준다.
            if (x == n - 1 && y == m - 1)  return true; // 현재 칸이 도착 지점이면 true 반환

            // 상하좌우 네방향 이동 시 사용할 좌표 값 배열
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            // 현재 칸 기준으로 상하좌우 네방향 확인
            for (int[] direction : directions) {

                int nextX = x + direction[0];
                int nextY = y + direction[1];

                // 발판 밖 (발판은 n x m 으로 구성되어있음)
                if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) continue;

                // 상하 좌우 네방향 중 이미 발판이 사라졌거나 이미 방문한 경우
                if (disappeared[nextX][nextY] || visited[nextX][nextY]) continue;

                // 방문 체크
                visited[nextX][nextY] = true;

                // 큐에 추가하여 다음 반복 할 때 이동된 칸을 현재 칸으로 교체한다.
                queue.add(new int[] {nextX, nextY});
            }
        }

        return false;
    }
}
