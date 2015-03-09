package pojo;

public class Partnership {
    public Company company1;
    public Company company2;

    public Partnership(Company company1, Company company2) {
        this.company1 = company1;
        this.company2 = company2;
    }

    public Company getCompany1() {
        return company1;
    }

    public void setCompany1(Company company1) {
        this.company1 = company1;
    }

    public Company getCompany2() {
        return company2;
    }

    public void setCompany2(Company company2) {
        this.company2 = company2;
    }
}
