package com.example.coding_test.연습;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class HotelBooking {

    @Test
    void test() {

        String[][] bookTimes = new String[][] { {"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"} };

        int[] starts = new int[bookTimes.length];
        int[] ends = new int[bookTimes.length];

        // 대실 시작, 종료 시간을 각각 배열에 저장
        for (int i = 0; i < bookTimes.length; i++) {
            starts[i] = convertToTime(bookTimes[i][0]);
            ends[i] = convertToTime(bookTimes[i][1]) + 10;  // 청소 시간 10분 추가
        }

        // 모든 예약의 시작 시각을 오름차순으로 정렬
        Arrays.sort(starts);

        // 모든 예약의 종료+청소 시각을 오름차순으로 정렬
        Arrays.sort(ends);

        int rooms = 0;
        int maxRooms = 0;

        int s = 0;
        int e = 0;

        // 모든 예약의 시작 시간을 모두 순회할 때 까지 반복
        while (s < bookTimes.length) {
            // 아직 방이 종료되지 않았거나 청소 중인 경우
            if (starts[s] < ends[e]) {
                rooms++;    // 새로운 방 필요
                s++;        // 다음 대실 시작 시간 확인을 위함

            // 예약이 종료된 경우
            } else {
                rooms--;    // 방 회수 (추가된 새로운 방을 하나 제거)
                e++;
            }

            maxRooms = Math.max(maxRooms, rooms);
        }

        System.out.println("maxRooms = " + maxRooms);
    }

    private int convertToTime(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }
}
