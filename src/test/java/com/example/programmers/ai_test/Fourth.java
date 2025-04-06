package com.example.programmers.ai_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Fourth {

    @DisplayName("오픈채팅방" +
                 "카카오톡 오픈채팅방에서는 친구가 아닌 사람들과 대화를 할 수 있는데, 본래 닉네임이 아닌 가상의 닉네임을 사용하여 채팅방에 들어갈 수 있다." +
                 "신입사원인 김크루는 카카오톡 오픈 채팅방을 개설한 사람을 위해, 다양한 사람들이 들어오고, 나가는 것을 지켜볼 수 있는 관리자창을 만들기로 했다." +
                 "채팅방에 누군가 들어오면 다음 메시지가 출력된다." + "\"[닉네임]님이 들어왔습니다.\"" +
                 "채팅방에서 누군가 나가면 다음 메시지가 출력된다." + "\"[닉네임]님이 나갔습니다.\"" +
                 "채팅방에서 닉네임을 변경하는 방법은 다음과 같이 두 가지이다." +
                 "채팅방을 나간 후, 새로운 닉네임으로 다시 들어간다." +
                 "채팅방에서 닉네임을 변경한다.\n" +
                 "닉네임을 변경할 때는 기존에 채팅방에 출력되어 있던 메시지의 닉네임도 전부 변경된다." +
                 "예를 들어, 채팅방에 \"Muzi\"와 \"Prodo\"라는 닉네임을 사용하는 사람이 순서대로 들어오면 채팅방에는 다음과 같이 메시지가 출력된다." +
                 "\"Muzi님이 들어왔습니다.\"" +
                 "\"Prodo님이 들어왔습니다.\"" +
                 "채팅방에 있던 사람이 나가면 채팅방에는 다음과 같이 메시지가 남는다." +
                 "\"Muzi님이 들어왔습니다.\"" +
                 "\"Prodo님이 들어왔습니다.\"" +
                 "\"Muzi님이 나갔습니다.\"" +
                 "Muzi가 나간후 다시 들어올 때, Prodo 라는 닉네임으로 들어올 경우 기존에 채팅방에 남아있던 Muzi도 Prodo로 다음과 같이 변경된다." +
                 "\"Prodo님이 들어왔습니다.\"" + "\"Prodo님이 들어왔습니다.\"" + "\"Prodo님이 나갔습니다.\"" +
                 "\"Prodo님이 들어왔습니다.\"" +
                 "채팅방은 중복 닉네임을 허용하기 때문에, 현재 채팅방에는 Prodo라는 닉네임을 사용하는 사람이 두 명이 있다. " +
                 "이제, 채팅방에 두 번째로 들어왔던 Prodo가 Ryan으로 닉네임을 변경하면 채팅방 메시지는 다음과 같이 변경된다." +
                 "\"Prodo님이 들어왔습니다.\"" + "\"Ryan님이 들어왔습니다.\"" + "\"Prodo님이 나갔습니다.\"" + "\"Prodo님이 들어왔습니다.\"" +
                 "채팅방에 들어오고 나가거나, 닉네임을 변경한 기록이 담긴 문자열 배열 record가 매개변수로 주어질 때, " +
                 "모든 기록이 처리된 후, 최종적으로 방을 개설한 사람이 보게 되는 메시지를 문자열 배열 형태로 return 하도록 solution 함수를 완성하라.")
    @ParameterizedTest
    @MethodSource("provideInput")
    void test(String[] record, String[] extected) {
        assertThat(solution(record)).isEqualTo(extected);
    }

    /**
     * 핵심 포인트는 유저 ID 기준으로 닉네임을 최신화하고, 출력 시점에 최신 닉네임을 적용 하는것이다.
     */
    private String[] solution(String[] record) {

        Map<String, String> map = new HashMap<>();

        for (String r : record) {

            String command = r.split(" ")[0];
            String uid = r.split(" ")[1];

            if (!command.equals("Leave")) {
                String nickName = r.split(" ")[2];

                map.put(uid, nickName);
            }
        }

        List<String> result = new ArrayList<>();

        for (String s : record) {

            String command = s.split(" ")[0];
            String uid = s.split(" ")[1];

            String nickName = map.get(uid);

            if (command.equals("Enter")) {
                result.add(String.format("%s님이 들어왔습니다.", nickName));
            }

            if (command.equals("Leave")) {
                result.add(String.format("%s님이 나갔습니다.", nickName));
            }
        }

        return result.toArray(String[]::new);
    }


    private static Stream<Arguments> provideInput() {
        return Stream.of(Arguments.of(
            new String[] {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"},
            new String[] {"Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."}));
    }
}
