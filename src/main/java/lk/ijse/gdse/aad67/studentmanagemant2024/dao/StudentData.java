package lk.ijse.gdse.aad67.studentmanagemant2024.dao;

import lk.ijse.gdse.aad67.studentmanagemant2024.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface StudentData {
    StudentDTO getStudent(String studentId, Connection connection) throws SQLException;
    String saveStudent(StudentDTO studentDTO,Connection connection);
    boolean deleteStudent(String studentId,Connection connection);
    boolean updateStudent(String studentId,StudentDTO student,Connection connection);
}
