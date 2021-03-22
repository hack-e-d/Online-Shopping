package com.example.userRegisteration;

import java.sql.SQLException;
import java.util.Map;

public interface Registeration {
    public Map<String,String> signUp(User user) throws SQLException, ClassNotFoundException;
    public Map<String,String> addUser(User user) throws SQLException, ClassNotFoundException;
    public Boolean addAuthentication(User user) throws SQLException,ClassNotFoundException;

}
