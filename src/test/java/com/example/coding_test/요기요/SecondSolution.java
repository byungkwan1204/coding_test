package com.example.coding_test.요기요;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

public class SecondSolution {

    @Test
    void test() {

        Pizza[] pizzas = new Pizza[]{
            new Pizza("margherita", 7, 8, 10),
            new Pizza("hawaii", 8, 9, 12),
            new Pizza("capricciosa", 5, 7, 13)};

        OrderItem[] orderItems = new OrderItem[]{
            new OrderItem("margherita", "Small", 3),
            new OrderItem("capricciosa", "Large", 2),
            new OrderItem("hawaii", "Large", 3),
            new OrderItem("margherita", "Large", 1),
            new OrderItem("hawaii", "Medium", 1),
            new OrderItem("capricciosa", "Small", 5),
            new OrderItem("capricciosa", "Medium", 1)};

        // 실제 지불 금액
        int payPrice = 0;
        // 주문 총 금액
        int totalPrice = 0;
        // 주문 총 수량
        int totalCount = 0;

        // 가장 싼 금액
        int minPrice = Integer.MAX_VALUE;

        // 주문 피자 별 금액 저장
        List<Integer> allOrderPrices = new ArrayList<>();

        // 라지 피자 주문 수량
        int largeCount = 0;

        // 스몰 피자 주문 수량
        int smallCount = 0;

        // 스몰 피자 중 최소 금액
        int smallMinPrice = Integer.MAX_VALUE;

        // 주문 피자 별 라지 피자 금액 - 미디엄 피자 금액 차액 저장
        List<Integer> largeToMediumDiffs = new ArrayList<>();

        System.out.println(Arrays.toString(pizzas));
        Map<String, Pizza> pizzaMap = Stream.of(pizzas)
            .collect(Collectors.toMap(pizza -> pizza.name, pizza -> pizza));

        for (OrderItem orderItem : orderItems) {
            Pizza pizza = pizzaMap.get(orderItem.name);
            int price = getPriceOfSize(pizza, orderItem.size);

            for (int i = 0; i < orderItem.quantity; i++) {
                totalCount++;
                totalPrice += price;
                minPrice = Math.min(minPrice, price);
                allOrderPrices.add(price);

                if ("Small".equals(orderItem.size)) {
                    smallCount++;
                    smallMinPrice = Math.min(smallMinPrice, price);
                }

                if ("Large".equals(orderItem.size)) {
                    largeCount++;
                    // price = 라지 피자 가격 이므로 미디엄 피자 가격과의 차액 계산
                    largeToMediumDiffs.add(price - pizza.price_M);
                }
            }
        }

        List<Integer> discounts = new ArrayList<>();
        discounts.add(totalPrice);

        // 할인 1 : 3개 이상이면 가장 싼 것 무료
        discounts.add(totalCount >= 3 ? totalPrice - minPrice : Integer.MAX_VALUE);

        // 할인 2 : 5개 이상 주문 시 가장 비싼 5개 100원 처리
        if (totalCount >= 5) {
            allOrderPrices.sort(Comparator.reverseOrder());

            int sum = 0;
            for (int i = 5; i < allOrderPrices.size(); i++) {
                sum += allOrderPrices.get(i);
            }

            // 가장 비싼 5개를 100원으로, 나머지는 원가
            discounts.add(100 + sum);
        }

        // 할인 3 : 라지 피자를 시키면 스몰 피자 무료
        discounts.add((largeCount >= 1 && smallCount >= 1) ? totalPrice - smallMinPrice : Integer.MAX_VALUE);

        // 할인 4 : 라지 3개를 주문하면 중형 3개 가격에 구매
        if (largeCount >= 3) {

            // 가장 비싼 라지 순 정렬
            largeToMediumDiffs.sort(Comparator.reverseOrder());

            int sum = 0;
            for (int i = 0; i < 3; i++) {
                sum += largeToMediumDiffs.get(i);
            }

            // 라지 3개 가격으로 저장된 totalPrice에 차액만큼 차감
            discounts.add(totalPrice - sum);
        }

        payPrice = discounts.stream()
            .min(Integer::compare)
            .orElse(totalPrice);

        System.out.println("payPrice = " + payPrice);
    }

    class Pizza {
        public String name;
        public int price_S;
        public int price_M;
        public int price_L;

        public Pizza(String name, int price_S, int price_M, int price_L) {
            this.name = name;
            this.price_S = price_S;
            this.price_M = price_M;
            this.price_L = price_L;
        }
    }
    class OrderItem {
        public String name;
        public String size;
        public int quantity;

        public OrderItem(String name, String size, int quantity) {
            this.name = name;
            this.size = size;
            this.quantity = quantity;
        }
    }

    public int getPriceOfSize(Pizza pizza, String orderSize) {
        return switch (orderSize) {
            case "Small" -> pizza.price_S;
            case "Medium" -> pizza.price_M;
            case "Large" -> pizza.price_L;
            default -> 0;
        };
    }
}
