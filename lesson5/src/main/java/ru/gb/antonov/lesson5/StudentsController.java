package ru.gb.antonov.lesson5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentsController {
    public static final String
        PROMPT_MARK_CHANGED     = "Оценка изменена.",
        PROMPT_MARK_NOT_CHANGED = "Не удалось изменить оценку",
        REDIRECT_TO_SHOW_ALL    = "redirect:/showallstudents",
        PAGE_NAME_INDEX         = "index",
        PAGE_NAME_FORM          = "form",
        PAGE_NAME_ALLSTUDENTS   = "allstudents",
        ATTRIBUTE_PROMPT        = "prompt",
        ATTRIBUTE_ALL_STUDENTS  = "allthestudents",
        PROMPT_FORMAT_ELEMENTS_ADDED = "Добавлены %d новых элементов.";
    private final StudentsDao studentsDao;
    private String prompt4Allstudents = "";

    @Autowired public StudentsController (StudentsDao repo) {
        studentsDao = repo;
    }

    @GetMapping    // http://localhost:7894/j9l5
    public String showMainPage() {    return PAGE_NAME_INDEX;    }

    @GetMapping ("/showallstudents")    // http://localhost:7894/j9l5/showallstudents
    public String getAll (Model model) {
        if (studentsDao != null)
            model.addAttribute (ATTRIBUTE_ALL_STUDENTS, studentsDao.findAll());
        String msg = prompt4Allstudents;
        prompt4Allstudents = "";
        model.addAttribute (ATTRIBUTE_PROMPT, msg);
        return PAGE_NAME_ALLSTUDENTS;
    }

/** Вызывается при перенапрвлении на страницу формы. */
    @GetMapping ("/create")    // http://localhost:7894/j9l5/create
    public String createItem (Model model) {
        model.addAttribute (ATTRIBUTE_PROMPT, "Заполните форму и нажмите кнопку 'Сохранить'.");
        return PAGE_NAME_FORM;
    }

/** Вызывается при нажатии submit-кнопки в форме. */
    @PostMapping ("/create")
    public String addItem (@RequestParam(name = "name") String name,
                           @RequestParam(name = "mark") Integer mark, Model model)
    {
        Student student = studentsDao.add (name, mark);
        String msg = student != null ? "Студент создан." : "Не удалось создать студента.";
        model.addAttribute (ATTRIBUTE_PROMPT, msg);
        return PAGE_NAME_FORM;
    }

    @GetMapping ("/delete/{id}")
    public String deleteItem (@PathVariable Long id, Model model) {
        prompt4Allstudents = (studentsDao.deleteById (id) != null) ? "Студент удалён." : "Не удалось удалить студента.";
        return REDIRECT_TO_SHOW_ALL;
    }

    @GetMapping ("/decrease_mark/{id}")
    public String decreaseMark (@PathVariable Long id, Model model) {
        prompt4Allstudents = (studentsDao.changeMark (id, -1)) ? PROMPT_MARK_CHANGED : PROMPT_MARK_NOT_CHANGED;
        return REDIRECT_TO_SHOW_ALL;
    }

    @GetMapping ("/increase_mark/{id}")
    public String increaseMark (@PathVariable Long id, Model model) {
        prompt4Allstudents = (studentsDao.changeMark (id, 1)) ? PROMPT_MARK_CHANGED : PROMPT_MARK_NOT_CHANGED;
        return REDIRECT_TO_SHOW_ALL;
    }

    @GetMapping ("/add/{n}")
    public String addRandomItems (@PathVariable(name = "n") Integer count, Model model) {
        count = studentsDao.addRandomItems (count);
        prompt4Allstudents = String.format (PROMPT_FORMAT_ELEMENTS_ADDED, count);
        return REDIRECT_TO_SHOW_ALL;
    }

    @GetMapping ("/add1000")
    public String addRandomItems1000 (Model model) {
        int count = studentsDao.addRandomItems (1000);
        prompt4Allstudents = String.format (PROMPT_FORMAT_ELEMENTS_ADDED, count);
        return REDIRECT_TO_SHOW_ALL;
    }
}
