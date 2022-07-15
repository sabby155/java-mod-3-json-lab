import java.util.List;

public class Person {
    private String name;
    private List<Person> friends;
    public Person(String name) {
        this.name = name;
        System.out.println("Initialized person.");
        System.out.println(this);
    }


    public String getName() {
        return name;
    }


}
