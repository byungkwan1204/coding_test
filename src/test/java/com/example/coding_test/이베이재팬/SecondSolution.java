package com.example.coding_test.이베이재팬;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import org.junit.jupiter.api.Test;

public class SecondSolution {

    @Test
    void test() {

        int m = 1000000;
        int n = 2;
        String[] suspects = new String[] {"a", "b"};
        String[] transactions = new String[] {"a c 1000000", "c b 1000000", "c d 1000000", "c e 1000000", "d e 1000000"};

        String[] result = new String[] {"a", "b", "c"};

        assertThat(solution(n, m, suspects, transactions)).isEqualTo(result);
    }

    private String[] solution(int n, int m, String[] suspects, String[] transactions) {

        Set<String> suspectSet = new HashSet<>(Arrays.asList(suspects));

        Map<String, Integer> countMap = new HashMap<>();
        Map<String, Integer> amountMap = new HashMap<>();

        Set<String> resultSet = new HashSet<>(suspectSet);

        for (String s : suspects) {
            countMap.put(s, 0);
            amountMap.put(s, 0);
        }

        for (String transaction : transactions) {

            String[] parts = transaction.split(" ");
            String from = parts[0];
            String to = parts[1];
            int amount = Integer.parseInt(parts[2]);

            // from, to 중 적어도 한쪽이 의심 계좌이면 거래 기록 누적
            boolean isFromSuspect = suspectSet.contains(from);
            boolean isToSuspect = suspectSet.contains(to);

            // 의심 계좌 <-> 비의심 계좌 거래일 때만 누적


            if (isFromSuspect && !isToSuspect) {
                // to 계좌 거래 누적
                countMap.put(to, countMap.getOrDefault(to, 0) + 1);
                amountMap.put(to, amountMap.getOrDefault(to, 0) + amount);
            }

            if (!isFromSuspect && isToSuspect) {
                // from 계좌 거래 누적
                countMap.put(from, countMap.getOrDefault(from, 0) + 1);
                amountMap.put(from, amountMap.getOrDefault(from, 0) + amount);
            }
        }

        for (String account : countMap.keySet()) {
            int count = countMap.get(account);
            int amount = amountMap.get(account);

            if (count >= n && amount >= m) {
                resultSet.add(account);
            }
        }

        List<String> result = new ArrayList<>(resultSet);
        Collections.sort(result);

        return result.toArray(new String[0]);
    }
}
