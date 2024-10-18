/**
 *
 * Diese Klasse ist eine DAO-Klasse (Data Access Object), die zur Verwaltung 
 * von Koordinatendaten in der Datenbank dient. Sie speichert, aktualisiert, 
 * löscht und liest die von Benutzern und anonymen Personen an die Datenbank 
 * gemeldeten Koordinaten.  Sie ist auch mit JSF integriert, um diese Daten 
 * während der Sitzung zu verarbeiten.  
 * Hauptfunktionen:  - Lesen von Koordinatendaten aus der Datenbank (readkdlist)
 * - Erstellen neuer Koordinatendaten (create) - Löschen von Koordinatendaten 
 * aus der Datenbank (delete) - Aktualisieren von Koordinatendaten in der 
 * Datenbank (update) - Speichern von Meldungen anonymer oder registrierter 
 * Benutzer (saveAnonymMeldungsDaten, saveUserData) Datenbankoperationen werden 
 * über JDBC durchgeführt und sind im DAO-Modell geschrieben. Diese Klasse 
 * enthält auch Funktionen zum Filtern von Koordinaten nach Benutzernamen, 
 * Status und anderen Kriterien sowie zur Verwaltung von Markierungsdaten.  
 * Die Klasse ist für die Verwendung während einer Sitzung im JSF-Framework 
 * konzipiert (SessionScoped).
 * @author erkan
 */
package com.mycompany.mavenproject8.controller.AdminPages;

import com.mycompany.mavenproject8.util.DBConnector;
import com.mycompany.mavenproject8.entity.KoordinatDaten;
import com.mycompany.mavenproject8.entity.UserKoorDatabaseXY;
import com.mycompany.mavenproject8.entity.UserManager;
import com.mycompany.mavenproject8.util.DateTimeUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Named("SAUKoordinatDatens")
@SessionScoped
public class AdminDao extends DBConnector implements Serializable{

