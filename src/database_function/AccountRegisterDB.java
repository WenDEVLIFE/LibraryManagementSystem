package database_function;

import model.AccountModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public boolean isUserExists(String userId) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }
    public boolean ifNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(NAME AS VARCHAR(128)) = ?";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean ifEmailExist(String email) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(EMAIL AS VARCHAR(128)) = ?";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public List<AccountModel> getUsers() {
        List<AccountModel> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("USER_ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                String userType = resultSet.getString("USER_TYPE");
                String programOrDepartment = resultSet.getString("PROGRAM_OR_DEPARTMENT");
                String yearLevelRank = resultSet.getString("YEAR_LEVEL_RANK");

                AccountModel user = new AccountModel(id, name, email, password, userType, programOrDepartment, yearLevelRank);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(Map<String, String> updatedUser) {
        String sql = "UPDATE USERS SET NAME = ?, EMAIL = ?, PASSWORD = ?, USER_TYPE = ?, PROGRAM_OR_DEPARTMENT = ?, YEAR_LEVEL_RANK = ? WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updatedUser.get("name"));
            preparedStatement.setString(2, updatedUser.get("email"));
            preparedStatement.setString(3, updatedUser.get("password"));
            preparedStatement.setString(4, updatedUser.get("user_type"));
            preparedStatement.setString(5, updatedUser.get("program"));
            preparedStatement.setString(6, updatedUser.get("year"));
            preparedStatement.setString(7, updatedUser.get("user_id"));

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("User updated successfully.");
                JOptionPane.showMessageDialog(null, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to update user.");
                JOptionPane.showMessageDialog(null, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userID) {
        String sql = "DELETE FROM USERS WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userID);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("User deleted successfully.");
                JOptionPane.showMessageDialog(null, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to delete user.");
                JOptionPane.showMessageDialog(null, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getAdminCount() {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(USER_TYPE AS VARCHAR(128)) = 'Admin'";
        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
   }

    public int getUserCount() {

        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(USER_TYPE AS VARCHAR(128)) = 'User'";

        try (Connection connection = DerbyConnectinDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return 0;
    }
}