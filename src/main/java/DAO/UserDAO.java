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
        String sql = "insert into users values (default,?,?,null,?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println(stmt.toString());
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setInt(3, user.getPrivilege());
            stmt.execute();
        } catch (Exception e ) {
            System.out.println("Error validating login existence: "+e);
            return false;            
        }
        
        return true;
    } 
    
    public boolean updateUser(User user) {
        String sql = "update users set login = ?, name = ?, privilege = ? where login = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println(stmt.toString());
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setInt(3, user.getPrivilege());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        } catch (Exception e ) {
            System.out.println("Error validating login existence: "+e);
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
            user = createUser(rs);
        } catch (Exception e) {
            System.out.println("Error getting user by login: "+e);
        }
        
        return user;
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
