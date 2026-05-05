package com.project.dao;

import com.project.db.DBconnection;
import java.sql.*;
import com.project.model.Student;

public class StudentDao {

    public String addStudent(Student student) {
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
                String roll = rs.getString("roll_no");

                System.out.println(id + " | " + name + " | " + roll + " | " + course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}