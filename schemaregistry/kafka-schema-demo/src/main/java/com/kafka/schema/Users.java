package com.kafka.schema;

public class Users {

    String name;
    String age;
    String phone;
public Users(){}
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        }
        else{
            Users otherPeople = (Users)obj;
            result = name.equals(((Users) obj).getName()) &&  age.equals(((Users) obj).getAge());
        } // end else

        return result;
    }

}
