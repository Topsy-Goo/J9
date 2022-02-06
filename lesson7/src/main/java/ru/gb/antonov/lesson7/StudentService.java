package ru.gb.antonov.lesson7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepo/*CrudRepository<Student, Long>*/ studentRepo;


/*    @Transactional
    public List<Student> getAllStudentd () {
        long count = studentRepo.count();
        return iterableToList (studentRepo.findAll(), (int)count);
    }*/


}
