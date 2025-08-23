package com.example.coding_test.쏘카;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 어떤 문자의 반복을 ~로 표시하기로합니다. 예를들어 A~는 A가한번이상 반복되는 모든 문자열의 집합을 의미합니다. (ABC)~는 괄호 안에 있는 ABC로 구성된 문자열이 한번이상 반복되는 모든 문자열의 집합을 의미합니다.
 *
 * 예)
 * B~ = {"B", "BB", "BBB", "BBBB", "B...B", ...}
 * (AB)~ = {"AB", "ABAB", "ABABAB", "AB...AB", ...}
 * (BAAB)~ = {"BAAB", "BAABBAAB", "BAABBAABBAAB...BAAB"}
 * BA~BB = {"BABB", BAABB", "BAAABB", "BAAAABB", "BAAA...ABB", ...}
 * (BA~B)~ = {"BAB", "BAAB", "BAAAB", "BAAAAB", "BABBAAB", "BAABBABBAAABBAB", ...}
 *
 * 추가로 (A|B)는 A또는 B 중에서 아무거나 하나만 선택해서 만든 문자열의 집합 (A,B)를 의미합니다.
 * 예를 들면, (BAA|ABA)를 집합으로 표현하면 {BAA, ABA}와 같습니다. 따라서 (A|B)~은 A와 B를 사용하여 만들수있는 모든 문자열의 집합을 의미합니다. 마찬가지로 (BAB|AA)~은 BAB와 AA를 마음대로 섞어서 만들 수 있는 모든 문자열의 집합을 의미합니다.
 * 예를 들어 (BAB|AA)~에 해당하는 문자열을 나열하면 {"AA", "BAB", "AAAA", "BABAA", "AABAB", "AAAABAB", "BABBABBAB", "BABAAAAAA", "AABABBABAA", ...} 입니다.
 * 본 문제에서는 (AAA~|BAB~A)~패턴을 띄는 문자열이 몇개인지 판별하려고 합니다.
 * (AAA~|BAB~A)~패턴을 띄는 문자열을 {"AAA", "BABA", "AABBB", "BABBA", "BABABABA", "AABBBABBA", "BABBAAAB", "AABBAABAAB", "BABAAABAABBABBA", ...} 입니다. 즉 AAB~와 BAB~A를 마음대로 섞어서 만들 수 있는 모든문자열을 판별하고자합니다.
 * 문자열이 담긴 배열 strs가 매개변수로 주어질 때 (AAB~|BAB~A)~패턴에 해당하는 문자열의 개수를 return 하도록 solution 함수를 완성해주세요.
 */
public class FirstSolution {

    @Test
    void test() {

        String[] first = {"AABAAA", "BABABB", "BABBAAAB", "BABAAABAABBABBA"};
        int firstAnswer = 2;
        String[] second = {"AA", "BAB", "BAAAA", "ABBABB", "AABBBBABBAAAA"};
        int secondAnswer = 0;
        String[] third = {"AABAABAAB", "AABBBAABBB", "AABBBABBABABBBAAABBBABBBA"};
        int thirdAnswer = 3;

        assertThat(solution(first)).isEqualTo(firstAnswer);
        assertThat(solution(second)).isEqualTo(secondAnswer);
        assertThat(solution(third)).isEqualTo(thirdAnswer);
    }

    private int solution(String[] args) {

        // (AAB~|BAB~A)~ : AAB~와 BAB~A를 마음대로 섞어서 만들 수 있는 모든 문자열 판별

        // AAB~ : AA 뒤에 B가 1개 이상 ("AAB", "AABB", "AABBB", "AABBBB" ...)
        // BAB~A : B A B...B A (B가 1개 이상) 이며 끝이 A로 끝나야 한다.

        int count = 0;
        for (String word : args) {
            if (checkMatch(word)) count++;
        }

        return count;
    }

    private boolean checkMatch(String word) {

        int wordLength = word.length();
        boolean[] matched = new boolean[wordLength + 1];
        matched[0] = true; // 시작점

        // 문자열의 길이만큼 순회한다.
        for (int i = 0; i < wordLength; i++) {

            // matched가 false인 경우는
            if (!matched[i]) continue;

            // AAB~
            // AA로 시작해야한다.
            if (word.startsWith("AA", i)) {

                // AA 다음 반드시 B가 와야하기 때문에 AA를 건너뛴다.
                // AA 뒤 위치
                int bIndex = i + 2;

                // AA 다음 B가 연속되는지 확인
                while(bIndex < wordLength && word.charAt(bIndex) == 'B') {
                    bIndex++;

                    // AAB 가 확인되었기에 다음번 인덱스부터 다시 확인 할 수 있도록 flag 지정
                    matched[bIndex] = true;
                }
            }

            // BAB~A
            // 4글자가 반드시 포함되어야하고, BA로 시작해야한다.
            if (word.startsWith("BA", i)) {

                // BA 다음 반드시 B가 1개이상있어야한다.
                int bIndex = i + 2;  // BA 뒤 위치

                // BA 다음 B가 연속되는지 확인
                while(bIndex < wordLength && word.charAt(bIndex) == 'B') {
                    bIndex++;
                    if (bIndex < wordLength && word.charAt(bIndex) == 'A') {
                        // BABA에 도달했기 때문에 다음 번 인덱스부터 다시 확인할 수 있도록 flag 지정
                        matched[bIndex + 1] = true;
                    }
                }
            }
        }

        // 문자열 끝까지 정확히 도달했는지
        return matched[wordLength];
    }
}
