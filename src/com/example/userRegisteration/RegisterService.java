package com.example.userRegisteration;

import com.example.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RegisterService {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        RegisterService registerService=new RegisterService();
//        User testUser =registerService.initialiseUser("vicar","p","v",
//                "q123",0,"1234567890","asd@gmail.com","asd123");
        Map<String,Object> userInputHashMap = registerService.getUserInput();
        User testUser =registerService.initialiseUserFromHashMap(userInputHashMap);
        RegistrationImpl registration=new RegistrationImpl();
        try {
            System.out.println( registration.signUp(testUser));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String checkNull(String s){
        if(s!=null){
            return s;
        }else {
            return "-";
        }
    }
    public User initialiseUser(Object firstName, Object middleName, Object lastName,
                               Object userId, Object userType, Object phoneNumber, Object mailId, Object password){
        User user = new User();
        user.setFirstName(checkNull(String.valueOf(firstName)));
        user.setMiddleName(checkNull(String.valueOf(middleName)));
        user.setLastName(checkNull(String.valueOf(lastName)));
        user.setUserId(checkNull(String.valueOf(userId)));
        user.setUserType(Integer.parseInt(checkNull(String.valueOf(userType))));
        user.setMailId(checkNull(String.valueOf(mailId)));
        user.setPhoneNumber(checkNull(String.valueOf(phoneNumber)));
        user.setPassword(checkNull(String.valueOf(password)));
        return user;
    }

    public User initialiseUserFromHashMap(Map<String,Object> userDetailsHashMap){
        User user=this.initialiseUser(userDetailsHashMap.get("firstName"),userDetailsHashMap.get("middleName"),
                userDetailsHashMap.get("lastName"),userDetailsHashMap.get("userId"),userDetailsHashMap.get("userType"),
                userDetailsHashMap.get("phoneNumber"),userDetailsHashMap.get("mailId"),userDetailsHashMap.get("password"));
        return user;
    }

    public Boolean userIdExists(String userId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="SELECT * FROM user WHERE `user_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,userId);
        ResultSet resultSet =preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
    return false;
    }

    public Map<String,Object> getUserInput() throws SQLException, ClassNotFoundException {
        Map<String,Object> userInputHashMap = new HashMap<String, Object>();
        String userId,firstName,middleName,lastName,mailId,phoneNumber,password;
        int userType;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the User ID");
        userId=scanner.next();
        while(userIdExists(userId)){
            System.out.println("This User ID is taken \nEnter a different User ID");
            userId=scanner.next();
        }
        System.out.println("Enter the First Name");
        firstName=scanner.next();
        System.out.println("Enter the Middle Name");
        middleName=scanner.next();
        System.out.println("Enter the Last tName");
        lastName=scanner.next();
        System.out.println("Enter the Mail ID");
        mailId=scanner.next();
        System.out.println("Enter the Phone Number");
        phoneNumber=scanner.next();
        System.out.println("Enter the User Type\n1- vendor\n0- customer");
        userType=scanner.nextInt();
        System.out.println("Enter the Password");
        password=scanner.next();
        userInputHashMap.put("userId", userId);
        userInputHashMap.put("firstName", firstName);
        userInputHashMap.put("middleName", middleName);
        userInputHashMap.put("lastName", lastName);
        userInputHashMap.put("userType", userType);
        userInputHashMap.put("mailId", mailId);
        userInputHashMap.put("phoneNumber", phoneNumber);
        userInputHashMap.put("password", password);
        return userInputHashMap;
    }
}
