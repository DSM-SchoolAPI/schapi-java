package parser;

import datastructure.Meal;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealParser {
    public static ArrayList<Meal> parse(String rawData) {
        if (rawData.length() < 1)
            return new ArrayList<Meal>();

        ArrayList<Meal> monthlyMenu = new ArrayList<>();
        monthlyMenu.add(new Meal());

        rawData = rawData.replaceAll("\\s+", "");

        StringBuffer buffer = new StringBuffer();

        boolean inDiv = false;

        for (int i = 0; i < rawData.length(); i++) {
            if (rawData.charAt(i) == 'v') {
                if (inDiv) {
                    buffer.delete(buffer.length() - 4, buffer.length());
                    if (buffer.length() > 0) {
                        monthlyMenu.add(parseDay(buffer.toString()));
                    }
                    buffer.setLength(0);
                } else {
                    i++;
                }
                inDiv = !inDiv;
            } else if (inDiv) {
                buffer.append(rawData.charAt(i));
            }
        }

        return monthlyMenu;
    }

    public static Meal parseDay(String rawData) {
        Meal meal = new Meal();

        // 의미 없는 단어를 제거합니다.
        rawData = rawData.replace("(석식)", "");
        rawData = rawData.replace("(선)", "");

        String[] chunks = rawData.split("<br/>");

        // 0 - 조식, 1 - 중식, 2 - 석식
        int parsingMode = 0;

        for (String chunk: chunks) {
            if (chunk.trim().length() < 1)
                continue;

            if (chunk.equals("[조식]")) {
                parsingMode = 0;
                meal.breakfast = new ArrayList<>();

                continue;
            } else if (chunk.equals("[중식]")) {
                parsingMode = 1;
                meal.lunch = new ArrayList<>();

                continue;
            } else if (chunk.equals("[석식]")) {
                parsingMode = 2;
                meal.dinner = new ArrayList<>();

                continue;
            } else if (chunk.matches("\\d+")) {
                continue;
            }

            Matcher matcher = Pattern.compile("[가-힇]+\\(\\[가-힇]+\\)|[가-힇]+").matcher(chunk);
            matcher.find();
            chunk = matcher.group();

            switch (parsingMode) {
                case 0:
                    meal.breakfast.add(chunk);
                    break;
                case 1:
                    meal.lunch.add(chunk);
                    break;
                case 2:
                    meal.dinner.add(chunk);
                    break;
            }
        }

        return meal;
    }
}
