# JsonConverter
[![Build Status](https://travis-ci.org/MacMisDev/JsonConverter.svg?branch=master)](https://travis-ci.org/MacMisDev/JsonConverter)
Prosty konwerter Jsona. 
Konwerter powinien bezbłędnie wygenerować Jsona z obiektu dzięki metodzie `convertToJson(object)`. Metoda powinna poradzić sobię z obiektem, który zawiera w sobie tablice jednowymiarowe, inne klasy, enumy, typy proste. Wszystko dla pól prywatnych oraz publicznych.
Metoda `convertFromJson(json, object)` przetworzy jsona na obiekt. Jednakże moja metoda zadziała **tylko** dla obiektu posiadającego typy proste, stringi oraz enumy.

#####Dane:
| Imię i nazwisko | Indeks |
| ------ | :------: |
|  [Maciej Miś](https://github.com/MacMisDev)  |  206295  |
