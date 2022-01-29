package ru.gb.antonov.lesson5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static ru.gb.antonov.lesson5.Student.isMarkValid;
import static ru.gb.antonov.lesson5.Student.isNameValid;

@Repository
public class StudentsDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public StudentsDao (SessionFactory sf) {
        sessionFactory = sf;
    }

    public Student findById (Long id) {
        Student student = null;
        if (sessionFactory != null)
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();
                student = session.get (Student.class, id);
                session.getTransaction().commit();
            }
        return student;
    }

    public List<Student> findAll () {
        List<Student> students = null;
        if (sessionFactory != null)
            try (Session session = sessionFactory.getCurrentSession();) {
                session.beginTransaction();
                Query<Student> qs = session.createQuery ("from Student", Student.class);
                students = qs.getResultList();
                session.getTransaction().commit();
            }
        if (students == null)
            students = emptyList();
        return students;
    }

    public Student add (String name, int mark) {
        if (!isNameValid (name) || !isMarkValid (mark))
            return null;
        Student student = new Student (name, mark);
        return saveOrUpdate (student);
    }

    private Student saveOrUpdate (Student student) {
        if (student != null)
            try (Session s = sessionFactory.getCurrentSession()) {
                s.beginTransaction();
                s.saveOrUpdate (student);
                s.getTransaction().commit();
            }
        return student;
    }

    public Student deleteById (Long id) {
        int deleted = 0;
        Student student = null;
        if (sessionFactory != null) {
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();
                student = session.get (Student.class, id);
                deleted = session.createQuery ("DELETE FROM Student s WHERE s.id = :x")
                                 .setParameter ("x", id)
                                 .executeUpdate();
                session.getTransaction().commit();
            }
            if (deleted != 1)
                student = null;
        }
        return student;
    }

    public boolean changeMark (Long id, int delta) {
        boolean ok = false;
        Student student = findById(id);
        if (student != null && student.setMark (student.getMark() + delta))
            ok = saveOrUpdate (student) != null;
        return ok;
    }

    public int addRandomItems (Integer count) {
        int result = 0;
        Random r = new Random(47);
        if (count > 0) {
            HashSet<Integer> setInt = new HashSet<>(count);
            while (setInt.size() < count)
                setInt.add (r.nextInt());
            Iterator<Integer> it = setInt.iterator();

            try (Session s = sessionFactory.getCurrentSession()) {
                s.beginTransaction();
                while (it.hasNext()) {
                    s.saveOrUpdate (new Student ("Student_"+ it.next(), r.nextInt(5)+1));
                    count--;
                    result++;
                }
                s.getTransaction().commit();
            }
        }
        return result;
    }

    public static List<Student> emptyList () {   return new ArrayList<>();   }
}
