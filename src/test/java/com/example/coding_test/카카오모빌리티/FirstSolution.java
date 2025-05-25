package com.example.coding_test.카카오모빌리티;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.junit.jupiter.api.Test;

public class FirstSolution {

    @Test
    void test() {

        StringJoiner result = new StringJoiner(", ");

        String s = "John Doe, Peter Parker, Mary Jane Watson-Parker, James Doe, John Elvis Doe, Jane Doe, Penny Parker";
        String c = "Example";

        String[] fullNames = s.split(", ");

        Map<String, Integer> emailMap = new HashMap<>();
        for (String fullName : fullNames) {

            String[] names = fullName.split(" ");

            String firstInitial = names.length > 2 ?
                names[0].substring(0, 1).toLowerCase() + names[1].substring(0, 1).toLowerCase() : names[0].substring(0, 1).toLowerCase();

            String lastInitial = names[names.length - 1].toLowerCase().replaceAll("\\-", "");
            if (lastInitial.length() > 8) {
                lastInitial = lastInitial.substring(0, 8);
            }

            String emailId = firstInitial + lastInitial;

            int count = emailMap.getOrDefault(emailId, 0) + 1;

            emailMap.put(emailId, count);

            if (count > 1) {
                emailId += count;
            }

            String email = String.format("%s <%s@%s.com>", fullName, emailId, c.toLowerCase());

            result.add(email);
        }

        System.out.println("result.toString() = " + result.toString());
    }
}
