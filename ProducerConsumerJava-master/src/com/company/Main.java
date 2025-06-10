package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int storageSize = 6;       // Максимальна місткість сховища
        int totalItems = 50;       // Загальна кількість продукції
        int producerCount = 8;     // Кількість виробників
        int consumerCount = 6;     // Кількість споживачів

        Manager manager = new Manager(storageSize);


        List<Integer> producerItems = splitRandomly(totalItems, producerCount);
        List<Integer> consumerItems = splitRandomly(totalItems, consumerCount);

        // виробники
        for (int i = 0; i < producerCount; i++) {
            int items = producerItems.get(i);
            new Producer(i + 1, items, manager);
        }

        // споживачі
        for (int i = 0; i < consumerCount; i++) {
            int items = consumerItems.get(i);
            new Consumer(i + 1, items, manager);
        }
    }

    // генерує n доданків, що у сумі дорівнюють total, з випадковим розподілом
    private static List<Integer> splitRandomly(int total, int parts) {
        Random random = new Random();
        List<Integer> splits = new ArrayList<>();

        int sum = 0;
        for (int i = 0; i < parts - 1; i++) {
            int remaining = total - sum - (parts - i - 1); // мінімум 1 для кожного
            int value = 1 + random.nextInt(remaining + 1); // мінімум 1
            splits.add(value);
            sum += value;
        }

        splits.add(total - sum); // останній елемент — щоб сума була total
        Collections.shuffle(splits); // випадковий порядок, щоб не завжди останній був найбільший

        return splits;
    }
}