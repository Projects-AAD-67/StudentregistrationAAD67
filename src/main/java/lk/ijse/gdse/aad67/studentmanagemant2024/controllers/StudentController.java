package lk.ijse.gdse.aad67.studentmanagemant2024.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad67.studentmanagemant2024.dto.StudentDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //Todo: Save student
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
         //String id  = UUID.randomUUID().toString();
         Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>() {
        }.getClass().getGenericSuperclass());
        studentDTOList.forEach(System.out::println);

//        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
//        studentDTO.setId(id);
//        System.out.println(studentDTO);

    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo:Update student
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Get student details
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //Todo: Delete Student
    }
}
