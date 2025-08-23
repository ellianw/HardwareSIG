/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.util.Map;

/**
 *
 * @author Ellian
 */
public class Sale {
    private Integer id;
    private Integer clientId;
    private String date;
    private Map mapProducts;
    private Integer sellerId;
    private Double value;

    public Sale(Integer id, Integer clientId, Integer sellerId, Map arrProducts, String date, Double value) {
        this.id = id;
        this.clientId = clientId;
        this.mapProducts = arrProducts;
        this.date = date;
        this.sellerId = sellerId;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Map getProductsMap() {
        return mapProducts;
    }
    
    public void addProduct(Integer id, int quantity) {
//        mapProducts.add(id);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
    
}
