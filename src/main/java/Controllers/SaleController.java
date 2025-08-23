/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.ClientDAO;
import DAO.SaleDAO;
import Entities.Sale;
import Views.Panes.JpnSales;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ellian
 */
public class SaleController {
    private SaleDAO dao = null;
    private ClientDAO clientDAO = null;
    private JpnSales panel = null;

    public SaleController() {
        dao = new SaleDAO();
        clientDAO = new ClientDAO();
    }
    
    public void setPanel(JpnSales panel) {
        this.panel = panel;
    }
    
    public DefaultTableModel getFilledTableModel() {
        String[] colunas = { "ID", "Cliente", "Valor", "Data"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas,0);
        List<Sale> saleList = null;
        
        try {
            saleList = dao.getAllSalesSimplified();
        } catch (Exception e) {
            System.out.println("Erro ao buscar vendas: "+e);
        }

        if (saleList == null ) return tableModel;
        
        for (Sale s : saleList) {
            try {
                Object[] linha = {
                    s.getId(),
                    clientDAO.findById(s.getClientId()).getName(),
                    dao.getSaleValue(s.getId()),
                    s.getDate(),
                };
                tableModel.addRow(linha);
            } catch (Exception e) {
                System.out.println("Error building sale list: "+e);
            }
        }
        
        return tableModel;
    }
    
    public boolean saveSale(Integer clientId, Integer sellerId, Map productMap, String date) {
        Sale sale = new Sale(null, clientId, sellerId, productMap, date, calculateSaleValue(productMap));
        try {
            if (sale.getId() == null) {
                dao.insert(sale);
            } else {
//                dao.update(sale);
            }
        } catch (Exception e) {
            System.out.println("SQL error while inserting or updating sale: "+e);
            return false;
        }
        
        return true;
    }

    public boolean deleteSale(Integer id) {

        return true;
    }
    
    public boolean editSale(Integer id) {

        return true;
    }
    
    private double calculateSaleValue(Map sale) {
        double output = 0;
        for (Object v : sale.values()) {
            double[] arr = (double[]) v;
            output += arr[1];
        }
        return output;
    }
}
