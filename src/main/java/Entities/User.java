/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author Ellian
 */
public class User {
    private Integer id;
    private String login;
    private String name;
    private String passwordHash;
    private int privilege;

    public User(Integer id, String login, String name, String passwordHash, int privilege) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.passwordHash = passwordHash;
        this.privilege = privilege;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }  
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
