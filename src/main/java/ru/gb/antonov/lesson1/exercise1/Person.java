package ru.gb.antonov.lesson1.exercise1;

import java.util.Objects;

import static ru.gb.antonov.Library.isStringValid;

public class Person {

    private String firstName;
    private String middleName;
    private String lastName;   //< используется в hashCode()
    private Integer age;       //< используется в hashCode()
    private String gender;
    private String country;
    private String address;
    private String phone;

//-------------------- Конструирование ---------------------------
    private Person (String fname, String mname, String lname, int ag, String gend) {
        if (!setFirstName (fname)
         || !setMiddleName (mname)
         || !setLastName (lname)
         || !setAge (ag)
         || !setGender (gend))
            throw new IllegalArgumentException();
    }

    public static Builder newPerson (String fname, String mname, String lname, int ag, String gend) {
        return new Builder (fname, mname, lname, ag, gend);
    }

    public static class Builder {
        private final Person person;

        private Builder (String fname, String mname, String lname, int ag, String gend) {
            person = new Person (fname, mname, lname, ag, gend);
        }
        public Builder withCountry (String value) {
            if (!person.setCountry (value))    throw new IllegalArgumentException();
            return this;
        }
        public Builder withAddress (String value) {
            if (!person.setAddress (value))    throw new IllegalArgumentException();
            return this;
        }
        public Builder withPhone (String value) {
            if (!person.setPhone (value))    throw new IllegalArgumentException();
            return this;
        }
        public Person build () { return person; }
    }
//-------------------- Геттеры и сеттеры -------------------------
    public String getFirstName () {  return firstName;  }
    public String getMiddleName () {  return middleName;    }
    public String getLastName () {  return lastName;    }
    public Integer getAge () {  return age;  }
    public String getGender () {  return gender;    }
    public String getCountry () {  return country;  }
    public String getAddress () {  return address;  }
    public String getPhone () {  return phone;  }

    public boolean setFirstName (String value) {
        boolean ok = isStringValid (value);
        if (ok) firstName = value;
        return ok;
    }
    public boolean setMiddleName (String value) {
        boolean ok = isStringValid (value);
        if (ok) middleName = value;
        return ok;
    }
    public boolean setLastName (String value) {
        boolean ok = isStringValid (value);
        if (ok) lastName = value;
        return ok;
    }
    public boolean setAge (int value) {
        boolean ok = value >= 0;
        if (ok) age = value;
        return ok;
    }
    public boolean setGender (String value) {
        boolean ok = isStringValid (value);
        if (ok) gender = value;
        return ok;
    }
    public boolean setCountry (String value) {
        boolean ok = isStringValid (value);
        if (ok) country = value;
        return ok;
    }
    public boolean setAddress (String value) {
        boolean ok = isStringValid (value);
        if (ok) address = value;
        return ok;
    }
    public boolean setPhone (String value) {
        boolean ok = isStringValid (value);
        if (ok) phone = value;
        return ok;
    }
//-------------------- Стандартные методы ------------------------
    @Override public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age.equals (person.age)
            && lastName.equals (person.lastName)
            && firstName.equals (person.firstName)
            && middleName.equals (person.middleName)
            && gender.equals (person.gender)
            && country.equals (person.country)
            && address.equals (person.address)
            && phone.equals (person.phone);
    }
    @Override public int hashCode () {
        return Objects.hash (lastName, age);
    }
}
