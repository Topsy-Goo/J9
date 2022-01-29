package ru.gb.antonov.lesson6;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet
{
    // http://localhost:8080/j9l6/hello

    @Override protected void doGet (HttpServletRequest requ, HttpServletResponse resp) throws IOException {
        resp.getWriter().printf("<h2>Привет, Мир!</h2>");
    }
}
