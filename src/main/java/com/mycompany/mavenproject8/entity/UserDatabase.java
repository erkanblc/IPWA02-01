
package com.mycompany.mavenproject8.entity;

/**
 *
 * @author erkan
 */
import java.sql.Timestamp;

public class UserDatabase {

    private int id;
    private String username;
    private String vornachname;
    private String password;
    private Integer kundenkatagori;
    private String email;
    private String telefon;
    private Timestamp createdAt;
    
    // Getter ve Setter metodları

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVornachname() {
        return vornachname;
    }

    public void setVornachname(String vornachname) {
        this.vornachname = vornachname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getKundenkatagori() {
        return kundenkatagori;
    }

    public void setKundenkatagori(Integer kundenkatagori) {
        this.kundenkatagori = kundenkatagori;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    
}
