/**
 *  Diese Klasse ist eine JavaBean-Klasse, die Benutzerdaten verwaltet.
 *  Sie verwendet die UserDao-Klasse, um Benutzerdatenbankoperationen durchzuführen.
 *  Ihre Hauptfunktionen sind
 *  - Benutzerregistrierung (register)
 *  - Löschen eines Benutzers aus der Datenbank (delete)
 *  - Aktualisieren und Verwalten der Liste der Benutzer
 *  Diese Klasse wird in Verbindung mit JSF (JavaServer Faces) verwendet und wird während der gesamten Sitzung vom Benutzer benutzt.
 *  speichert Daten (SessionScoped). Datenbankoperationen DAO (Data Access Object) 
 *  wird durch Logik realisiert. 
 *  Funktionsweise:
 *  - Die Registrierung eines Benutzers erfolgt durch die Methode `register`.
 *  - Das Löschen eines Benutzers erfolgt mit der Methode `delete`.
 *  - Die Methode `getUserlist` gibt die aktuelle Benutzerliste aus der Datenbank zurück.
 * @author erkan
 */
package com.mycompany.mavenproject8.bean;


import com.mycompany.mavenproject8.controller.AdminPages.UserDao;
import com.mycompany.mavenproject8.entity.UserDatabase;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;



@Named("userdatenbean")
@SessionScoped
public class UserDatenBean implements Serializable{
     private UserDatabase userentity;  

    private List<UserDatabase> userlist;

    private UserDao userdao;

    public void delete(UserDatabase entity) {
        this.getUserdao().delete(entity);
        this.userlist.clear();
        this.userlist = this.userdao.ReadList();
        this.userdao.clearList();
        entity = new UserDatabase();
    }

    public void register() {
        this.getUserdao().register(userentity);
        userentity = new UserDatabase();
    }

    // Getter und Setter
    public UserDatabase getUserentity() {
        if (this.userentity == null) {
            userentity = new UserDatabase();
        }
        return userentity;
    }

    public void setUserentity(UserDatabase userentity) {
        this.userentity = userentity;
    }

    public UserDao getUserdao() {
        if (this.userdao == null) {
            userdao = new UserDao();
        }
        return userdao;
    }

    public void setUserdao(UserDao userdao) {
        this.userdao = userdao;
    }

    public List<UserDatabase> getUserlist() {
        this.userlist = this.getUserdao().ReadList();
        return userlist;
    }

    public void setUserlist(List<UserDatabase> userlist) {
        this.userlist = userlist;
    }

}
