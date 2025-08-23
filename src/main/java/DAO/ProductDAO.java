/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entities.ApplicationContext;
import Entities.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ellian
 */
public class ProductDAO {
    private Connection conn;
    
    public ProductDAO() {
        conn = ApplicationContext.getInstance().getConnection();
    }

    public void insert(Product product) throws SQLException {
        String sql = "INSERT INTO product VALUES (default, ?, ?, ?, ?, true)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getValue());
            stmt.setInt(4, product.getStock());
            stmt.executeUpdate();
            System.out.println(stmt.toString());
        }
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("value"),
                    rs.getInt("stock")
                );
            }
        }
        return null;
    }
    
    public ArrayList<Product> findById(ArrayList<Integer> ids) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE id IN (";
        for (int id : ids) {
            sql+=id+",";
        }
        sql = sql.substring(0,sql.length()-1)+')';
        System.out.println(sql);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("value"),
                    rs.getInt("stock"))
                );
            }
        }
        return list;
    }    
    
    public List<Product> findAllActive() throws SQLException {
        return findAllActive(null);
    }

    public List<Product> findAllActive(String whereClause) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE active = true";
        if (whereClause!=null && !whereClause.isBlank()) {
            sql+=" AND "+whereClause;
        }
        System.out.println(sql);        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("value"),
                    rs.getInt("stock")
                ));
            }
        }
        return products;
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, description = ?, value = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getValue());
            stmt.setInt(4, product.getStock());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

