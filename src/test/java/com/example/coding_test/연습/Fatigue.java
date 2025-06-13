package com.example.coding_test.연습;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Fatigue {

    int maxCount = 0;

    @Test
    void test() {

        int k = 80;
        int[][] dungeons = new int[][]{ {80, 20}, {50,40}, {30,10}};

        // 필요한 피로도가 가장 적은 순서로 정렬 (동일한 필요 피로도인 경우 소모 피로도가 적은 순서)
        Arrays.sort(dungeons, (a, b) -> {
            if (a[0] != b[0]) { return Integer.compare(a[0], b[0]); }
            return Integer.compare(a[1], b[1]);
        });

        boolean[] visited = new boolean[dungeons.length];

        dfs(k, dungeons, visited, 0);

        System.out.println("maxCount = " + maxCount);
    }

    private void dfs(int currentFatigue, int[][] dungeons, boolean[] visited, int count) {

        maxCount = Math.max(maxCount, count);

        for (int i = 0; i < dungeons.length; i++) {

            int required = dungeons[i][0];
            int use = dungeons[i][1];

            // 탐험한 적 없고 보유한 피로도가 필요한 피로도보다 같거나 클 경우
            if (!visited[i] && currentFatigue >= required) {
                visited[i] = true;
                int remain = currentFatigue - use;
                dfs(remain, dungeons, visited, count + 1);
                visited[i] = false; // 백트래킹

                System.out.println("index : " + i);
                System.out.println("dungeon : " + Arrays.toString(dungeons[i]));
            }
        }
    }
}
