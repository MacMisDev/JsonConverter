import org.junit.*;
import org.junit.rules.ExpectedException;
import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.exceptions.JsonConvertToObjectException;
import pl.edu.ug.inf.exceptions.JsonSyntaxException;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private JsonConverter jsonConverter;
    private Person person;
    private Person maciej;

    @Before
    public void prepareForTests(){
        jsonConverter = new JsonConverter();
        person = new Person("Maciej", "Miś", 24, 78.5, Person.Gender.MAN, true, (short)0, (char)65);
        maciej = new Person();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void CompareHashMapAndFieldsSize(){
        jsonConverter.convertToJson(person);
        assertEquals(person.getClass().getDeclaredFields().length, jsonConverter.getToJson().size());

    }

    @Test
    public void CheckIfJsonIsValid() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
        final double epsilon = 1e-15;
        String json = jsonConverter.convertToJson(person);
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


    @Test
    public void doubleColonInJson() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\"::\"Maciej\"\n" +
                "}", maciej);
    }

    @Test
    public void comaAfterLastValue() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\":\"maciej\",\n" +
                "}", maciej);
    }

    @Test
    public void emptyJsonTest() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
        exception.expect(JsonSyntaxException.class);
        exception.expectMessage("empty json");
        jsonConverter.convertFromJson("{\n" +
                "\n" +
                "}", maciej);
    }
}
