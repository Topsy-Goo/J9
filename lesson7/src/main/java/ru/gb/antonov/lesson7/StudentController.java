package ru.gb.antonov.lesson7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //("student_controller") // включает компонент
@RequestMapping ("/")
public class StudentController {

// http://localhost:17894/j9l7

    @Autowired //@Qualifier ("student_service")
    private final StudentService studentService;

    public StudentController (StudentService sts) {
        studentService = sts;
    }
}
