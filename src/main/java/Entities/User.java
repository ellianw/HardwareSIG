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
    private int privilege;

    public User(Integer id, String login, String name, int privilege) {
        this.id = id;
        this.login = login;
        this.name = name;
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
    
    
}
