/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

/**
 *
 * @author Ellian
 */
public class ViewUtils {
    public static boolean missingField(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JFormattedTextField ftf) {
                if (ftf.getText().trim().isEmpty() || ftf.getValue() == null) {
                    return true;
                }
            } else if (comp instanceof JTextField tf) {
                if (tf.getText().trim().isEmpty()) {
                    return true;
                }
            } else if (comp instanceof Container nested) {
                if (missingField(nested)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean clearFields(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JFormattedTextField ftf) {
                ftf.setText("");
            } else if (comp instanceof JTextField tf) {
                tf.setText("");
            } else if (comp instanceof Container nested) {
                clearFields(nested);
            }
        }
        return false;
    }
    
    public static Integer getSelectedListItemId(JTable table) {
        int row = table.getSelectedRow();
        if (row <0) return null;
        TableModel tableModel = table.getModel();
        Integer id = (Integer) tableModel.getValueAt(row, 0);
        return id;
    }

    public static boolean excludePane() {
        int resposta = JOptionPane.showConfirmDialog(
            null,
            "Tem certeza que deseja excluir este registro?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (resposta==JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
