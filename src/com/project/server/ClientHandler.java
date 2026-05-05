package com.project.server;

import com.project.dao.StudentDao;
import com.project.model.Student;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private StudentDao studentDao;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.studentDao = new StudentDao();
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            Student student = (Student) in.readObject();
            System.out.println("Server received data for: " + student.getName());

            String result = studentDao.addStudent(student);

            out.println(result);

        } catch (Exception e) {
            System.out.println("ClientHandler Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}