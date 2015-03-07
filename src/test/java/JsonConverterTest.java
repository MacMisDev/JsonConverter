import org.junit.Before;
import org.junit.Test;
import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.exceptions.JsonSyntaxException;
import pl.edu.ug.inf.exceptions.JsonToObjectException;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private JsonConverter jsonConverter;
    private Person person;

    @Before
    public void prepareForTests(){
        jsonConverter = new JsonConverter();
        person = new Person("Maciej", "Miś", 24, 78.5, Person.Gender.MAN, true, (short)0, (char)65);
    }

    @Test
    public void shouldCompareHashMapAndFieldsSize(){
        jsonConverter.convertToJson(person);
        assertEquals(person.getClass().getDeclaredFields().length, jsonConverter.getToJson().size());

    }

    @Test
    public void shouldCheckIfJsonIsValid() throws JsonToObjectException, IllegalAccessException, JsonSyntaxException {
        final double epsilon = 1e-15;
        String json = jsonConverter.convertToJson(person);
        Person maciej = new Person();
        jsonConverter.convertFromJson(json, maciej);
        assertEquals(person.getSurname(), maciej.getSurname());
        assertEquals(person.getName(), maciej.getName());
        assertEquals(person.getGender(), maciej.getGender());
        assertEquals(person.getAge(), maciej.getAge());
        assertEquals(person.isInsurance(), maciej.isInsurance());
        assertEquals(person.getWeight(), maciej.getWeight(), epsilon);
        assertEquals(person.getChildNumber(), maciej.getChildNumber());
        assertEquals(person.getMilitaryCategory(), maciej.getMilitaryCategory());
    }
}
