package java_express.functional;

import java_express.utils.MyListUtils;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> dupl = List.of("a", "a", "b", "c");
        List<Integer> integers = MyListUtils.createList(3, Integer.class);
        List<String> strings = MyListUtils.createList(10, String.class);

        MathOperation add = (x, y) -> x + y;
        MathOperation minuss = (x, y) -> x - y;
        MathOperation multiply = (x, y) -> x * y;
        MathOperation divide = (x, y) -> x / y;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class!");
            }
        };

        Predicate<Integer> pre = x -> x % 2 == 0;
        Function<String, Integer> fun = x -> x.length();
        Consumer<String> con = x -> System.out.println(x);

        List<String> newList = strings.stream().filter(x -> x.length() > 5).toList();

        MyListUtils.printList(strings);
        MyListUtils.printList(newList);

        System.out.println("-------------------");

        List<Integer> newIntList = integers.stream().filter(x -> x % 5 == 0).toList();

        MyListUtils.printList(integers);
        MyListUtils.printList(newIntList);

        System.out.println("-------------------");

        List<Integer> lengths = strings.stream().map(x -> x.length()).toList();

        MyListUtils.printList(strings);
        MyListUtils.printList(lengths);

        System.out.println("-------------------");

        List<Integer> sq = integers.stream().map(x -> x * x).toList();

        MyListUtils.printList(integers);
        MyListUtils.printList(sq);

        System.out.println("-------------------");

        List<String> noDupl = dupl.stream().distinct().toList();

        MyListUtils.printList(dupl);
        MyListUtils.printList(noDupl);

        System.out.println("-------------------");

        List<Integer> ints = Stream.of(1, 2, 3, 4, 5).toList();

        int max = ints.stream().max((x, y) -> x.compareTo(y)).get();
        int min = ints.stream().min((x, y) -> x.compareTo(y)).get();
        int sum = ints.stream().mapToInt(x -> Integer.valueOf(x)).sum();

        MyListUtils.printList(ints);
        System.out.println(max);
        System.out.println(min);
        System.out.println(sum);

        System.out.println("-------------------");

        List<String> names = MyListUtils.createList(10, String.class);

//        String startsWithB = names.stream().filter(x -> x.startsWith("B")).toList().getFirst();
//        boolean check = names.stream().anyMatch(x -> x.startsWith("B"));

        MyListUtils.printList(names);
//        System.out.println(startsWithB);
//        System.out.println(check);

        System.out.println("-------------------");


        Map<String, List<String>> mmap = names.stream().collect(Collectors.groupingBy(
                x -> String.valueOf(x.charAt(0)),
                Collectors.mapping(x -> x, Collectors.toList())
        ));

        System.out.println(mmap);

        System.out.println("-------------------");
        List<Integer> intsForMap = MyListUtils.createList(10, Integer.class);

        Map<Boolean, List<Integer>> imap = intsForMap.stream().collect(Collectors.groupingBy(
                x -> x % 2 == 0,
                Collectors.mapping(x -> x, Collectors.toList())
        ));

        System.out.println(imap);

        System.out.println("-------------------");

        List<Integer> intsForAvg = MyListUtils.createList(2, Integer.class);
        OptionalDouble avg = intsForAvg.stream().mapToInt(x -> Integer.valueOf(x)).average();

        MyListUtils.printList(intsForAvg);
        System.out.println(avg);


    }
}
