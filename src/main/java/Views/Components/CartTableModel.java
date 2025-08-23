/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views.Components;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ellian
 */
public class CartTableModel extends DefaultTableModel {

    public CartTableModel() {
    }

    public CartTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 4) {
            return true;
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        fireTableCellUpdated(row, column);
    }
}
