/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.ClientDAO;
import Entities.Client;
import Views.Editors.ClientEditor;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ellian
 */
public class ClientController implements ControllerInterface<Client>{
    private ClientDAO dao = null;
    
    public ClientController() {
        dao = new ClientDAO();
    }
    
    public DefaultTableModel getFilledTableModel() {
        return getFilledTableModel(false,null);
    }
    
    public DefaultTableModel getFilledTableModel(boolean conditioned,String expression) {
        String[] colunas = { "ID", "Nome", "Email", "Telefone","CPF" };
        DefaultTableModel tableModel = new DefaultTableModel(colunas,0);
        List<Client> clientList = null;
        
        try {
            if (conditioned) {
                clientList = dao.findAllActive(expression);
            } else {
                clientList = dao.findAllActive();
            }            
        } catch (Exception e) {
            System.out.println("SQL error searching clients: "+e);
        }

        if (clientList == null ) return tableModel;
        
        for (Client s : clientList) {
            Object[] linha = {
                s.getId(),
                s.getName(),
                s.getEmail(),
                s.getPhone(),
                s.getCPF()
            };
            tableModel.addRow(linha);
        }
        
        return tableModel;
    }
    
    @Override
    public boolean saveItem(Client client) {
        try {
            if (client.getId() == null) {
                dao.insert(client);
            } else {
                dao.update(client);
            }
        } catch (Exception e) {
            System.out.println("SQL error while inserting or updating clients: "+e);
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public boolean deleteClient(Integer id) {
        try {
            dao.delete(id);
        } catch (Exception e) {
            System.out.println("SQL error while deleting client: "+e);
            return false;
        }
        return true;
    }
    
    public boolean editClient(Integer id) {
        try {
            Client client = dao.findById(id);
            ClientEditor editor = new ClientEditor(null,true);
            editor.setLocationRelativeTo(null);
            editor.fillFields(client);
            editor.setVisible(true);
        } catch (Exception e) {
            System.out.println("SQL error while editing client: "+e);
            return false;
        }
        return true;
    }
    
    public DefaultComboBoxModel getFilledComboBoxModel() {
        List<Client> clients = null;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(new Client(null, "Selecione um cliente", null, null, null));
        try {
            clients = dao.findAllActive(null);
        } catch (Exception e) {
            System.out.println("SQL error searching clients: "+e);
        }
        if (clients == null ) return model;
        for (Client c : clients) {
            model.addElement(c);
        }
        return model;
    }
}
