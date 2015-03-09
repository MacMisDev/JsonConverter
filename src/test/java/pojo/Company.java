package pojo;

public class Company {
    public String companyName;
    public Person boss;
    public Person employee;

    public Company(String companyName, Person boss, Person employee) {
        this.companyName = companyName;
        this.boss = boss;
        this.employee = employee;
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

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }
}
