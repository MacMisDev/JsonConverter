package tests;

import org.junit.*;
import org.junit.rules.ExpectedException;
import pl.edu.ug.inf.converter.JsonConverter;
import pl.edu.ug.inf.exceptions.JsonConvertToObjectException;
import pl.edu.ug.inf.exceptions.JsonSyntaxException;
import pojo.Company;
import pojo.Partnership;
import pojo.Person;
import pojo.Table;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private JsonConverter jsonConverter;
    private Person person;
    private Person maciej;
    private Company company;
    private Partnership partnership;
    private Table table;

    @Before
    public void prepareForTests(){
        jsonConverter = new JsonConverter();
        person = new Person("Maciej", "Miś", 24, 78.5, Person.Gender.MAN, true, (short)0, (char)65);
        maciej = new Person();
        company = new Company("M.M Industries" ,person, person);
        partnership = new Partnership(company, company);
        table = new Table(company);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void CompareHashMapAndFieldsSizeTest(){
        jsonConverter.convertToJson(person);
        assertEquals(person.getClass().getDeclaredFields().length, jsonConverter.getToJson().size());

    }

    @Test
    public void CheckIfJsonIsValidTest() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
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
    public void doubleColonInJsonTest() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\"::\"Maciej\"\n" +
                "}", maciej);
    }

    @Test
    public void comaAfterLastValueTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\":\"maciej\",\n" +
                "}", maciej);
    }

    @Test
    public void comaBeforeFirstValueTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{,\n" +
                "\"name\":\"maciej\"\n" +
                "}", maciej);
    }

    @Test
    public void lackOfQuotationMarkTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "name:\"maciej\"\n" +
                "}" ,maciej);
    }

    @Test
    public void invalidEmptyJsonTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("", maciej);
    }

    @Test
    public void emptyJsonTest() throws JsonConvertToObjectException, IllegalAccessException, JsonSyntaxException {
        exception.expect(JsonSyntaxException.class);
        exception.expectMessage("empty json");
        jsonConverter.convertFromJson("{\n" +
                "\n" +
                "}", maciej);
    }

    @Test
    public void invalidJsonFirstCharacterTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("\n" +
                "\"name\":\"maciej\"\n" +
                "}", maciej);
    }

    @Test
    public void invalidJsonLastsCharacterTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\":\"maciej\"\n" +
                "", maciej);
    }

    @Test
    public void numberWithoutQuotationMarkTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        jsonConverter.convertFromJson("{\n" +
                "\"age\":24\n" +
                "}", maciej);
        assertEquals(maciej.getAge(), person.getAge());
    }

    @Test
    public void numberWithQuotationMarkTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        jsonConverter.convertFromJson("{\n" +
                "\"age\":\"24\"\n" +
                "}", maciej);
        assertEquals(maciej.getAge(), person.getAge());
    }

    @Test
    public void randomWordAfterQuotationMarkTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"age\"invalid:\"24\"\n" +
                "}", maciej);
    }

    @Test
    public void missingComaTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonSyntaxException.class);
        jsonConverter.convertFromJson("{\n" +
                "\"name\":\"maciej\"\n" +
                "\"age\":24\n" +
                "}", maciej);
    }

    @Test
    public void moreDataFromJsonThanFieldsInObjectTest() throws IllegalAccessException, JsonSyntaxException, JsonConvertToObjectException {
        exception.expect(JsonConvertToObjectException.class);
        jsonConverter.convertFromJson("{\"insurance\":\"true\",\"weight\":\"78.5\",\"isHuman\":\"true\",\"age\":\"24\",\"name\":\"Maciej\",\"gender\":\"MAN\",\"surname\":\"Miś\",\"militaryCategory\":\"A\",\"childNumber\":\"0\", \"country\":\"Polska\"}", maciej);
    }

    @Test
    public void test(){
        String s = "bla";
        System.out.println(jsonConverter.convertToJson(s));
    }



}
