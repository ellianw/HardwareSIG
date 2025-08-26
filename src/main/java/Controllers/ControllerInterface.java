/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ellian
 */
public interface ControllerInterface<T> {
    boolean saveItem(T arg);
    boolean editItem(Integer arg);
    DefaultTableModel getFilledTableModel();    
    DefaultTableModel getFilledTableModel(boolean bool, String args);
    boolean deleteItem(Integer arg);
}
