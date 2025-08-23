/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.ClientDAO;
import DAO.ProductDAO;
import DAO.SaleDAO;
import Entities.ApplicationContext;
import Entities.Client;
import Entities.Product;
import Entities.Sale;
import java.awt.Desktop;
import java.io.File;
import org.openpdf.pdf.ITextRenderer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ellian
 */
public class ReportController {
    private ProductDAO productDAO;
    private ClientDAO clientDAO;
    private final String head = """
            <head>
                  <style>
                      body {font-family: Arial, Helvetica, sans-serif;}
                      #content_table {margin:auto;}
                      table {border-collapse: collapse;width: 100%;}
                      td,th {text-align: center;}
                      #title,p {text-align: center;}
                      p {font-size: large;}
                      footer {
                          position: fixed;
                          bottom: 0;
                          right: 1vw;
                      }
                  </style>
              </head>                          
                          """;

    public ReportController() {
        productDAO = new ProductDAO();
        clientDAO = new ClientDAO();
    }
    
    public void buildProductsReport() {
        ArrayList<Product> products;
        try {
            products = (ArrayList<Product>) productDAO.findAllActive();
        } catch (Exception e) {
            System.out.println("Error getting products: "+e);
            return;
        }
        String start = head+"""
            <body>
                <h1 id="title">Relatório Geral de Produtos</h1>
                <div id="maindiv">
                    <table id="content_table">
                        <tr>
                            <th>ID</th>
                            <th>Produto</th>
                            <th>Valor</th>
                            <th>Estoque</th>
                        </tr>
            """;
        StringBuilder builder = new StringBuilder(start);
        for (Product p : products) {
            String name = p.getName();
            int id = p.getId();
            Double value = p.getValue();
            int stock = p.getStock();
            String tableRow = """
                              <tr>
                                  <td>%d</td>
                                  <td>%s</td>
                                  <td>R$ %.2f</td>
                                  <td>%d</td>
                              </tr>
                              """.formatted(id,name,value,stock);
            builder.append(tableRow);
        }
        String end = """
                            </table>
                         </div>
                         <footer>Usuário ativo: %s</footer>
                     </body>
                     </html>                     
                     """.formatted(ApplicationContext.getInstance().getActiveUser().getName());
        builder.append(end);
        
        
        buildDefaultReport(builder.toString());
        
        return;
    }
    
    public void buildClientsReport() {
        ArrayList<Client> clients;
        try {
            clients = (ArrayList<Client>) clientDAO.findAllActive();
        } catch (Exception e) {
            System.out.println("Error getting products: "+e);
            return;
        }
        String start = head+"""
            <body>
                <h1 id="title">Relatório Geral de Clientes</h1>
                <div id="maindiv">
                    <table id="content_table">
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Cpf</th>
                            <th>Telefone</th>
                        </tr>
            """;
        StringBuilder builder = new StringBuilder(start);
        for (Client c : clients) {
            String name = c.getName();
            int id = c.getId();
            String cpf = c.getCPF();
            String phone = c.getPhone();
            String tableRow = """
                              <tr>
                                  <td>%d</td>
                                  <td>%s</td>
                                  <td>%s</td>
                                  <td>%s</td>
                              </tr>
                              """.formatted(id,name,cpf,phone);
            builder.append(tableRow);
        }
        String end = """
                            </table>
                         </div>
                         <footer>Usuário ativo: %s</footer>
                     </body>
                     </html>                     
                     """.formatted(ApplicationContext.getInstance().getActiveUser().getName());
        builder.append(end);
        
        
        buildDefaultReport(builder.toString());
        
        return;
    }
    
    
    public void buildSalesReport() {
        ArrayList<Sale> sales;
        try {
            sales = (ArrayList<Sale>) new SaleDAO().getAllSalesSimplified();
        } catch (Exception e) {
            System.out.println("Error getting products: "+e);
            return;
        }
        String start = head+"""
            <body>
                <h1 id="title">Relatório Geral de Vendas</h1>
                <div id="maindiv">
                    <table id="content_table">
                        <tr>
                            <th>ID</th>
                            <th>Cliente</th>
                            <th>Valor</th>
                            <th>Data</th>
                        </tr>
            """;
        StringBuilder builder = new StringBuilder(start);
        for (Sale s : sales) {
            String client = "";
            try {
                client = clientDAO.findById(s.getClientId()).getName();
            } catch (Exception e) {
                System.out.println("Error getting client name: "+e);
                continue;
            }
            int id = s.getId();
            Double value = s.getValue();
            String date = s.getDate();
            String tableRow = """
                              <tr>
                                  <td>%d</td>
                                  <td>%s</td>
                                  <td>R$ %.2f</td>
                                  <td>%s</td>
                              </tr>
                              """.formatted(id,client,value,date);
            builder.append(tableRow);
        }
        String end = """
                            </table>
                         </div>
                         <footer>Usuário ativo: %s</footer>
                     </body>
                     </html>                     
                     """.formatted(ApplicationContext.getInstance().getActiveUser().getName());
        builder.append(end);
        
        
        buildDefaultReport(builder.toString());
        
        return;
    }    
    
    
    private void buildDefaultReport(String html) {
        try (FileOutputStream outputStream = new FileOutputStream("report.pdf")) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch(Exception e) {
            System.out.println("Error building report: "+e);
            return;
        }

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(new File("report.pdf"));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error opening generated file: "+ex);
            }
        }    
    };
}
