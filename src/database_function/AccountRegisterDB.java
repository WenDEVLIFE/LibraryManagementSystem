package database_function;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class AccountRegisterDB {

    private static volatile AccountRegisterDB instance;

    public static AccountRegisterDB getInstance() {
        if (instance == null) {
            synchronized (AccountRegisterDB.class) {
                if (instance == null) {
                    instance = new AccountRegisterDB();
                }
            }
        }
        return instance;
    }

    public void addUser(Map<String, String> userData) {
        String sql = "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD, USER_TYPE, PROGRAM_OR_DEPARTMENT, YEAR_LEVEL_RANK) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userData.get("user_id"));
            preparedStatement.setString(2, userData.get("name"));
            preparedStatement.setString(3, userData.get("email"));
            preparedStatement.setString(4, userData.get("password"));
            preparedStatement.setString(5, userData.get("user_type"));
            preparedStatement.setString(6, userData.get("program"));
            preparedStatement.setString(7, userData.get("year"));

           int  result  =    preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("User added successfully.");
                JOptionPane.showMessageDialog(null, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to add user.");
                JOptionPane.showMessageDialog(null, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}