/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entities.ApplicationContext;
import Entities.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ellian
 */
public class ClientDAO {
    private Connection conn;

    public ClientDAO() {
        conn = ApplicationContext.getInstance().getConnection();
    }

    public void insert(Client client) throws SQLException {
        String sql = "INSERT INTO client VALUES (default, ?, ?, ?, ?, true)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCPF());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail());
            stmt.executeUpdate();
        }
    }

    public Client findById(int id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("phone"),
                    rs.getString("email")
                );
            }
        }
        return null;
    }
    
    public List<Client> findAllActive() throws SQLException {
        return findAllActive(null);
    }

    public List<Client> findAllActive(String whereClause) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE active = true";
        if (whereClause!=null && !whereClause.isBlank()) {
            sql+=" AND "+whereClause;
        }
        System.out.println(sql);           
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        }
        return clients;
    }

    public void update(Client client) throws SQLException {
        String sql = "UPDATE client SET name = ?, cpf = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCPF());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail());
            stmt.setInt(5, client.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "UPDATE client SET active = false WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}