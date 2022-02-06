package ru.gb.antonov.lesson5;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.gb.antonov.lesson5.App.isStringValid;

@Entity
@Table (name="students")
public class Student implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id") private Long id;
    @Column(name="name") private String name;
    @Column(name="mark") private Integer mark;
    @Column(name="created_at") private LocalDateTime createdAt;
    @Column(name="updated_at") private LocalDateTime updatedAt;

    private Student () {}
    public Student (String name, Integer mark) {
        if (!setName (name) || !setMark (mark))
            throw new IllegalArgumentException();
    }

    public Long    getId ()   { return id; }
    public String  getName () { return name; }
    public Integer getMark () { return mark; }

    private void setId (Long value)      { id = value; }

    public boolean setName (String value)  {
        boolean ok = isNameValid (value);
        if (ok)
            name = value;
        return ok;
    }
    public boolean setMark (Integer value) {
        boolean ok = isMarkValid (value);
        if (ok)
            mark = value;
        return ok;
    }
    public static boolean isNameValid (String name) { return isStringValid (name); }
    public static boolean isMarkValid (Integer mark) { return mark != null && mark > 0 && mark <= 5; }

    public String toString() {  return String.format ("Student.(%d, %s, %d)", id, name, mark);  }
}
