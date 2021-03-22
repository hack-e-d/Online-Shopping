package com.example.authentication;

import com.example.connection.DBConnection;
import com.example.userRegisteration.RegisterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AuthenticationOperationsImpl implements AuthenticationOperations {
    @Override
    public String login() throws SQLException, ClassNotFoundException {
        String userId=null,password=null;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the User ID : ");
        userId = scanner.next();
        RegisterService registerService=new RegisterService();
        if (!registerService.userIdExists(userId)){
            System.out.println("User ID dose not exits\nRegister if not already a registered user\n(or) \nEnter an existing User ID : ");
            userId = scanner.next();
        }
        System.out.println("Enter the password : ");
        password = scanner.next();
        if(authenticateLogin(userId,password)){
            return userId;
        }else{
            return null;
        }
    }

    @Override
    public Boolean authenticateLogin(String userId, String password) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="SELECT `password` FROM authentication WHERE `user_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,userId);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            return (resultSet.getString(1).equals(password));
        }
        return false;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AuthenticationOperationsImpl authenticationOperations=new AuthenticationOperationsImpl();
        System.out.println(authenticationOperations.login());
    }
}
