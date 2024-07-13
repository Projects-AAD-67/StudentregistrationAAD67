package lk.ijse.gdse.aad67.studentmanagemant2024.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.aad67.studentmanagemant2024.dto.StudentDTO;
import lk.ijse.gdse.aad67.studentmanagemant2024.util.UtilProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/student",
        initParams = {
          @WebInitParam(name = "driver-class",value = "com.mysql.cj.jdbc.Driver"),
          @WebInitParam(name = "dbURL",value = "jdbc:mysql://localhost:3306/aad67JavaEE?createDatabaseIfNotExist=true"),
          @WebInitParam(name = "dbUserName",value = "root"),
          @WebInitParam(name = "dbPassword",value = "mysql"),
        }
)
public class StudentController extends HttpServlet {
    Connection connection;
    static String SAVE_STUDENT = "INSERT INTO student (id,name,city,email,level) VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    @Override
    public void init() throws ServletException {
        try {
//            var driverCalss = getServletContext().getInitParameter("driver-class");
//            var dbUrl = getServletContext().getInitParameter("dbURL");
//            var userName = getServletContext().getInitParameter("dbUserName");
//            var password = getServletContext().getInitParameter("dbPassword");
            // Get configs from servlet
            var driverCalss = getServletConfig().getInitParameter("driver-class");
            var dbUrl = getServletConfig().getInitParameter("dbURL");
            var userName = getServletConfig().getInitParameter("dbUserName");
            var password = getServletConfig().getInitParameter("dbPassword");
            Class.forName(driverCalss);
           this.connection =  DriverManager.getConnection(dbUrl,userName,password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //Todo: Save student
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
         Jsonb jsonb = JsonbBuilder.create();
         StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
         studentDTO.setId(UtilProcess.generateId());
         System.out.println(studentDTO);
         // Persist Data
        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getCity());
            ps.setString(4, studentDTO.getEmail());
            ps.setString(5, studentDTO.getLevel());
            if(ps.executeUpdate() != 0){
                writer.write("Student Saved");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Student Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try (var writer = resp.getWriter()){
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
            var studentID = req.getParameter("stu-id");
            Jsonb jsonb = JsonbBuilder.create();
            var updatedStudent = jsonb.fromJson(req.getReader(), StudentDTO.class);
            ps.setString(1, updatedStudent.getName());
            ps.setString(2, updatedStudent.getCity());
            ps.setString(3, updatedStudent.getEmail());
            ps.setString(4, updatedStudent.getLevel());
            ps.setString(5, studentID);
            if(ps.executeUpdate() != 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update Failed");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");

        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setLevel(resultSet.getString("level"));
            }
            System.out.println(studentDTO);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(studentDTO,writer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var stuId = req.getParameter("stu-id");
        try (var writer = resp.getWriter()){
            var ps = this.connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1, stuId);
            if(ps.executeUpdate() != 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
               writer.write("Delete Failed");
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
