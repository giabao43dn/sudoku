package com.mycompany.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/oop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static PreparedStatement prepareStatement(String query) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Connection connection;

   
    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database!");
        }
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Trả về true nếu tìm thấy user
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(String name, String phone, String username, String password) {
        String query = "INSERT INTO user (name,phonecall,username,password) VALUES (?, ?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean scores(String username, String scores) {
        String query = "SELECT * FROM user WHERE username = ? AND scores = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, scores);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Trả về true nếu tìm thấy user
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEmailExists(String email) {
    String query = "SELECT COUNT(*) FROM user WHERE email = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);  // Gán giá trị email vào câu truy vấn
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            int count = resultSet.getInt(1);  // Lấy kết quả đếm từ câu truy vấn
            if (count > 0) {
                return true;  // Email đã tồn tại
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;  // Email chưa tồn tại
}

public boolean update(String email, String password, String name, String phone) {
    // Kiểm tra email có tồn tại hay chưa trước khi thực hiện insert
    if (checkEmailExists(email)) {
        System.out.println("Email đã tồn tại trong hệ thống.");
        return false;  // Không thực hiện update nếu email đã tồn tại
    }

    String query = "INSERT INTO user (email, password, name, phone) VALUES (?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        statement.setString(2, password);
        statement.setString(3, name);
        statement.setString(4, phone);
        statement.executeUpdate();
        return true;  // Thêm thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Xử lý lỗi khi thực hiện câu lệnh SQL
    }
}

    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}