    private List<KoordinatDaten> koordinatListesi;
    private List<UserKoorDatabaseXY> markerData;

    
    ////Read
    public List<KoordinatDaten> readkdlist() {
        
        
            koordinatListesi = new ArrayList<>();
            markerData = new ArrayList<>();
            try {
                Connection con = getConnection();
                String sql = "SELECT * FROM koordinatendaten";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                
                while (rs.next()) {
                    KoordinatDaten user = new KoordinatDaten();
                    UserKoorDatabaseXY markerCache = new UserKoorDatabaseXY(
                            rs.getDouble("kundenx"), rs.getDouble("kundeny"),
                            rs.getString("username") != null ? rs.getString("username") : "Anonym",
                            rs.getBoolean("situation")
                    );
                    user.setId(rs.getInt("id"));
                    user.setKundenx(rs.getDouble("kundenx"));
                    user.setKundeny(rs.getDouble("kundeny"));
                    user.setSituation(rs.getBoolean("situation"));
                    user.setBergungsperson(rs.getString("bergungsperson").length() != 0 ? rs.getString("bergungsperson") : "X");

                    user.setCreated_at(rs.getString("created_at"));
                    user.setReportednote(rs.getString("reported_note"));
                    user.setOptiondata(rs.getInt("optiondata"));
                    user.setKundenkatagori(rs.getInt("kundenkatagori"));
                    user.setGeschatzteGrosse(rs.getFloat("geschatzteGrosse"));
                    user.setUsername(rs.getString("username") != null ? rs.getString("username") : "Anonym");
                    markerData.add(markerCache);
                    koordinatListesi.add(user);
                }

                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        

        return koordinatListesi;
    }
     // Create işlemi
    public void create(KoordinatDaten kd) {
        System.out.println(kd.toString());
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO koordinatendaten (kundenx, kundeny, situation, bergungsperson, created_at, kundenkatagori, username) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, kd.getKundenx());
            pstmt.setDouble(2, kd.getKundeny());
            pstmt.setBoolean(3, kd.isSituation());
            pstmt.setString(4, kd.getBergungsperson());
            pstmt.setString(5, kd.getCreated_at());
            pstmt.setInt(6, kd.getKundenkatagori());
            pstmt.setString(7, kd.getUsername());

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete işlemi
    public void delete(KoordinatDaten kd) {
        try {
            Connection con = getConnection();
            String sql = "DELETE FROM koordinatendaten WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, kd.getId());

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update işlemi
    public void update(KoordinatDaten kd) {
        System.out.println("update kismi " + kd.toString());
        UserManager vUser = (UserManager) FacesContext.getCurrentInstance()
                             .getExternalContext()
                             .getSessionMap()
                             .get("validUser");
        kd.setBergungsperson(vUser.getUsername());
        System.out.println(vUser.getUsername());
        try {
            Connection con = getConnection();
            String sql = "UPDATE koordinatendaten SET kundenx = ?, kundeny = ?, situation = ?, "
                    + "bergungsperson = ?, created_at = ?, kundenkatagori = ?, username = ?, last_modified = ?, optiondata = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, kd.getKundenx());
            pstmt.setDouble(2, kd.getKundeny());
            pstmt.setBoolean(3, kd.isSituation());
                    System.out.println(vUser.getUsername());

            pstmt.setString(4, kd.getBergungsperson());
            pstmt.setString(5, kd.getCreated_at());
            pstmt.setInt(6, kd.getKundenkatagori());
            pstmt.setString(7, kd.getUsername());
            pstmt.setString(8, DateTimeUtils.getTimeFormat());
            pstmt.setInt(9, kd.getOptiondata());
            pstmt.setInt(10, kd.getId());
            
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<KoordinatDaten> getKoordinatListesi() {
            koordinatListesi = new ArrayList<>();
            markerData = new ArrayList<>();
            try {
                Connection con = getConnection();
                String sql = "SELECT * FROM koordinatendaten";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                // Veritabanından çekilen verileri koordinatListesi'ne ekliyoruz
                while (rs.next()) {
                    KoordinatDaten user = new KoordinatDaten();
                    UserKoorDatabaseXY markerCache = new UserKoorDatabaseXY(
                            rs.getDouble("kundenx"), rs.getDouble("kundeny"),
                            rs.getString("username") != null ? rs.getString("username") : "Anonym",
                            rs.getBoolean("situation")
                    );
                    user.setId(rs.getInt("id"));
                    user.setKundenx(rs.getDouble("kundenx"));
                    user.setKundeny(rs.getDouble("kundeny"));
                    user.setSituation(rs.getBoolean("situation"));
                    String sUser = rs.getString("bergungsperson");
                    // Eğer sUser null veya boş ise, vUser.getUsername() değeri "X" olacak.
                    if (sUser == null || sUser.isEmpty() || sUser.equals("X")) {
                        sUser = "X";
                    }
                    user.setBergungsperson(sUser);

                    user.setCreated_at(rs.getString("created_at"));

                    user.setKundenkatagori(rs.getInt("kundenkatagori"));

                    user.setUsername(rs.getString("username") != null ? rs.getString("username") : "Anonym");
                    markerData.add(markerCache);
                    koordinatListesi.add(user);
                }

                rs.close();
                stmt.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        return koordinatListesi;
    }

    public void setKoordinatListesi(List<KoordinatDaten> koordinatListesi) {
        this.koordinatListesi = koordinatListesi;
    }

    public List<UserKoorDatabaseXY> getMarkerData() {
        return markerData;
    }

    public void setMarkerData(List<UserKoorDatabaseXY> markerData) {
        this.markerData = markerData;
    }

    public void clearList() {
        this.koordinatListesi = null;
        this.markerData = null;
    }

    
    public void saveAnonymMeldungsDaten(KoordinatDaten kd) {
            koordinatListesi = new ArrayList<>();
            markerData = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO koordinatendaten (kundenx, kundeny, situation, bergungsperson, created_at, kundenkatagori, username, optiondata, reported_note, geschatzteGrosse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, kd.getKundenx());
            statement.setDouble(2, kd.getKundeny());
            statement.setBoolean(3, false);
            statement.setString(4, "");
            statement.setDate(5, Date.valueOf(DateTimeUtils.getDateFormat())); // Tarih 'DATE' tipinde olduğu için java.sql.Date kullanıyoruz
            statement.setInt(6, 1);// melder Anonym immer 1
            statement.setString(7, "Anonym");
            statement.setInt(8, 1);
            statement.setString(9, kd.getReportednote());
            statement.setFloat(10, kd.getGeschatzteGrosse());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void saveUserData(KoordinatDaten kd) {
        
    UserManager vUser = (UserManager) FacesContext.getCurrentInstance()
                             .getExternalContext()
                             .getSessionMap()
                             .get("validUser");
        System.out.println("burda1");
       try {
        Connection conn = getConnection();
            String sql = "INSERT INTO koordinatendaten (kundenx, kundeny, situation, bergungsperson, created_at, kundenkatagori, username, optiondata, reported_note, geschatzteGrosse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, kd.getKundenx());
            statement.setDouble(2, kd.getKundeny());
            statement.setBoolean(3, false);
            statement.setString(4, "");
            statement.setDate(5, Date.valueOf(DateTimeUtils.getDateFormat())); // Tarih 'DATE' tipinde olduğu için java.sql.Date kullanıyoruz
            statement.setInt(6, 2); // melder User immer 2
            statement.setString(7, vUser.getUsername());
            statement.setInt(8, 1);
            statement.setString(9, kd.getReportednote());
            statement.setFloat(10, kd.getGeschatzteGrosse());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
