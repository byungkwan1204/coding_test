package com.example.coding_test.여기어때;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * 사이버 코딩 대학(CCU)은 n개의 반으로 이루어져있습니다.
 * CCU는 학생들의 코딩 실력을 평가하기 위해 온라인 코딩 테스트를 준비 중입니다.
 * 그런데 학생들의 수가 너무 많아서, 한대의 서버로는 테스트를 진행할 수 없습니다.
 * 고민 끝에, CCU는 n개의 반을 k개의 서버로 분배 시키는데, 아래와 같은 규칙을 따릅니다.
 * 1. n개의 반은 1반부터 n반까지, k개의 서버는 1번부터 k번 서버까지 고유 번호가 부여되어 있습니다. (단, k <= n)
 * 2. a번 서버에 포함된 모든 반은 a - 1번 서버에 포함된 모든 반보다 뒷 반이어야 합니다. (2 <= a <=k)
 * 2-1. 예를 들어, 7개의 반을 2개의 서버로 나눈다면 1,2,3반을 1번 서버에, 4,5,6,7반을 2번 서버로 나누는 것은 가능하지만, 1,3,5반을 1번 서버에, 2,4,6,7반을 2번 서버로 나누는 것은 불가능합니다. 2번 서버에 포함된 4반이 1번 서버에 포함된 5반보다 뒷반이 아니기 때문입니다.
 * 2-2. 즉, 7개의 반을 2개의 서버로 분배하는 방법은 총 6가지 입니다.
 * [1반] / [2~7반], [1~2반] / [3~7반], [1~3반] / [4~7반], [1~4반] / [5~7반], [1~5반] / [6~7반], [1~6반] / [7반]
 * 3. 각각의 서버에 분배된 학생 수의 최대 값을 최소로 해야합니다.
 * 3-1. 예를 들어, 9개 반의 학생 수가 [30,29,35,25,20,30,20,45,48] 이고, 3개의 서버로 나누는 경우를 고려해보겠습니다.
 * 3-2. [30, 29, 35], [25, 20, 30, 20], [45, 48] 으로 나눈다면, 각각의 서버에 분배된 학생 수는 94,95,93명 입니다. 즉, 주어진 9개의 반을 3개의 서버로 나눈다면, 각각의 서버에 속한 학생 수가 최대 95명 이하가 되도록 만들 수 있습니다.
 * 4. 한반은 여러 서버에 분배되어 흩어지지 않습니다. 즉, 하나의 반은 정확히 한 서버에만 분배됩니다.
 *
 * 각 반의 학생 수를 담은 배열 classes와, 반을 나누어서 수용할 서버 수 k가 매개변수로 주어집니다.
 * 이 떄, k개의 서버에 속한 학생 수의 최대값이 최소가 되도록 반을 분해했을 떄, 그 최소값을 return하도록 solution 함수를 완성해주세요.
 *
 * 제한 사항
 * - classes의 길이는 1 이상 300,000 이하입니다.
 *    - classes는 각 반의 학생 수를 담고 있으며, 1반부터 차례대로 주어집니다.
 *    - classes를 구성하는 원소는 1이상 1,000,000,000 이하인 자연수 입니다.
 * - k는 1이상 300,000 이하인 자연수입니다. 또한, k는 classes의 길이보다 작거나 같습니다.
 *
 * 입출력 예시 1.
 * 1. classes = {30, 29, 35, 25, 20, 30, 20, 45, 48}, k = 3, result = 95
 * 2. classes = {100, 1, 50, 77, 4854}, k = 5, result = 4854
 * 3. classes = {1056, 999, 74}, k = 1, result = 2129
 *
 * 입출력 예시 2. (입출력 예시1-2에 대한 설명)
 * 5개의 반을 5개의 서버로 분산시켜야 하므로, 각각의 서버에 한반씩 분산시킵니다.
 * 마지막 5번 서버에 들어간 학생 수가 4854명으로 가장 많으므로, 이 값을 return 해주어야 합니다.
 *
 * 입출력 예시 3. (입출력 예시1-3에 대한 설명)
 * 3개의 반을 1개의 서버로 분산시켜야하므로, 모든 반이 하나의 서버로 몰립니다.
 * 따라서 1056, 999, 74를 모두 더한 값인 2129를 return 해주어야합니다.
 */
public class SecondSolution {

    @Test
    void test() {

        int[] first_classes = {30, 29, 35, 25, 20, 30, 20, 45, 48};
        int first_k = 3;
        int first_result = 95;
        int[] second_classes = {100, 1, 50, 77, 4854};
        int second_k = 5;
        int second_result = 4854;
        int[] third_classes = {1056, 999, 74};
        int third_k = 1;
        int third_result = 2129;

        int[] fourth_classes = {1, 3, 5, 10};
        int fourth_k = 2;
        int fourth_result = 10;

        assertThat(solution(fourth_classes, fourth_k)).isEqualTo(fourth_result);
    }

    private long solution(int[] classes, int k) {

        long minCapacity = Arrays.stream(classes).asLongStream().max().orElse(0);
        long maxCapacity = Arrays.stream(classes).asLongStream().sum();

        while(minCapacity < maxCapacity) {

            long middleCapacity = (minCapacity + maxCapacity) / 2;

            System.out.println("middleCapacity = " + middleCapacity);
            if (isAvailableDecrease(classes, k, middleCapacity)) {
                maxCapacity = middleCapacity;
            } else {
                minCapacity = middleCapacity + 1;
            }

            System.out.println("maxCapacity = " + maxCapacity);
            System.out.println("minCapacity = " + minCapacity);
        }

        return minCapacity;
    }

    private boolean isAvailableDecrease(int[] classes, int k, long capacity) {

        int sum = 0;
        int server = 1;
        for (int c : classes) {

            if (sum + c <= capacity) {
                sum += c;
            } else {
                server++;
                sum = c;
                if (server > k) {
                    return false;
                }
            }
        }

        return true;
    }

}
