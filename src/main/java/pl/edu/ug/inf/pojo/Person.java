package pl.edu.ug.inf.pojo;

public class Person {
    public String name;
    protected int age;
    protected double weight;
    private String surname;

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
