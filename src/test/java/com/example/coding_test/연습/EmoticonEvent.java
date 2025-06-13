package com.example.coding_test.연습;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

// 7000 -(7000 * 30 / 100) + 9000 - (9000 * 40 / 100)
public class EmoticonEvent {

    private static final int[] DISCOUNTS = {10, 20, 30, 40};

    static int MAX_EMOTICON_PLUS_COUNT = 0;
    static int MAX_SAELS = 0;

    @Test
    void test() {

        int[][] users = {{40, 10000}, {25, 10000}};
        int[] emoticons = {7000, 9000};

        // 이모티콘 별로 할인율을 저장하는 용도
        int[] discountRates = new int[emoticons.length];

        dfs(0, emoticons, users, discountRates);

        System.out.println("result : " + Arrays.toString(new int[] { MAX_EMOTICON_PLUS_COUNT, MAX_SAELS }));
    }

    private void dfs(int idx, int[] emoticons, int[][] users, int[] discountRates) {

        // 모든 이모티콘에 할인율 선택 완료 (즉, discountRates에 모든 이모티콘의 할인율이 채워졌다.)
        if (idx == emoticons.length) {
            // 할인율  선택이 완료 되었기 때문에 계산을 시작.
            calulate(users, emoticons, discountRates);
            return;
        }

        // 모든 이모티콘에 할인율 선택 미완료로 인한 모든 할인율 순회
        for (int rate : DISCOUNTS) {
            discountRates[idx] = rate;
            dfs(idx + 1, emoticons, users, discountRates);
        }
    }

    private void calulate(int[][] users, int[] emoticons, int[] discountRates) {

        // 이모티콘 플러스 가입 수
        int emoticonPlusCount = 0;
        // 매출
        int sales = 0;

        // 사용자 순회
        for (int[] user : users) {

            // 사용자가 원하는 할인율 --> 이 할인율 이상이여야 사용자가 구매
            int userRate = user[0]; // 40
            // 사용자가 사용가능한 금액 --> 이 금액 이상이면 이모티콘 플러스 가입
            int userLimitPrice = user[1]; // 40

            int sum = 0;

            // 이모티콘 순회
            for (int i = 0; i < emoticons.length; i++) {

                // 7000, 9000
                int emoticon = emoticons[i];

                // {10, 10}, {10, 20} ...
                int discountRate = discountRates[i];

                // 이모티콘 할인율이 사용자가 원하는 할인율 이상인 경우
                if (discountRate >= userRate) {
                    int price = emoticon * (100 - discountRate) / 100;
                    sum += price;
                }
            }

            // 이모티콘 총 할인된 금액이 사용자가 사용가능한 금액 이상인 경우 이모티콘 플러스를 가입한다.
            if (sum >= userLimitPrice) {
                emoticonPlusCount++;
            } else {
                sales += sum;
            }
        }

        // 해당 할인율 조합의 가입자 수가 더 많거나
        // 해당 할인율 조합의 가입자 수가 같고 해당 할인율 조합의 매출이 더 많은 경우
        if (emoticonPlusCount > MAX_EMOTICON_PLUS_COUNT || (emoticonPlusCount == MAX_EMOTICON_PLUS_COUNT && sales > MAX_SAELS)) {
            MAX_EMOTICON_PLUS_COUNT = emoticonPlusCount;
            MAX_SAELS = sales;
        }
    }
}
