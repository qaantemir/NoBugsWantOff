package java_express.myCollections;

import com.github.javafaker.Faker;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Faker faker = new Faker();
        List<Integer> numList = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        numList.add(6);
        numList.forEach(System.out::print);
        divider();

        numList.forEach((x) -> {if (x % 2 == 0) System.out.print(x + " ");});

        divider();

        List<String> strList = new LinkedList<>() {{
            add("a");
            add("b");
            add("c");
            add("d");
            add("e");
        }};

        strList.forEach((x) -> System.out.print(x + " "));

        divider();

        Deque<String> tasks = new LinkedList<>() {{
            offer("task1");
            offer("task2");
            offer("task3");
        }};

        while (!(tasks.isEmpty())) System.out.println("В работе: " + tasks.poll());

        divider();

        Set<Integer> numSet = new HashSet<>(){{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};

        numSet.forEach(System.out::println);

        divider();

        numSet.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        boolean b = numSet.retainAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        numSet.forEach(System.out::println);
        System.out.println(b);
        System.out.println(numSet.contains(5));
        System.out.println(numSet.contains(99));

        divider();

        Set<String> linkSet = new LinkedHashSet<>() {{
            add("q");
            add("w");
            add("e");
            add("r");
            add("t");
        }};

        System.out.println(linkSet);

        divider();

        Set<Integer> myTreeSet = new TreeSet<>() {{
            addAll(List.of(3, 2, 5, 1, 4));
        }};

        System.out.println(myTreeSet);

        divider();

        Map<String, Integer> mmap = new HashMap<>(){{
           put("anna", 55);
           put("alla", 22);
           put("polya", 345);
           put("sofa", 20);
           put("sasha", 16);
        }};

        for (Map.Entry<String, Integer> entry : mmap.entrySet()) System.out.println(entry);

        divider();

        System.out.println(mmap.containsKey("sasha"));
        System.out.println(mmap.containsKey("kostya"));

        divider();

        Map<String, String> lmap = new LinkedHashMap<>(){{
            put("anna", faker.phoneNumber().phoneNumber());
            put("alla", faker.phoneNumber().phoneNumber());
            put("polya", faker.phoneNumber().phoneNumber());
            put("sofa", faker.phoneNumber().phoneNumber());
            put("sasha", faker.phoneNumber().phoneNumber());
        }};

        System.out.println(lmap.get("sofa"));

        divider();

        TreeMap<String, String> tmap = new TreeMap<>(){{
            put("anna", faker.phoneNumber().phoneNumber());
            put("masha", faker.phoneNumber().phoneNumber());
            put("yana", faker.phoneNumber().phoneNumber());
            put("sofa", faker.phoneNumber().phoneNumber());
            put("sasha", faker.phoneNumber().phoneNumber());
        }};

        System.out.println(tmap);

        System.out.println(tmap.firstKey());
        System.out.println(tmap.lastKey());

        divider();

        Queue<Integer> pq = new PriorityQueue<>();

        pq.offer(6);
        pq.offer(5);
        pq.offer(1);
        pq.offer(4);
        pq.offer(3);
        pq.offer(2);

        System.out.println(pq);

        while (!(pq.isEmpty())) System.out.println(pq.poll());

        ArrayDeque<Integer> aq = new ArrayDeque<>();
        divider();

        aq.offer(1);
        aq.offer(2);
        aq.offer(3);
        aq.offer(4);
        aq.offer(5);

        while (!(aq.isEmpty())) System.out.println(aq.pop());








    }

    static void divider() {
        System.out.println();
        System.out.println("---------------------------------");
    }
}
