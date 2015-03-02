package pl.edu.ug.inf.pojo;

public class Person {
    public String name;
    public int age;
    public double weight;
    public String surname;

    public Person(String name, String surname, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
