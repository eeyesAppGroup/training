import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        //list -> set
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lists.add(Arrays.asList(1, 2, 3));
        }
        Set<Integer> set = lists.stream()
                .flatMap(integers -> integers.stream())
                .map(integer -> integer + 2)
                .collect(Collectors.toSet());
        for (int i : set) {
            System.out.print(i + " ");
        }

        //group中每个成人年龄求和
        Person person1 = new Person(17, "lihua");
        Person person2 = new Person(19, "xiaoming");
        Person person3 = new Person(33, "xiaohing");
        Group musicGroup = new Group(Arrays.asList(person1, person2, person3));
        int result=Stream.of(musicGroup)
                .map(group -> group.list)
                .flatMap(list -> list.stream())
                .filter(person -> person.age > 18)
                .map(person -> person.age)
                .reduce(0, (x, y) -> x + y);
        System.out.println(" "+result);
    }

}
