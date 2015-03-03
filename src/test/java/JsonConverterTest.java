import org.junit.Before;
import org.junit.Test;
import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.pojo.Person;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private JsonConverter jsonConverter;
    private Person person;

    @Before
    public void prepareForTests(){
        jsonConverter = new JsonConverter();
        person = new Person("Maciej", "Miś", 24, 78.5, Person.Gender.MAN);
    }

    @Test
    public void shouldCompareHashMapAndFieldsSize(){
        jsonConverter.convertToJson(person);
        assertEquals(person.getClass().getDeclaredFields().length, jsonConverter.getMap().size());
    }

    @Test
    public void shouldCheckIfJsonIsValid(){

    }
}
