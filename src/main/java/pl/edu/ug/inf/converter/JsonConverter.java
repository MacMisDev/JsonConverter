package pl.edu.ug.inf.converter;

import pl.edu.ug.inf.exceptions.JsonConvertToObjectException;
import pl.edu.ug.inf.exceptions.JsonSyntaxException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonConverter {
    private HashMap<String, String> toJson;
    private HashMap<String, String> fromJson;
    private String json;

    public JsonConverter() {
        this.json = "";
    }

    //Gettery
    public HashMap<String, String> getToJson() {
        return toJson;
    }

    public HashMap<String, String> getFromJson() {
        return fromJson;
    }

    //Metoda konwertujaca obiekt na json
    public String convertToJson(Object object){

        toJson = getNamesAndValues(object.getClass().getDeclaredFields(), object);

        return generateJson(toJson);
    }

    //Metoda odpowiadająca za konwertowanie jsona na obiekt
    public Object convertFromJson(String json, Object object) throws JsonSyntaxException, JsonConvertToObjectException, IllegalAccessException {
        //Wyrzucenie wszystkich enterów oraz spacji
        json = json.replaceAll(" ", "");
        json = json.replaceAll("\n", "");

        //Sprawdzamy, czy json zaczyna się od { oraz kończy na } oraz, czy przedostatni znak to nie przecinek
        if(json.length() == 0){
            throw new JsonSyntaxException("empty json");
        }else if(!(json.charAt(0) == 123) || !(json.charAt(json.length()-1) == 125) || (json.charAt(json.length()-2) == 44)){
            throw new JsonSyntaxException("JSON nie zaczyna się od { bądź nie kończy się na } lub posiada , po ostatnim elemencie");
        }

        //Pozbycie się początkowego oraz końcowego znaku
        json = json.substring(1, json.length()-1);

        if(json.length() == 0){
            throw new JsonSyntaxException("empty json");
        }

        fromJson = getDataFromJson(splitJsonByComma(json));

        return fillObjectWithData(fromJson, object);
    }

    //Metoda zwracajaca HashMapę, w której znajdują się nazwy pól wraz z ich wartościami
    private HashMap<String, String> getNamesAndValues(Field[] fields, Object object){

        HashMap<String, String> values = new HashMap<String, String>();
        for(Field f: fields){
            //Jezeli typ pola nie jest typem prostym, enumem badz stringiem, to idz dalej
            if(!f.getType().isPrimitive() && !f.getType().isEnum() && !f.getType().getName().equals("java.lang.String")){
                //Jeżeli to tablica, to wykonaj
                if(f.getType().isArray()){
                    //jak tablica
                //Jeżeli to nie jest tablica, to wywolaj metode generateJsonForInnerClass
                }else{
                    try{
                        f.setAccessible(true);
                        this.json = generateJsonForInnerClass(f.get(object), f.getName());
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }
                }
            //W przeciwnym wypadku dodaj pole do hashmapy
            }else{
                //Jeżeli pole jest publiczne
                if (f.getModifiers() == Modifier.PUBLIC) {
                    try {
                        //Dopisz do HashMapy nazwę zmiennej jako klucz oraz jej wartość
                        values.put(f.getName(), f.get(object).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.setAccessible(true);
                    try {
                        values.put(f.getName(), f.get(object).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return values;
    }

    //Metoda wpisujaca do jsona klasy wewnetrzne w obiekcie
    private String generateJsonForInnerClass(Object object, String name) {
        //Jeżeli json jest pusty, to dopisz znak poczatkowy
        if(json.length() == 0){
            json += "{\"" + name + "\":{";
        }else{
            json += "\"" + name + "\":{";
        }


        for(Field f : object.getClass().getDeclaredFields()){
            //Jezeli typ pola nie jest typem prostym, enumem badz stringiem, to idz dalej
            if(!f.getType().isPrimitive() && !f.getType().isEnum() && !f.getType().getName().equals("java.lang.String")) {
                //Jeżeli to tablica, to wykonaj
                if (f.getType().isArray()) {
                    //jak tablica
                    //Jeżeli to nie jest tablica, to wywolaj metode generateJsonForInnerClass
                } else {
                    try {
                        f.setAccessible(true);
                        this.json = generateJsonForInnerClass(f.get(object), f.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                //Jeżeli pole jest publiczne
                if (f.getModifiers() == Modifier.PUBLIC) {
                    try {
                        json += "\"" + f.getName() + "\":\"" + f.get(object).toString() + "\",";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.setAccessible(true);
                    try {
                        json += "\"" + f.getName() + "\":\"" + f.get(object).toString() + "\",";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //Utnij ostatni przecinek

        json = json.substring(0, json.length()-1);
        json += "},";

        return json;
    }

    //Metoda generujaca jsona
    private String generateJson(HashMap<String, String> map){
        if(json.length() == 0) {
            json += "{";
        }
        //Jeżeli mapa jest pusta, to utnij przecinek
        if(map.size() == 0){
            json = json.substring(0, json.length()-1);
        }

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

    //Metoda dzieląca string na części po przecinkach
    private String[] splitJsonByComma(String json){
        return json.split(",");
    }

    //Metoda zamieniająca tablicę stringów na HashMapę
    private HashMap<String, String> getDataFromJson(String[] data) throws JsonSyntaxException {

        HashMap<String, String> result = new HashMap<String, String>();
        String[] d;

        for(String s : data){

            d = s.split(":");
            try{
                //Jeżeli d[0] zaczyna się i kończy na " to kontynuuj, w innym wypadku rzuć wyjątek
                if((d[0].charAt(0) == 34) && (d[0].charAt(d[0].length()-1) == 34)){
                    //Jeżeli d[1] zaczyna się i kończy na " to kontynuuj
                    if((d[1].charAt(0) == 34) && (d[1].charAt(d[1].length()-1) == 34)){
                        d[0] = d[0].replaceAll("\"", "");
                        d[1] = d[1].replaceAll("\"", "");
                        //W innym wypadku sprawdź, czy początkujący znak bądź ostatni jest ". Jeżeli tak, to rzuć wyjątek
                    }else if((d[1].charAt(0) == 34) || (d[1].charAt(d[1].length()-1) == 34)){
                        throw new JsonSyntaxException("Niepoprawny JSON!");
                        //Jeżeli nie ma znaków " to spróbuj skonwertować na liczbę, w przypadku niepowodzenia wyrzuć wyjątek
                    }else{
                        d[0] = d[0].replaceAll("\"", "");
                        //Jeżeli posiada . to spróbuj skonwertować na double, w innym wypadku na inta
                        if(d[1].contains(".")){
                            try{
                                Double.parseDouble(d[1]);
                            }catch(NumberFormatException e){
                                throw new JsonSyntaxException("Niepoprawny JSON!");
                            }
                        }else{
                            try{
                                Integer.parseInt(d[1]);
                            }catch(NumberFormatException e){
                                throw new JsonSyntaxException("Niepoprawny JSON!");
                            }
                        }
                    }
                }else{
                    throw new JsonSyntaxException("Niepoprawny JSON!");
                }
                //Jeżeli ktoś dał podwójny : to zgłoś wyjątek
                if(d.length == 2){
                    result.put(d[0], d[1]);
                }else{
                    throw new JsonSyntaxException("Niepoprawny JSON!");
                }
            //Jeżeli złapiemy StringIndexOutOfBoundsException to oznacza, ze json jest zle zbudowany.
            }catch(StringIndexOutOfBoundsException e){
                throw new JsonSyntaxException("Niepoprawny JSON!");
            }


        }
        return result;
    }

    //Metoda wypelniajaca obiekt informacjami z jsona
    private Object fillObjectWithData(HashMap<String, String> data, Object object) throws JsonConvertToObjectException, IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        //Jeżeli liczba pól w obiekcie jest mniejsza niż ilość elementów w Jsonie, to wyrzuć wyjątek
        if(fields.length < data.size()){
            throw new JsonConvertToObjectException();
        }

        for(Field f : fields){

            if(data.containsKey(f.getName())){
                f.setAccessible(true);
                //Srprawdź, jakiego typu jest pole w obiekcie, następnie wpisz do niego wartość
                if(f.getType() == byte.class){
                    try{
                        f.set(object, Byte.parseByte(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == short.class){
                    try{
                        f.set(object, Short.parseShort(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == int.class){
                    try{
                        f.set(object, Integer.parseInt(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == long.class){
                    try{
                        f.set(object, Long.parseLong(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == char.class){
                    f.set(object, (data.get(f.getName())).charAt(0));
                }else if(f.getType() == float.class){
                    try{
                        f.set(object, Float.parseFloat(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == double.class){
                    try{
                        f.set(object, Double.parseDouble(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType() == boolean.class){
                    try{
                        f.set(object, Boolean.parseBoolean(data.get(f.getName())));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else if(f.getType().isEnum()){
                    f.set(object, Enum.valueOf((Class<Enum>) f.getType(), data.get(f.getName())));
                }else if(f.getType().isArray()){
                    //todo
                }else{
                    f.set(object, data.get(f.getName()));
                }

            }else{
                f.setAccessible(true);
                //Jeżeli wartość nie została odnaleziona w jsonie, to wpisz 0 bądź null
                if((f.getType() == double.class) || (f.getType() == float.class) || (f.getType() == long.class) || (f.getType() == int.class)) {
                    f.set(object, 0);
                }else if((f.getType() == short.class)){
                    f.set(object, (short)0);
                }else if(f.getType() == boolean.class){
                    f.set(object, false);
                }else if(f.getType() == char.class){
                    f.set(object, (char)0);
                }else{
                    f.set(object, null);
                }

            }
        }

        return object;
    }

}
