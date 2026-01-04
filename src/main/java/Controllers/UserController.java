/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.UserDAO;
import Entities.ApplicationContext;
import Entities.User;
import Utils.PasswordUtils;
import Views.Editors.UserEditor;
import java.awt.Frame;
import java.util.List;
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
        
        if (!(hashedPassword.equals(user.getPassword()))) {
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
    public boolean saveItem(User user) {        
        String passwordHash = null;
        try {
            if (!user.getPassword().isBlank()) {
                passwordHash = PasswordUtils.generateHash(user.getPassword());
                user.setPassword(passwordHash);
            }                        
            if (user.getId() == null) {
                dao.saveUser(user);
            } else {
                dao.updateUser(user);
                if (passwordHash != null) {
                    dao.updateLoginPassword(user.getId(), passwordHash);
                }
            }
        } catch (Exception e) {
            System.out.println("SQL error while inserting or updating user: "+e);
            e.printStackTrace();
            return false;                 
        }
        return true;
    }

    @Override
    public boolean editItem(Integer id) {
        try {
            User user = dao.getById(id);
            UserEditor editor = new UserEditor(null, true);
            editor.setLocationRelativeTo(null);
            editor.fillFields(user);
            editor.setVisible(true);  
        } catch (Exception e) {
            System.out.println("SQL error while editing client: "+e);
            return false;
        }
        return true;
    }

    @Override
    public DefaultTableModel getFilledTableModel() {
        return getFilledTableModel(false, null);
    }

    @Override
    public DefaultTableModel getFilledTableModel(boolean modified, String expression) {
        String[] colunas = { "ID", "Nome", "login", "Privilégio"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas,0);
        List<User> userList = null;
        
        try {
            if (modified) {
                userList = dao.findAllActive(expression);
            } else {
                userList = dao.findAllActive();
            }            
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuários: "+e);
            e.printStackTrace();
        }

        if (userList == null ) return tableModel;
        
        for (User u : userList) {
            Object[] linha = {
                u.getId(),
                u.getName(),
                u.getLogin(),
                u.getPrivilege() > 0 ? "Administrador" : "Padrão"
            };
            tableModel.addRow(linha);
        }
        
        return tableModel;        
    }

    @Override
    public boolean deleteItem(Integer id) {
        try {
            dao.delete(id);
        } catch (Exception e) {
            System.out.println("SQL error while deleting client: "+e);
            return false;
        }
        return true;
    }
}
