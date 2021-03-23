package com.example.orders;

import com.example.connection.DBConnection;
import com.example.product.ProductOperationsImpl;

import java.security.AccessControlException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderOperationsImpl implements OrderOperations {
    @Override
    public boolean placeOrder(List<Purchase> purchaseArrayList, String userId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="INSERT INTO purchase (`product_id`,`product_count`,`created_by`) VALUES (?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        int i=0;
        for(Purchase p:purchaseArrayList){
            preparedStatement.setString(1,p.getProductId());
            preparedStatement.setInt(2,p.getProductCount());
            preparedStatement.setString(3,userId);
            i=preparedStatement.executeUpdate();
            if(i>0){
                i=0;
                continue;
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Purchase> viewOders(String userId) throws SQLException ,ClassNotFoundException{
        Connection connection=DBConnection.createConnection();
        String query="SELECT `product_id`,`product_count` FROM purchase WHERE `created_by` = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(query);
        preparedStatement.setString(1,userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Purchase> purchaseList=new ArrayList<Purchase>();
        int i=0;
        while (resultSet.next()){
            purchaseList.add(new Purchase());
            purchaseList.get(i).setProductId(resultSet.getString(1));
            purchaseList.get(i).setProductCount(resultSet.getInt(2));
            i++;
        }
        return purchaseList;
    }

    @Override
    public boolean addToCart(String productId, int count, List<Purchase> purchasesArrayList) {
        purchasesArrayList.add(new Purchase());
        purchasesArrayList.get(purchasesArrayList.size()-1).setProductId(productId);
        purchasesArrayList.get(purchasesArrayList.size()-1).setProductCount(count);
        return true;
    }

    @Override
    public void viewCart(List<Purchase> purchaseList) throws SQLException, ClassNotFoundException {
        System.out.println("Product Id\tProduct Name\t\tCount");
        ProductOperationsImpl productOperations=new ProductOperationsImpl();
        for (Purchase p:purchaseList){
            System.out.println("\t"+p.getProductId()+"\t\t"+productOperations.getProductName(p.getProductId())+"\t\t\t\t"+p.getProductCount());
        }
    }

    @Override
    public boolean makePurchase(List<Purchase> purchaseList) throws SQLException, ClassNotFoundException {
        ProductOperationsImpl productOperations=new ProductOperationsImpl();
        try {
            productOperations.displayProducts(productOperations.getProducts());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String productId=null;
        int productCount = 0;
        System.out.println("Enter the product id to purchase:");
        Scanner scanner=new Scanner(System.in);
        productId=scanner.next();
        System.out.println("Enter the Product Count:");
        productCount = scanner.nextInt();
        OrderOperationsImpl orderOperations=new OrderOperationsImpl();
        if (orderOperations.addToCart(productId,productCount,purchaseList)){
            orderOperations.viewCart(purchaseList);
            return true;
        }
        return false;
    }

    @Override
    public void clearCart(List<Purchase> purchasesList) {
        purchasesList.clear();
    }
}
