package com.example.coding_test.연습;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class ParkingFee {

    @Test
    void test() {

        int[] fees = new int[] { 180, 5000, 10, 600};
        String[] records = new String[] {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};

        int baseTime = fees[0];
        int basePrice = fees[1];
        int unitTime = fees[2];
        int unitPrice = fees[3];

        // 입차 기록
        Map<String, Integer> inMap = new HashMap<>();

        // 결과 기록 (출차 시간 - 입차 시간)
        Map<String, Integer> resultMap = new HashMap<>();

        for (String record : records) {

            String[] splits = record.split(" ");

            int time = convertToTime(splits[0]);
            String carNum = splits[1];
            String inOut = splits[2];

            if ("IN".equals(inOut)) {
                inMap.put(carNum, time);
            } else {
                int inTime = inMap.remove(carNum);
                int duration = time - inTime;

                // 입차, 출차, 입차 의 순서인 경우 resultMap에 해당 키가 존재하므로 값을 갱신해준다.
                resultMap.put(carNum, resultMap.getOrDefault(carNum, 0) + duration);
            }
        }

        int endOfDay = convertToTime("23:59");

        // 입차 기록만 있고 출차 기록은 없는 경우 (출차 기록이 있으면 위 for문에서 inMap에 해당 키가 제거되었을 것임)
        for (Map.Entry<String, Integer> entry : inMap.entrySet()) {
            String carNum = entry.getKey();
            int time = entry.getValue();
            int duration = endOfDay - time;

            resultMap.put(carNum, resultMap.getOrDefault(carNum, 0) + duration);
        }

        List<String> carNumList = resultMap.keySet().stream().sorted().collect(Collectors.toList());

        int[] answer = new int[carNumList.size()];

        int i = 0;
        for (String carNum : carNumList) {

            int time = resultMap.get(carNum);
            int fee = basePrice;

            // 기본 주차 시간을 초과한 경우 추가 금액 계산
            if (time > baseTime) {
                // 초과한 시간이 단위 시간으로 나누어 떨어지지 않으면, 올림합니다.
                fee += (int) (Math.ceil((double) (time - baseTime) / unitTime) * unitPrice);
            }

            answer[i] = fee;

            i++;
        }

        System.out.println("answer = " + Arrays.toString(answer));
    }

    private int convertToTime(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}
