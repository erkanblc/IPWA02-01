package com.mycompany.mavenproject8.entity;

/**
 *
 * @author erkan
 */
public class UserManager {

    private String username;
    private String password;
    private int kundenkatagori;

    public UserManager() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKundenkatagori() {
        return kundenkatagori;
    }

    public void setKundenkatagori(int kundenkatagori) {
        this.kundenkatagori = kundenkatagori;
    }

}
