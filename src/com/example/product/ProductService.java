package com.example.product;

import com.example.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductService {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ProductService productService=new ProductService();
        ProductOperationsImpl productOperations = new ProductOperationsImpl();
//        Product testProduct=productService.initialiseProduct("a345","p08","Oneplus",
//                "Mobile Phone","electronics",50000);
        Map<String,Object> productDetailsFromUser = productService.getUserInput();
        Product testProduct=productService.initialiseProductFromHashMap(productDetailsFromUser);

        List<Product> productList = null;
        try {
            productOperations.addProduct(testProduct);
            productList=productOperations.getProducts();
            productOperations.displayProducts(productList);
            productOperations.deleteProduct();
            productList=productOperations.getProducts();
            productOperations.displayProducts(productList);
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

    public Product initialiseProduct(Object userId, Object productId, Object productName,
                                     Object productDescription, Object productCategory, Object price) {
        Product product=new Product();
        product.setProductName(checkNull(String.valueOf(productName)));
        product.setUserId(checkNull(String.valueOf(userId)));
        product.setProductId(checkNull(String.valueOf(productId)));
        product.setProductDescription(checkNull(String.valueOf(productDescription)));
        product.setProductCategory(checkNull(String.valueOf(productCategory)));
        product.setPrice(Double.parseDouble(checkNull(String.valueOf(price))));
        return product;
    }

    public Product initialiseProductFromHashMap(Map<String,Object> productInputHashMap){
        Product product=initialiseProduct(productInputHashMap.get("user_id"),productInputHashMap.get("product_id"),
                productInputHashMap.get("product_name"),productInputHashMap.get("product_description"),
                productInputHashMap.get("product_category"),productInputHashMap.get("price"));
        return product;
    }

    public Boolean productIdExists(String productId) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.createConnection();
        String query="SELECT * FROM product WHERE `product_id` = ?";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,productId);
        ResultSet resultSet =preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }
    public Map<String,Object> getUserInput() throws SQLException, ClassNotFoundException {
        Map<String,Object> userInputHashMap = new HashMap<String, Object>();
        String userId,productName,productId,productCategory,productDescription;
        double price;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the Vendor ID");
        userId=scanner.next();
        System.out.println("Enter the Product ID");
        productId=scanner.next();
        while(productIdExists(productId)){
            System.out.println("This User ID is taken \nEnter a different User ID");
            productId=scanner.next();
        }
        System.out.println("Enter the Product Name");
        productName=scanner.next();
        System.out.println("Enter the Product Description");
        productDescription=scanner.next();
        System.out.println("Enter the Product Category");
        productCategory=scanner.next();
        System.out.println("Enter the Price");
        price=scanner.nextDouble();
        userInputHashMap.put("user_id", userId);
        userInputHashMap.put("product_id", productId);
        userInputHashMap.put("product_name", productName);
        userInputHashMap.put("product_category", productCategory);
        userInputHashMap.put("product_description", productDescription);
        userInputHashMap.put("price", price);
        return userInputHashMap;
    }

}

