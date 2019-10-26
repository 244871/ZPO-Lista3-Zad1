public class Teacher {
    private String name;
    private String surname;
    private int salary;
    private String district;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //name, surname, salary, district, position
/*
    @Override
    public String toString(){
        StringBuilder bulider = new StringBuilder();
        bulider.append("Car{id= ").append(id).append(", name= ").append(name).append(", price= ").append(price).append("}");
        return bulider.toString();
    }
    */


}
