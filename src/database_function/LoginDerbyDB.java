package database_function;

import librarymanagementsystem.LibraryAdmin;
import librarymanagementsystem.LibraryUser;
import librarymanagementsystem.LoginAdmin;
import librarymanagementsystem.LoginUser;

import javax.swing.*;

public class LoginDerbyDB {

    private static volatile LoginDerbyDB instance;

    public static LoginDerbyDB getInstance() {
        if (instance == null) {
            synchronized (LoginDerbyDB.class) {
                if (instance == null) {
                    instance = new LoginDerbyDB();
                }
            }
        }
        return instance;
    }

    public boolean ifIdExists(String userId) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(USER_ID AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean ifEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CAST(EMAIL AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;

    }


    public void LoginUser(String id, String email, String password, LoginUser loginUser) {
        String sql = "SELECT * FROM USERS WHERE CAST(USER_ID AS VARCHAR(128)) = ? AND CAST(EMAIL AS VARCHAR(128)) = ? AND CAST(PASSWORD AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("USER_ID");
                 String name = resultSet.getString("NAME");
                 String userType = resultSet.getString("USER_TYPE");

                 if (userType.equals("User")) {
                     LibraryUser user = new LibraryUser(userId, name);
                     user.setVisible(true);
                     loginUser.dispose();
                     JOptionPane.showMessageDialog(null, "Welcome " + name, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                 }else {
                     System.out.println("No User Found");
                     JOptionPane.showMessageDialog(null, "No User Found", "Error", JOptionPane.ERROR_MESSAGE);
                 }

            } else {
                System.out.println("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoginAdmin(String id, String email, String password, LoginAdmin admin) {
        String sql = "SELECT * FROM USERS WHERE CAST(USER_ID AS VARCHAR(128)) = ? AND CAST(EMAIL AS VARCHAR(128)) = ? AND CAST(PASSWORD AS VARCHAR(128)) = ?";
        try (var connection = DerbyConnectinDB.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("USER_ID");
                String name = resultSet.getString("NAME");

                if (resultSet.getString("USER_TYPE").equals("Admin")) {
                    LibraryAdmin adminUser = new LibraryAdmin(userId);
                    adminUser.setVisible(true);
                    admin.dispose();
                    JOptionPane.showMessageDialog(null, "Welcome " + name, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("No Admin User Found");
                    JOptionPane.showMessageDialog(null, "No Admin User Found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                System.out.println("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
