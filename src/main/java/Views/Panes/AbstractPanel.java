/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views.Panes;

import Controllers.ControllerInterface;
import Entities.ApplicationContext;
import Utils.ViewUtils;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author Ellian
 */
abstract class AbstractPanel extends javax.swing.JPanel {
    protected ApplicationContext context;
    protected ControllerInterface controller;
    
    public AbstractPanel() {
        context = ApplicationContext.getInstance();
    }       
    
    protected void openEditor(JTable table) {
        Integer id = ViewUtils.getSelectedListItemId(table);
        if (id == null) {
            JOptionPane.showMessageDialog(null, "Selecione um item!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean status = controller.editItem(id);
        if (!status) {
            JOptionPane.showMessageDialog(null, "Erro desconhecido ao excluir item!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadTable();
    }
    
    protected void deleteItemFromTable(JTable table) {
        Integer id = ViewUtils.getSelectedListItemId(table);
        if (id == null) {
            JOptionPane.showMessageDialog(null, "Selecione um item!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ViewUtils.excludePane()) {
            boolean status = controller.deleteItem(id);
            if (!status) {
                JOptionPane.showMessageDialog(null, "Erro desconhecido ao excluir item!", "Erro", JOptionPane.ERROR_MESSAGE);
                  return;
            }
            table.setModel(controller.getFilledTableModel());
            JOptionPane.showMessageDialog(null, "Item deletado!", "Sucesso", JOptionPane.PLAIN_MESSAGE);        
        }    
    }
    
    protected void setSearchButtonMapping(JButton button) {
        KeyStroke shortcut = KeyStroke.getKeyStroke("F5");
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = button.getActionMap();

        inputMap.put(shortcut, "clickButton");
        actionMap.put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick(); // simula clique
            }
        });        
    }
    
    abstract void loadTable();
}
