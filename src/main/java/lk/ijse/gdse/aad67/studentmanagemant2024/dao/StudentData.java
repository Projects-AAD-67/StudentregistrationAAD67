package lk.ijse.gdse.aad67.studentmanagemant2024.dao;

import lk.ijse.gdse.aad67.studentmanagemant2024.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface StudentData permits StudentDataProcess{
    StudentDTO getStudent(String studentId, Connection connection) throws SQLException;
    boolean saveStudent(StudentDTO studentDTO,Connection connection);
    boolean deleteStudent(String studentId,Connection connection);
    boolean updateStudent(String studentId,StudentDTO student,Connection connection);
}
