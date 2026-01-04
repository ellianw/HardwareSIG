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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ellian
 */
public class UserDAO {
    private Connection conn;

    public UserDAO() {
        conn = ApplicationContext.getInstance().getConnection();
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
    
    public boolean saveUser(User user) {
        String sql = "insert into users values (default,?,?,?,?,true)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println(stmt.toString());
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getPrivilege());
            stmt.execute();
        } catch (Exception e ) {
            System.out.println("Error saving new user: "+e);
            return false;            
        }
        
        return true;
    } 
    
    public boolean updateUser(User user) {
        String sql = "update users set login = ?, name = ?, privilege = ? where id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setInt(3, user.getPrivilege());
            stmt.setInt(4, user.getId());
            System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (Exception e ) {
            System.out.println("Error updating user: "+e);
            return false;            
        }
        
        return true;
    }
    
    public User getByLogin(String login) {
        String sql = "select * from users where login = ? and active = true";
        
        User user = null;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,login);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user = createUser(rs);
        } catch (Exception e) {
            System.out.println("Error getting user by login: "+e);
        }
        
        return user;
    }
    
    public User getById(int id) throws SQLException {
        String sql = "select * from users where id = ?";
        
        User user = null;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user = createUser(rs);
        } 
        
        return user;
    }
    
    public List<User> findAllActive() throws SQLException {
        return findAllActive(null);
    }

    public List<User> findAllActive(String whereClause) throws SQLException {
        List<User> products = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE active = true";
        
        if (whereClause!=null && !whereClause.isBlank()) {
            sql+=" AND "+whereClause;
        }
        
        System.out.println(sql);        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(createUser(rs));
            }
        }
        return products;
    }    
    
    public void delete(int id) throws SQLException{
        String sql = "update users set active = false WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    private User createUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("login"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getInt("privilege")               
        );
    }
}
