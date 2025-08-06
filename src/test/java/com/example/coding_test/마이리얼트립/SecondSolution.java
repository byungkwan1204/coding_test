package com.example.coding_test.마이리얼트립;

import java.sql.Timestamp;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
 * Deque 를 사용해서 오래된 요청을 제거하면서 새로운 요청을 저장한다.
 */
public class SecondSolution {

    private static final Map<String, Deque<Integer>> map = new HashMap<>();

    private static final int MAX_REQUEST = 10;
    private static final int AVAILABLE_TIME = 60;

    @Test
    void test() {

        String[] args = new String[] {
           "1728278981,/offers/63685,211.10.10.90",
           "1728278982,/offers/63686,211.10.10.90",
           "1728278983,/offers/63687,211.10.10.90",
           "1728278984,/offers/63688,211.10.10.90",
           "1728278984,/offers/63700,192.168.10.11",
           "1728278985,/offers/63689,211.10.10.90",
           "1728278986,/offers/63690,211.10.10.90",
           "1728278987,/offers/63690,192.168.10.10",
           "1728278987,/offers/63691,211.10.10.90",
           "1728278988,/offers/63692,211.10.10.90",
           "1728278989,/offers/63693,211.10.10.90",
           "1728278990,/offers/63694,211.10.10.90",
           "1728278991,/offers/63695,211.10.10.90",
           "1728278999,/offers/63696,211.10.10.90",
           "1728279000,/offers/63697,211.10.10.90",
           "1728279001,/offers/63698,211.10.10.90",
           "1728288888,/offers/63699,211.10.10.90",
           "1728288890,/offers/63700,211.10.10.90",
           "1728288891,/offers/63701,211.10.10.90",
           "1728288892,/offers/63702,211.10.10.90"
        };

        for (String arg : args) {
            System.out.println("solution() = " + isAbusing(arg));
        }

    }

    private int isAbusing(String arg) {

        int timestamp = Integer.parseInt(arg.split(",")[0]);
        String url = arg.split(",")[1];
        String ip = arg.split(",")[2];

        Deque<Integer> deque = map.computeIfAbsent(ip, c -> new LinkedList<>());

        // 60초 이전 요청들은 제거 (pollFirst는 맨 앞 인덱스를 꺼낸다. 없으면 null 반환, removeFirst도 있는데 이건 없으면 NoSuchException 이 발생한다.)
        while (!deque.isEmpty() && deque.peekFirst() <= timestamp - AVAILABLE_TIME) {
            deque.pollFirst();
        }

        // 맨 뒤에 요청을 추가한다.
        deque.addLast(timestamp);

        return deque.size() > MAX_REQUEST ? 1 : 0;
    }
}
