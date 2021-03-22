package com.example.authentication;

import java.sql.SQLException;

public interface AuthenticationOperations {
    public String login() throws SQLException,ClassNotFoundException;
    public Boolean authenticateLogin(String userId,String password) throws SQLException,ClassNotFoundException;
}
