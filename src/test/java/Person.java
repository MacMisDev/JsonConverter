public class Person {
    public String name;
    private int age;
    protected double weight;
    private String surname;
    private static boolean isHuman = true;
    public Gender gender;
    private boolean insurance;
    private short childNumber;
    private char militaryCategory;

    public enum Gender{
        MAN, WOMAN
    }

    public Person(String name, String surname, int age, double weight, Gender gender, boolean insurance, short childNumber, char militaryCategory) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.surname = surname;
        this.gender = gender;
        this.insurance = insurance;
        this.childNumber = childNumber;
        this.militaryCategory = militaryCategory;
    }

    public Person() {
    }

    public char getMilitaryCategory() {
        return militaryCategory;
    }

    public void setMilitaryCategory(char militaryCategory) {
        this.militaryCategory = militaryCategory;
    }

    public short getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(short childNumber) {
        this.childNumber = childNumber;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
