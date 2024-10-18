/**
 * Dieser Code ist eine Java-Klasse, die einen Benutzeranmeldeprozess durchführt.  
 * Die Informationen des Benutzers, der versucht, sich mit Benutzernamen und 
 * Passwort anzumelden, werden in der Datenbank überprüft, und wenn die 
 * Informationen korrekt sind, wird der Benutzer je nach Benutzerberechtigung 
 * (Kundenkategorie) auf verschiedene Seiten weitergeleitet.
 * @author erkan
 */

package com.mycompany.mavenproject8.bean;

import com.mycompany.mavenproject8.entity.UserDatabase;
import com.mycompany.mavenproject8.entity.UserManager;
import com.mycompany.mavenproject8.util.DBConnector;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Named("applogincontroller")
@SessionScoped
public class AppLoginController extends DBConnector implements Serializable {

    private UserManager user;

    public AppLoginController() {
    }

    public void login() throws IOException {

        try {
            Connection conn = getConnection();
            String checkSql = "SELECT kundenkatagori FROM login_portal WHERE username = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(checkSql);
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());

            ResultSet rs = st.executeQuery();
            
            // Sonucu kontrol et
            if (rs.next()) {
                user.setKundenkatagori(rs.getInt(1));
            } else {
                System.out.println("Nachtricht : Benutzername oder Kennwort ist falsch!");
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////////////////////////////////
        if (user.getKundenkatagori() ==3) {
             //Admin
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("validUser", user);
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdminControlPanel.xhtml");
        } else if (user.getKundenkatagori()==2) {
            //User
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("validUser", user);
            FacesContext.getCurrentInstance().getExternalContext().redirect("UserReportLost.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nachtricht : Benutzername oder Kennwort ist falsch!"));
        }
    }

    // Getter und Setter
    public UserManager getUser() {
        if (user == null) {
            user = new UserManager();
        }
        return user;
    }

    public void setUser(UserManager user) {
        this.user = user;
    }

}
