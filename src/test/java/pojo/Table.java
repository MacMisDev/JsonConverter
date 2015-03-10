package pojo;

import java.util.Random;

public class Table {
    private Company company;
    private int[] tablicaInt = new int[30];
    private String[] tablicaString = new String[30];
    private double[] tablicaDouble = new double[30];

    public Table(Company company) {
        this.company = company;
        Random generator = new Random();
        for(int i = 0; i < 30; i++){
            this.tablicaInt[i] = generator.nextInt();
            this.tablicaDouble[i] = generator.nextDouble()*10;
            this.tablicaString[i] = Integer.toString(i);
        }
    }

    public int[] getTablicaInt() {
        return tablicaInt;
    }

    public void setTablicaInt(int[] tablicaInt) {
        this.tablicaInt = tablicaInt;
    }

    public String[] getTablicaString() {
        return tablicaString;
    }

    public void setTablicaString(String[] tablicaString) {
        this.tablicaString = tablicaString;
    }

    public double[] getTablicaDouble() {
        return tablicaDouble;
    }

    public void setTablicaDouble(double[] tablicaDouble) {
        this.tablicaDouble = tablicaDouble;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
