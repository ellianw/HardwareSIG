/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.UserDAO;
import Entities.ApplicationContext;
import Entities.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ellian
 */
public class SecurityController {
    private UserDAO dao;

    public SecurityController() {
        dao = new UserDAO();
    }    
    
    public static String generateHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean validLogin(String login) {
        return dao.loginExists(login);
    }
    
    public boolean validatePassword(String login, String password) {
        String dbPassoword = dao.getHashByLogin(login);
        if (dbPassoword.equals(password)) {
            return true;
        }
        return false;
    }
    
    public boolean getUser(String login, String password) {
        User user = dao.findByLogin(login, password);
        if (user == null) return false;
        ApplicationContext.getInstance().setActiveUser(user);
        return true;
    }
    
    public boolean updateUserPassword(User user,String password){
        String hashedPassword = generateHash(password);
        return dao.updateLoginPassword(user.getId(), hashedPassword);
    }
}
