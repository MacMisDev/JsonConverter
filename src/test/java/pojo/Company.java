package pojo;

public class Company {
    public String companyName;
    public Person boss;

    public Company(String companyName, Person boss) {
        this.companyName = companyName;
        this.boss = boss;
    }

    public Company() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Person getBoss() {
        return boss;
    }

    public void setBoss(Person boss) {
        this.boss = boss;
    }
}
