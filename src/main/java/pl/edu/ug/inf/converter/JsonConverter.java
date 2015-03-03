package pl.edu.ug.inf.converter;

import pl.edu.ug.inf.exceptions.JsonSyntaxException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonConverter {
    private HashMap<String, String> map;

    public JsonConverter() {
    }

    //Gettery
    public HashMap<String, String> getMap() {
        return map;
    }

    //Metoda konwertujaca obiekt na json
    public String convertToJson(Object object){


        map = getNamesAndValues(object.getClass().getDeclaredFields(), object);

        return generateJson(map);
    }

    //Metoda odpowiadająca za konwertowanie jsona na obiekt
    public Object convertFromJson(String json, Object object) throws JsonSyntaxException{
        //Sprawdzamy, czy json zaczyna się od { oraz kończy na } oraz, czy przedostatni znak to nie przecinek
        if(!(json.charAt(0) == 123) || !(json.charAt(json.length()-1) == 125) || (json.charAt(json.length()-2) == 44)){
            throw new JsonSyntaxException("Niepoprawny JSON!");
        }
        json = json.replaceAll(" ", "");
        json = json.replaceAll("\n", "");
        System.out.println(json);
        return null;
    }

    //Metoda zwracajaca HashMapę, w której znajdują się nazwy pól wraz z ich wartościami
    private HashMap getNamesAndValues(Field[] fields, Object object){

        HashMap<String, String> values = new HashMap<String, String>();
        for(Field f: fields){
            //Jeżeli pole jest publiczne
            if(f.getModifiers() == Modifier.PUBLIC){
                try {
                    //Dopisz do HashMapy nazwę zmiennej jako klucz oraz jej wartość
                    values.put(f.getName(), f.get(object).toString());
                } catch (IllegalAccessException e) {
                    System.out.println("Pole prywatne, bądź chronione - brak dostępu!");
                }
            }else{
                f.setAccessible(true);
                try {
                    values.put(f.getName(), f.get(object).toString());
                } catch (IllegalAccessException e) {
                    System.out.println("Pole prywatne, bądź chronione - brak dostępu!");
                }
            }
        }
        return values;
    }
    //Metoda generujaca jsona
    private String generateJson(HashMap<String, String> map){
        String json = "{";
        Iterator iterator = map.entrySet().iterator();
        //Przechodzimy iteratorem po HashMapie dopisując klucz oraz wartość do stringa
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            json += "\""+ (String)entry.getKey() + "\":\"" + entry.getValue() + "\"";
            if(iterator.hasNext()){
               json += ",";
            }
        }
        json += "}";
        return json;
    }
}
