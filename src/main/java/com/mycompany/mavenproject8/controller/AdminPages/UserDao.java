/**
 * Diese Klasse ist eine DAO-Klasse (Data Access Object), 
 * die Benutzerdatenbankoperationen durchführt. Sie verwaltet das Lesen, 
 * Löschen, Hinzufügen und Aktualisieren von Benutzerdatensätzen.  
 * Sie verwendet die DBConnector-Klasse für die Datenbankverbindung. 
 * Funktionen: - Benutzer auflisten (ReadList) 
 * - Erstellen einer neuen Benutzerregistrierung (Register) 
 * - Löschen von Benutzern (delete) 
 * @author erkan
 */
package com.mycompany.mavenproject8.controller.AdminPages;

import com.mycompany.mavenproject8.entity.UserDatabase;
import com.mycompany.mavenproject8.util.DBConnector;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Named("SAUUserDatens")
@RequestScoped
public class UserDao extends DBConnector {

    private List<UserDatabase> users;
    private UserDatabase userDatabase;
    private String userErrorMsg = "";

    public UserDao() {
        // reset
        users = new ArrayList<>();
        userDatabase = new UserDatabase();
    }

    public List<UserDatabase> ReadList() {
        users = new ArrayList<>();
        DBConnector db = new DBConnector();
        try (
                Connection con = db.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM login_portal")) {

            while (rs.next()) {
                UserDatabase user = new UserDatabase();
                user.setId(rs.getInt("id"));
                System.out.println(user.getId());
                user.setUsername(rs.getString("username"));
                user.setVornachname(rs.getString("vornachname"));
                user.setPassword(rs.getString("password"));
                user.setKundenkatagori(rs.getInt("kundenkatagori"));
                user.setEmail(rs.getString("email"));
                user.setTelefon(rs.getString("telefon"));
                user.setCreatedAt(rs.getTimestamp("created_at"));  // created_at alanını java.sql.Timestamp olarak çekiyoruz
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public void register(UserDatabase user_entity) {

        try {
            DBConnector dbConnector = new DBConnector();
            Connection conn = dbConnector.getConnection();
            // Önce kullanıcı adını kontrol etmek için SELECT sorgusu
            String checkSql = "SELECT COUNT(*) FROM login_portal WHERE username = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkSql);
            checkStatement.setString(1, user_entity.getUsername());

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();  

            int userCount = resultSet.getInt(1);  

            if (userCount == 0) {
                
                String sql = "INSERT INTO login_portal (username, vornachname, password, kundenkatagori, email, telefon) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(1, user_entity.getUsername());
                statement.setString(2, user_entity.getVornachname());
                statement.setString(3, user_entity.getPassword());
                statement.setInt(4, user_entity.getKundenkatagori());
                statement.setString(5, user_entity.getEmail());
                statement.setString(6, user_entity.getTelefon());

                statement.executeUpdate();

                statement.close();
            } else {
                this.setUserErrorMsg("Geben Sie bitte andere username....");
            }

            // SELECT sorgusu kaynaklarını kapat
            checkStatement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("LoginApp.xhtml");  // Umleitung auf index.xhtml
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }  
    

    public void delete(UserDatabase kd) {
        try {
            DBConnector dbConnector = new DBConnector();
            Connection con = dbConnector.getConnection();
            String sql = "DELETE FROM login_portal WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, kd.getId());

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearList() {
        this.users = null;
    }

    public List<UserDatabase> getUsers() {
        users = new ArrayList<>();
        DBConnector db = new DBConnector();
        try (
                Connection con = db.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM login_portal")) {

            while (rs.next()) {
                UserDatabase user = new UserDatabase();
                user.setId(rs.getInt("id"));
                System.out.println(user.getId());
                user.setUsername(rs.getString("username"));
                user.setVornachname(rs.getString("vornachname"));
                user.setPassword(rs.getString("password"));
                user.setKundenkatagori(rs.getInt("kundenkatagori"));
                user.setEmail(rs.getString("email"));
                user.setTelefon(rs.getString("telefon"));
                user.setCreatedAt(rs.getTimestamp("created_at"));  // created_at alanını java.sql.Timestamp olarak çekiyoruz
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void setUsers(List<UserDatabase> users) {
        this.users = users;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public void setUserDatabase(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public String getUserErrorMsg() {
        return userErrorMsg;
    }

    public void setUserErrorMsg(String userErrorMsg) {
        this.userErrorMsg = userErrorMsg;
    }
}
