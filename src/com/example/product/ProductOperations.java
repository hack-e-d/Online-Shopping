package com.example.product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductOperations {
    public Boolean addProduct(Product product) throws SQLException ,ClassNotFoundException;
    public Boolean updateProduct(String vendorId) throws SQLException ,ClassNotFoundException;
    public Boolean deleteProduct(String vendorId) throws SQLException ,ClassNotFoundException;
    public List<Product> getProducts() throws SQLException ,ClassNotFoundException;
    public List<Product> getProducts(String vendorId) throws SQLException ,ClassNotFoundException;
    public List<Product> getProduct(String productId) throws SQLException,ClassNotFoundException;
    public Map<String, String> getProductNames() throws SQLException, ClassNotFoundException;
    public void displayProducts(List<Product> productList);
    public String getProductName(String productId) throws SQLException, ClassNotFoundException;
}
