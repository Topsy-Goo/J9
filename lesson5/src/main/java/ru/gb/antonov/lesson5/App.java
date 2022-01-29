package ru.gb.antonov.lesson5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class App {
    private static ConfigurableApplicationContext context;

    public static void main (String[] args) {
        context = SpringApplication.run (App.class, args);
    }

    @Bean private static SessionFactory sessionFactory() {
        Configuration cfg = new Configuration().configure(); //< по умолчальнию считывается "hibernate.cfg.xml".
        SessionFactory sf = cfg.buildSessionFactory();
        return readSqlFile (sf) ? sf : null;
    }

/**  Считываем «базу» из sql-файла и скармливаем хибер-ту. (Считывать содержимое SQL-файла
    хибер-т не умеет, но табуляцию и прочие пробельные символы переваривает нормально.)<p>
    Эта церемония требуется только для разворачивания БД в памяти при работе jdbc:h2:mem:… и
    совершенно не подходит для других случаев.
*/
    private static boolean readSqlFile (SessionFactory sf) {
        boolean result  = false;
        Session session = null;
        try
        {
            //sql = Files.lines(Paths.get(fileName)).collect(Collectors.joining(" "));
            String sql = Files.readString (Paths.get ("db_students.sql"));
            session = sf.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            result = true;
        }
        catch (IOException e) {
            sf.close();
            e.printStackTrace();
        }
        finally {    if (session != null) session.close();    }
        return result;
    }

    public static boolean isStringValid (String s) {    return s != null && !s.isBlank();    }
}
