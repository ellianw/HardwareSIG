/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entities.ApplicationContext;
import Entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Ellian
 */
public class UserDAO {
    private Connection conn;

    public UserDAO() {
        conn = ApplicationContext.getInstance().getConnection();
    }
    
    public boolean loginExists(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error validating login existence: "+e);
        }
        return false;
    }
    
    public String getHashByLogin(String login) {
        String sql = "SELECT password FROM users WHERE login = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("Error validating login existence: "+e);
        }
        return null;
    }
    
    public User findByLogin(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ? AND active = true";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),rs.getString("login"),rs.getString("name"),rs.getInt("privilege"));
            }
        } catch (Exception e) {
            System.out.println("Error validating login existence: "+e);
        }
        return null;
    }
    public boolean updateLoginPassword(int id, String hashPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println(stmt.toString());
            stmt.setString(1, hashPassword);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error validating login existence: "+e);
            return false;
        }
        return true;        
    }
    
}
