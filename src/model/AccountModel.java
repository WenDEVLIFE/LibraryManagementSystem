package model;

public class AccountModel {

    String id;

    String  name;

    String email;

    String password;

    String userType;

    String programOrDepartment;

    String yearLevelRank;


    public AccountModel(String id, String name, String email, String password, String userType, String programOrDepartment, String yearLevelRank) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.programOrDepartment = programOrDepartment;
        this.yearLevelRank = yearLevelRank;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getProgramOrDepartment() {
        return programOrDepartment;
    }

    public String getYearLevelRank() {
        return yearLevelRank;
    }

}
