package com.example.orders;

import com.example.product.ProductOperationsImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderServices {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        List<Purchase> purchaseArrayList =new ArrayList<Purchase>();
        ProductOperationsImpl productOperations=new ProductOperationsImpl();
        try {
            productOperations.displayProducts(productOperations.getProducts());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String productId=null;
        System.out.println("Enter the product id to purchase:");
        Scanner scanner=new Scanner(System.in);
        productId=scanner.next();
        OrderOpterationsImpl orderOpterations=new OrderOpterationsImpl();
        if (orderOpterations.addToCart(productId,10,purchaseArrayList)){
            orderOpterations.viewCart(purchaseArrayList);
        }
    }
}
