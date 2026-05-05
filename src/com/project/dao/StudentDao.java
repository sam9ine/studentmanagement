package com.project.dao;

import com.project.db.DBconnection;
import java.sql.*;
import com.project.model.Student;

public class StudentDao {

    public String addStudent(Student student) throws Exception {
        String Query = "INSERT INTO students(name, course, roll_no) VALUES(?,?,?)";

        try(Connection connect = DBconnection.getConnection();
            PreparedStatement ps = connect.prepareStatement(Query)){

            ps.setString(1, student.getName());
            ps.setString(2, student.getCourse());
            ps.setString(3, student.getRollNo());

            int rows = ps.executeUpdate();
            return (rows > 0) ? "Success" : "Error";
        } catch(Exception e) {
            return "Exception: " + e.getMessage();
        }
    }


    public void getAllStudents() {
        String query = "SELECT * FROM students";

        try (Connection con = DBconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("Student List");
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String email = rs.getString("email");

                System.out.println(id + " | " + name + " | " + course + " | " + email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
