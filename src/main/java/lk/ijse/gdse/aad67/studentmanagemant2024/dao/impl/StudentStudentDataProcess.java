package lk.ijse.gdse.aad67.studentmanagemant2024.dao.impl;

import lk.ijse.gdse.aad67.studentmanagemant2024.dao.StudentData;
import lk.ijse.gdse.aad67.studentmanagemant2024.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentStudentDataProcess implements StudentData {
    static String SAVE_STUDENT = "INSERT INTO student (id,name,city,email,level) VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    @Override
    public StudentDTO getStudent(String studentId, Connection connection) throws SQLException {
        var studentDTO = new StudentDTO();
        try {
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setCity(resultSet.getString("city"));
                studentDTO.setEmail(resultSet.getString("email"));
                studentDTO.setLevel(resultSet.getString("level"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return studentDTO;
    }

    @Override
    public String saveStudent(StudentDTO studentDTO, Connection connection) {
        return "";
    }

    @Override
    public boolean deleteStudent(String studentId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateStudent(String studentId, StudentDTO student, Connection connection) {
        return false;
    }
}
