package pl.edu.ug.inf.main;

import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.exceptions.JsonSyntaxException;
import pl.edu.ug.inf.exceptions.JsonToObjectException;
import pl.edu.ug.inf.pojo.Person;

public class Main {

    public static void main(String[] args) throws JsonSyntaxException, JsonToObjectException, IllegalAccessException {
        Person Maciek = new Person("Maciej", "Miś", 24, 79.5, Person.Gender.MAN);
        JsonConverter converter = new JsonConverter();
        System.out.println(converter.convertToJson(Maciek));
        Person c = new Person();
        converter.convertFromJson("{\"weight\":\"85\",\"isHuman\":\"true\",\"age\":\"31\",\"name\":\"Konrad\",\"gender\":\"MAN\",\"surname\":\"Nies\"}", Maciek);
        System.out.println(converter.convertToJson(Maciek));

    }
}
