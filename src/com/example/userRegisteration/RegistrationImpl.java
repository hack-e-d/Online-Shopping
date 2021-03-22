package com.example.userRegisteration;

import com.example.connection.DBConnection;
import com.mysql.cj.xdevapi.DbDoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationImpl implements Registeration {
    @Override
    public Map<String, String> signUp(User user) throws SQLException, ClassNotFoundException {
//        Implement validation
        return addUser(user);
    }

    @Override
    public Map<String,String> addUser(User user) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO user" +
                "(`user_id`,`first_name`,`middle_name`,`last_name`,`mail_id`," +
                "`phone_number`,`user_type`,`created_by`) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection= DBConnection.createConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,user.getUserId());
        preparedStatement.setString(2,user.getFirstName());
        preparedStatement.setString(3,user.getMiddleName());
        preparedStatement.setString(4,user.getLastName());
        preparedStatement.setString(5,user.getMailId());
        preparedStatement.setString(6,user.getPhoneNumber());
        preparedStatement.setInt(7,user.getUserType());
        preparedStatement.setString(8,user.getUserId());
        boolean flag = !preparedStatement.execute();
        connection.close();
        boolean authenticationAddFlag=false;
        if(flag) {
            authenticationAddFlag = !addAuthentication(user);
        }
        Map<String,String > statusMap = new HashMap<String, String>();
        if(flag && authenticationAddFlag){
            statusMap.put("Status" , "Success");
            statusMap.put("Status Code" , "S101");
            statusMap.put("Status Message" , "The user has been created.");
            return statusMap;
        }
        statusMap.put("Status" , "Failure");
        statusMap.put("Status Code" , "E101");
        statusMap.put("Status Message" , "The user has not been created.");
        return statusMap;
    }

    @Override
    public Boolean addAuthentication(User user) throws SQLException,ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="INSERT INTO authentication (`user_id`,`password`,`created_by`) VALUES (?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,user.getUserId());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setString(3,user.getUserId());
        boolean flag=preparedStatement.execute();
        connection.close();
        return flag;
    }


}
