/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.UserDAO;
import Entities.ApplicationContext;
import Entities.User;
import Utils.PasswordUtils;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ellian
 */
public class UserController implements ControllerInterface<User> {
    public static final int SUCCESS = 0;    
    public static final int INVALID_LOGIN = 1;
    public static final int INVALID_PASSWORD = 2;
    
    private UserDAO dao;

    public UserController() {
        dao = new UserDAO();
    }    
    
    public int validateLogin(String login, String rawPassword) {
        String hashedPassword = PasswordUtils.generateHash(rawPassword);
        User user = dao.getByLogin(login);
        
        if (user == null) {
            return INVALID_LOGIN;
        }
        
        if (!(hashedPassword.equals(user.getPasswordHash()))) {
            return INVALID_PASSWORD;
        }
        
        ApplicationContext.getInstance().setActiveUser(user);
        return SUCCESS;
    }
    
    public boolean updateUserPassword(User user,String password){
        String hashedPassword = PasswordUtils.generateHash(password);
        return dao.updateLoginPassword(user.getId(), hashedPassword);
    }

    @Override
    public boolean saveItem(User arg) {
        try {
            if (arg == null) {
            
            } else {
           
            }
        } catch (Exception e) {
            System.out.println("SQL error while inserting or updating clients: "+e);
            e.printStackTrace();
            return false;                 
        }
        return true;
    }

    @Override
    public boolean editItem(Integer arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DefaultTableModel getFilledTableModel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DefaultTableModel getFilledTableModel(boolean bool, String args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteItem(Integer arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
