package com.example.orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderOperations {
    public boolean placeOrder(List<Purchase> purchaseArrayList,String userId) throws SQLException, ClassNotFoundException;
    public List<Purchase> viewOders(String userId) throws SQLException,ClassNotFoundException;
    public boolean addToCart(String productId,int count,List<Purchase> purchasesArrayList);
    public void viewCart(List<Purchase> purchaseList) throws SQLException, ClassNotFoundException;
    public boolean makePurchase(List<Purchase> purchaseList) throws SQLException,ClassNotFoundException;
    public void clearCart(List<Purchase> purchasesList);
}

