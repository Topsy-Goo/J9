package ru.gb.antonov.lesson7;

//import org.hibernate.annotations.Table;
import org.springframework.context.annotation.Scope;
//import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;


import javax.persistence.*;

import java.time.LocalDateTime;

import static ru.gb.antonov.lesson7.AppLesson7.isStringValid;

@Entity
@Table (name = "students") //< У org.hibernate.annotations.Table нет свойства name.
//@Component //("just_a_student")
//@Scope ("prototype")
public class Student {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")    private Long id;
    @Column(name="name")  private String name;
    @Column(name="age")   private Integer age;
    @Column(name="created_at") private LocalDateTime createdAt;
    @Column(name="updated_at") private LocalDateTime updatedAt;

    public Student () {}
    public Student (String name, int age) {
        if (!setName (name) || !setAge (age))
            throw new IllegalArgumentException();
    }

    public void setId   (Long value)    {    id = value;    }

    public boolean setName (String value)  {
        boolean ok = isNameValid (value);
        if (ok)
            name = value;
        return ok;
    }

    public boolean setAge  (Integer value) {
        boolean ok = isAgeValid (value);
        if (ok)
            age = value;
        return ok;
    }

    public Long    getId ()   {    return id;    }
    public String  getName () {    return name;    }
    public Integer getAge ()  {    return age;    }

    public static boolean isNameValid (String name) { return isStringValid (name); }
    public static boolean isAgeValid (Integer age) { return age > 0; }

    public String toString() {  return String.format ("Student.(%d, %s, %d)", id, name, age);  }
}
