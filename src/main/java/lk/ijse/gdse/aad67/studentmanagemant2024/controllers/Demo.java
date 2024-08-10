package lk.ijse.gdse.aad67.studentmanagemant2024.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(urlPatterns = "/demo")
public class Demo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        System.out.println(queryString);
        var myHader = req.getHeader("X-myHeader");
        System.out.println(myHader);



    }
}
