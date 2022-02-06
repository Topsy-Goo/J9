package ru.gb.antonov.lesson7;


import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@ComponentScan ("ru.gb.antonov.lesson7")
//@EnableTransactionManagement
@EnableJpaRepositories //(basePackageClasses = ru.gb.antonov.lesson7.StudentRepo.class)
//@RunWith(SpringRunner.class)
public class AppLesson7 {

    private static EntityManagerFactory entityManagerFactory;
    //private static SessionFactory       sessionFactory;

    public static void main (String[] args) {
        ApplicationContext context =
            new AnnotationConfigApplicationContext (AppLesson7.class);
            //new ClassPathXmlApplicationContext("ApplicationContext.xml");

        String[] classNames = context.getBeanDefinitionNames()/*getBeanNamesForAnnotation (Repository.class)*/;
        //String[] classNames = context.getBeanNamesForAnnotation (Service.class);
        for (String s : classNames)
            System.out.println(s);

        if (classNames.length > 0) {
            if (!initFlyway())
                System.exit(-1);

/*            Student student = context.getBean (Student.class);
            student.setAge(19);
            student.setName("Шурик");
            System.out.println ("\n"+student);*/
        }
        else System.err.println ("\nОШИБКА! Отсутствует бин репозитория!\n");
    }

    private static boolean initFlyway () {
        Flyway flyway = Flyway.configure()
                              .dataSource("jdbc:h2:mem:mydatabase;MODE=PostgreSQL", "sa", "1")
                              .load();
        flyway.migrate();
        //DataSource dataSource = flyway.getConfiguration().getDataSource();
        return true;
    }

/*    @Bean public static DataSource dataSource() {
        Flyway flyway = Flyway.configure()
                              .dataSource("jdbc:h2:mem:mydatabase;MODE=PostgreSQL", "sa", "1")
                              .load();
        flyway.migrate();
        return flyway.getConfiguration().getDataSource();
        //DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName (environment.getProperty("jdbc.driverClassName"));
        //dataSource.setUrl (environment.getProperty("jdbc.url"));
        //dataSource.setPassword (environment.getProperty("jdbc.password"));
        //dataSource.setUsername (environment.getProperty("jdbc.username"));
        //return dataSource;
    }

    @Autowired static Environment environment;
    @Bean public static JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory (entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }

    @Bean public static LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPersistenceProviderClass (HibernatePersistenceProvider.class);
        bean.setPackagesToScan("com.spring.model");
        bean.setJpaProperties(hibernateProperties());
        return bean;
    }

    private static Properties hibernateProperties () {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        return properties;
    }
*/
    @Bean public /*private*/ static EntityManagerFactory entityManagerFactory () {
        entityManagerFactory = Persistence.createEntityManagerFactory ("java9lesson7"); //< имя unit'а в файле META-INF\persistence.xml
        //return readDataBase (entityManagerFactory) ? entityManagerFactory : null;
        return entityManagerFactory;
    }

/*    @Bean private static SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.l7.xml");
        SessionFactory                  sf  = cfg.buildSessionFactory();
        return readSqlFile (sf) ? sf : null;
    }*/

    public static EntityManager getEntityManager () {
        return (entityManagerFactory != null  &&  entityManagerFactory.isOpen())
                ? entityManagerFactory.createEntityManager()
                : null;
    }

/* *  Считываем «базу» из sql-файла и скармливаем хибер-ту. (Считывать содержимое SQL-файла
    хибер-т не умеет, но табуляцию и прочие пробельные символы переваривает нормально.)<p>
    Эта церемония требуется только для разворачивания БД в памяти при работе jdbc:h2:mem:… и
    совершенно не подходит для других случаев.
*/
/*    private static boolean readDataBase (EntityManagerFactory emf) {
        boolean       ok = false;
        EntityManager em = null;
        try {
            String sql = Files.readString (Paths.get ("db_students_l7.sql"));
            em = getEntityManager();
            if (em != null) {
                em.getTransaction().begin();
                em.createNamedQuery(sql).executeUpdate();
                em.getTransaction().commit();
                ok = true;
            }
        }
        catch (IOException e) {
            emf.close();
            throw new RuntimeException (e);
        }
        finally {  if (em != null)  em.close();  }
        return ok;
    }*/

/*    private static boolean readSqlFile (SessionFactory sf) {
        boolean result  = false;
        Session session = null;
        try
        {   //sql = Files.lines(Paths.get(fileName)).collect(Collectors.joining(" "));
            String sql = Files.readString (Paths.get ("db_students_l7.sql"));
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
    }*/

    public static boolean isStringValid (String s) {    return s != null && !s.trim().isEmpty()/*isBlank()*/;    }
}
