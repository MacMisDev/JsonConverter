package pl.edu.ug.inf.main;

import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.pojo.Person;

public class Main {

    public static void main(String[] args) {
        Person Maciek = new Person("Maciej", "Miś", 24, 79.5);
        JsonConverter converter = new JsonConverter();
        System.out.println(converter.convertToJson(Maciek));

    }
}
