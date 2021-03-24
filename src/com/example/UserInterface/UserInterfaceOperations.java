package com.example.UserInterface;

import java.sql.SQLException;

public interface UserInterfaceOperations {
    public int getUserType(String userId) throws SQLException,ClassNotFoundException;
    public void performUserOperations(String UserId) throws SQLException,ClassNotFoundException;
    public void customerOperations(String userId) throws SQLException,ClassNotFoundException;
    public void vendorOperations(String userId) throws SQLException,ClassNotFoundException;
    public void addProduct() throws SQLException,ClassNotFoundException;
}
