package ru.gb.antonov.lesson1.exercise1;

import java.util.Objects;

import static ru.gb.antonov.Library.isStringValid;

public class Person {

    private String firstName;   //< используется в hashCode()
    private String middleName;
    private String lastName;    //< используется в hashCode()
    private Integer age;
    private String gender;
    private String country;
    private String address;
    private String phone;

//-------------------- Конструирование ---------------------------
/*  Замечание преподавателя: логику этого конструктора лучше перенести в метод build() билдера.    */
/*  Замечание преподавателя (проигнорировано): зачем то старательно сокращаете названия переменных. это плохая практика, проходили как один из антипаттернов - пишите целиком, чтобы было понятно. вы ничего не выиграете пытаюсь недописать пару символов    */
    private Person (String fname, String lname) {
        if (!setFirstName (fname)
         || !setLastName (lname)/*
         || !setMiddleName (mname)
         || !setAge (ag)
         || !setGender (gend)*/)
            throw new IllegalArgumentException();
    }
/*  Замечание преподавателя: билдер в том числе помогает избавиться от большого количества аргументов в методе, которые усложняют внесение изменений. Стоит вам добавить еще один обязательный параметр и множество вхождений сломается и нужно будет искать где именно вставить аргумент. Билдер потому и создается обычно констурктором по умолчанию и потом заполняется. а уже при окончательной генерации можно завалидировать, что все нужные поля были инициированы.  */
    public static Builder newPerson (String fname, String lname) {
        return new Builder (fname, lname);
    }

    public static class Builder {
        private final Person person;

        private Builder (String fname, String lname) {
            person = new Person (fname, lname);
        }
        public Builder withMiddleName (String value) {
            if (!person.setMiddleName (value))    throw new IllegalArgumentException();
            return this;
        }
        public Builder withAge (Integer value) {
            if (!person.setAge (value))    throw new IllegalArgumentException();
            return this;
        }
        public Builder withGender (String value) {
            if (!person.setGender (value))    throw new IllegalArgumentException();
            return this;
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
        public Person build () {
            if (person.firstName  == null
             || person.lastName   == null
             || person.middleName == null
             || person.age        == null
             || person.gender     == null
             || person.country    == null
             || person.address    == null
             || person.phone      == null
             ) throw new IllegalArgumentException();
            return person;
        }
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
/*    @Override public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return
        firstName.equals(person.firstName)
        && lastName.equals(person.lastName)
        && Objects.equals(middleName, person.middleName)
        && Objects.equals(age, person.age)
        && Objects.equals(gender, person.gender)
        && Objects.equals(country, person.country)
        && Objects.equals(address, person.address)
        && Objects.equals(phone, person.phone);
    }*/

    @Override public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == (person.age.intValue())
               && middleName.equals (person.middleName)
               && gender.equals (person.gender)
               && country.equals (person.country)
               && address.equals (person.address)
               && phone.equals (person.phone)
               && firstName.equals (person.firstName)
               && lastName.equals (person.lastName);
    }
    @Override public int hashCode () {
        return Objects.hash (lastName, firstName);
    }
}
