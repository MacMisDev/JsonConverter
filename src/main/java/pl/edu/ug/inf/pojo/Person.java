package pl.edu.ug.inf.pojo;

public class Person {
    public String name;
    private final int age;
    protected double weight;
    private String surname;
    private static boolean isHuman = true;
    public Gender gender;

    public enum Gender{
        MAN, WOMAN
    }

    public Person(String name, String surname, int age, double weight, Gender gender) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.surname = surname;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static boolean isIsHuman() {
        return isHuman;
    }

    public static void setIsHuman(boolean isHuman) {
        Person.isHuman = isHuman;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
