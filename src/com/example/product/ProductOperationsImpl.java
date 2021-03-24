package com.example.product;

import com.example.connection.DBConnection;

import java.awt.dnd.DnDConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductOperationsImpl implements ProductOperations {
    @Override
    public Boolean addProduct(Product product) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="INSERT INTO product(`user_id`,`product_id`,`product_name`" +
                ",`product_discription`,`product_catagory`,`price`,`created_by`) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,product.getUserId());
        preparedStatement.setString(2,product.getProductId());
        preparedStatement.setString(3,product.getProductName());
        preparedStatement.setString(4,product.getProductDescription());
        preparedStatement.setString(5,product.getProductCategory());
        preparedStatement.setDouble(6,product.getPrice());
        preparedStatement.setString(7,product.getUserId());
        int i=preparedStatement.executeUpdate();
        connection.close();
        if(i>0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateProduct(String vendorId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        Scanner scanner=new Scanner(System.in);
        String productId,productDescription,productName;
        double price;
//        System.out.println("\n Products \n");
        displayProducts(getProducts(vendorId));
        System.out.println("Enter the product ID to modify");
        productId = scanner.nextLine();
        System.out.println("Enter the product Name:");
        productName = scanner.nextLine();
        System.out.println("Enter the product Description:");
        productDescription = scanner.nextLine();
        System.out.println("Enter the product price:");
        price = scanner.nextDouble();
        String query="UPDATE product SET `product_name` = ?,`product_discription` = ?," +
                "`price` = ?,`modified_by` = ? WHERE `product_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,productName);
        preparedStatement.setString(2,productDescription);
        preparedStatement.setDouble(3,price);
        preparedStatement.setString(4,vendorId);
        preparedStatement.setString(5,productId);
        int i=preparedStatement.executeUpdate();
        connection.close();
        if(i>0) {
            System.out.println("Product updated!");
            displayProducts(getProduct(productId));
            return true;
        }else {
            System.out.println("Product Update failed");
            return false;
        }
    }

    @Override
    public Boolean deleteProduct(String vendorId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String productId=null;
        Scanner scanner=new Scanner(System.in);
        displayProducts(getProducts(vendorId));
        System.out.println("Enter the product id to Delete : ");
        productId=scanner.next();
        String query="DELETE FROM product WHERE `product_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,productId);
        int i=preparedStatement.executeUpdate();
        connection.close();
        if(i>0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Product> getProducts() throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.createConnection();
        String query="SELECT `user_id`,`product_id`,`product_name`,`product_discription`,`product_catagory`,`price` " +
                "FROM product";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Product> productArrayList = new ArrayList<Product>();
        int productArrayListIndex=0;
        while(resultSet.next()){
            productArrayList.add(new Product());
            productArrayList.get(productArrayListIndex).setUserId(resultSet.getString(1));
            productArrayList.get(productArrayListIndex).setProductId(resultSet.getString(2));
            productArrayList.get(productArrayListIndex).setProductName(resultSet.getString(3));
            productArrayList.get(productArrayListIndex).setProductDescription(resultSet.getString(4));
            productArrayList.get(productArrayListIndex).setProductCategory(resultSet.getString(5));
            productArrayList.get(productArrayListIndex).setPrice(resultSet.getDouble(6));
            productArrayListIndex++;
        }
        connection.close();
        return productArrayList;
    }

    @Override
    public List<Product> getProducts(String vendorId) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.createConnection();
        String query="SELECT `user_id`,`product_id`,`product_name`,`product_discription`,`product_catagory`,`price` " +
                "FROM product WHERE `user_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,vendorId);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Product> productArrayList = new ArrayList<Product>();
        int productArrayListIndex=0;
        while(resultSet.next()){
            productArrayList.add(new Product());
            productArrayList.get(productArrayListIndex).setUserId(resultSet.getString(1));
            productArrayList.get(productArrayListIndex).setProductId(resultSet.getString(2));
            productArrayList.get(productArrayListIndex).setProductName(resultSet.getString(3));
            productArrayList.get(productArrayListIndex).setProductDescription(resultSet.getString(4));
            productArrayList.get(productArrayListIndex).setProductCategory(resultSet.getString(5));
            productArrayList.get(productArrayListIndex).setPrice(resultSet.getDouble(6));
            productArrayListIndex++;
        }
        connection.close();
        return productArrayList;
    }

    @Override
    public List<Product> getProduct(String productId) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.createConnection();
        String query="SELECT `user_id`,`product_id`,`product_name`,`product_discription`,`product_catagory`,`price` " +
                "FROM product WHERE `product_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,productId);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Product> productArrayList = new ArrayList<Product>();
        int productArrayListIndex=0;
        while(resultSet.next()){
            productArrayList.add(new Product());
            productArrayList.get(productArrayListIndex).setUserId(resultSet.getString(1));
            productArrayList.get(productArrayListIndex).setProductId(resultSet.getString(2));
            productArrayList.get(productArrayListIndex).setProductName(resultSet.getString(3));
            productArrayList.get(productArrayListIndex).setProductDescription(resultSet.getString(4));
            productArrayList.get(productArrayListIndex).setProductCategory(resultSet.getString(5));
            productArrayList.get(productArrayListIndex).setPrice(resultSet.getDouble(6));
            productArrayListIndex++;
        }
        connection.close();
        return productArrayList;
    }

    @Override
    public Map<String, String> getProductNames() throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.createConnection();
        String query="SELECT `product_id`,`product_name` FROM product";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
        Map<String ,String> productNameMap = new HashMap<String, String>();
        while(resultSet.next()){
            productNameMap.put(resultSet.getString(1),resultSet.getString(2));
        }
        connection.close();
        return productNameMap;
    }

    @Override
    public void displayProducts(List<Product> productList) {

        System.out.println("Product Id\tProduct Name\tProduct Description\tProduct Category\tProduct Price");
        for(Product product:productList){
            System.out.println("\t"+product.getProductId()+"\t\t"+product.getProductName()+"\t\t\t\t"+product.getProductDescription()+
                    "\t\t\t"+product.getProductCategory()+"\t\t\t\t"+product.getPrice());
        }
    }

    @Override
    public String getProductName(String productId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="SELECT `product_name` FROM product WHERE `product_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,productId);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
