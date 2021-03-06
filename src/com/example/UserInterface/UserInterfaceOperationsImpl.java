package com.example.UserInterface;

import com.example.connection.DBConnection;
import com.example.orders.OrderOperationsImpl;
import com.example.orders.Purchase;
import com.example.product.Product;
import com.example.product.ProductOperationsImpl;
import com.example.product.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterfaceOperationsImpl implements UserInterfaceOperations {
    @Override
    public int getUserType(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.createConnection();
        String query = "SELECT `user_type` FROM user WHERE `user_id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    @Override
    public void performUserOperations(String userId) throws SQLException, ClassNotFoundException {
        switch (getUserType(userId)) {
            case 0:
                customerOperations(userId);
            case 1:
                vendorOperations(userId);
        }
    }

    @Override
    public void customerOperations(String userId) throws SQLException, ClassNotFoundException {
        List<Purchase> purchaseArrayList = new ArrayList<Purchase>();
        OrderOperationsImpl orderOperations = new OrderOperationsImpl();
        while (true) {
            int option = -1;
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n### Customer Options ###\n\n 1 - Make purchase\n" +
                    "2 - View Cart\n3 - Checkout\n 4 - Clear cart \n5 - Exit App\nEnter the option:");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    orderOperations.makePurchase(purchaseArrayList);
                    break;
                case 2:
                    orderOperations.viewCart(purchaseArrayList);
                    break;
                case 3:
                    orderOperations.placeOrder(purchaseArrayList, userId);
                    orderOperations.clearCart(purchaseArrayList);
                    break;
                case 4:
                    orderOperations.clearCart(purchaseArrayList);
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    @Override
    public void vendorOperations(String userId) throws SQLException, ClassNotFoundException {
        ProductOperationsImpl productOperations = new ProductOperationsImpl();
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        while (option !=5) {
            option = -1;
            System.out.println("\n### Vendor Options ###\n\n 1 - Add Product \n 2 - Delete Products" +
                    "3 - Update Product \n4 - View Products \n5 - Exit App\nEnter the option:");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    productOperations.deleteProduct(userId);
                    break;
                case 3:
                    productOperations.updateProduct(userId);
                    break;
                case 4:
                    productOperations.displayProducts(productOperations.getProducts(userId));
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    @Override
    public void addProduct() throws SQLException, ClassNotFoundException {
        ProductService productService=new ProductService();
        ProductOperationsImpl productOperations = new ProductOperationsImpl();
        Map<String,Object> productDetailsFromUser = productService.getUserInput();
        Product testProduct=productService.initialiseProductFromHashMap(productDetailsFromUser);

        List<Product> productList = null;
        try {
            productOperations.addProduct(testProduct);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
