package com.example.javapro;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTasks {
    public static void main(String[] args) {
        // Тестовые данные
        List<Integer> numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        List<String> words = Arrays.asList("spring", "java", "boot", "data", "kotlin", "postgres");
        String text = "java spring hibernate data spring boot kotlin";
        List<String> lines = Arrays.asList("Java programming language", "Java is a proven programming language", "Java forever");

        List<Employee> employees = Arrays.asList(
                new Employee("Ivan", 35, "Разработчик"),
                new Employee("Petr", 40, "Менеджер"),
                new Employee("Anna", 45, "QA"),
                new Employee("Olga", 50, "Инженер"),
                new Employee("Sergey", 38, "Инженер"),
                new Employee("Maria", 28, "Аналитик")
        );

        // 3-е наибольшее число
        int thirdMax = numbers.stream().sorted(Comparator.reverseOrder()).limit(3).skip(2).findFirst().orElseThrow();
        System.out.println("3-е наибольшее число: " + thirdMax);

        // 3-е наибольшее уникальное число
        int thirdUniqueMax = numbers.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElseThrow();
        System.out.println("3-е уникальное наибольшее число: " + thirdUniqueMax);

        // Имена 3 самых старших инженеров
        List<String> top3Engineers = employees.stream().filter(e -> e.position().equals("Инженер")).sorted(Comparator.comparingInt(Employee::age).reversed()).limit(3).map(Employee::name).toList();
        System.out.println("Топ-3 инженеров по возрасту: " + top3Engineers);

        // Средний возраст инженеров
        double avgAge = employees.stream().filter(e -> e.position().equals("Инженер")).mapToInt(Employee::age).average().orElse(0);
        System.out.println("Средний возраст инженеров: " + avgAge);

        // Самое длинное слово
        String longestWord = words.stream().max(Comparator.comparingInt(String::length)).orElse("");
        System.out.println("Самое длинное слово: " + longestWord);

        // Map: слово -> частота
        Map<String, Long> wordFreq = Arrays.stream(text.split(" ")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Частота слов: " + wordFreq);

        // Строки по длине + алфавиту
        System.out.println("Слова по длине и алфавиту:");
        words.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).forEach(System.out::println);

        // Самое длинное слово из всех массивов строк
        String longestFromLines = lines.stream().flatMap(line -> Arrays.stream(line.split(" "))).max(Comparator.comparingInt(String::length)).orElse("");
        System.out.println("Самое длинное слово среди всех строк: " + longestFromLines);
    }


    record Employee(String name, int age, String position) {

        @Override
            public String toString() {
                return name + " (" + age + ", " + position + ")";
            }
        }
}