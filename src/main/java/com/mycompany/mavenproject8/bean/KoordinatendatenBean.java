/**
 * Diese Klasse ist eine JavaBean-Klasse, die Koordinatendaten 
 * („KoordinatDaten“) verwaltet.  Sie führt Operationen wie das 
 * Speichern, Aktualisieren und Löschen von Benutzer- und anonymen
 * Berichten durch.  Koordinatendaten werden über ein vom 
 * Administrator verwaltetes DAO (Data Access Object) in der Datenbank
 * gespeichert, aktualisiert oder gelöscht. Gleichzeitig werden 
 * die Benutzer- oder anonymen Berichte über diese Klasse verwaltet.
 * Die Klasse ist mit JSF (JavaServer Faces) verbunden und speichert 
 * Daten während der Sitzung (SessionScoped).  Funktionen  - Speichern 
 * von Koordinatendaten (sowohl Benutzer- als auch anonyme Berichte) - 
 * Aktualisieren und Löschen von Koordinatendaten - Abrufen einer gefilterten
 * Liste von Koordinaten - Berechnen der Gesamtzahl von Koordinatendaten 
 * Diese Klasse ist für die Verwendung in JSF definiert und arbeitet 
 * mit der Klasse AdminDao zusammen, um * Datenbankoperationen durchzuführen.
 * 
 * @author erkan
 */
package com.mycompany.mavenproject8.bean;

import com.mycompany.mavenproject8.controller.AdminPages.AdminDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import com.mycompany.mavenproject8.entity.KoordinatDaten;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Named("koordinatendatenbean")
@SessionScoped
public class KoordinatendatenBean implements Serializable {

    private KoordinatDaten kd_entity;  //kd = KoordinatDaten

    private List<KoordinatDaten> kd_list;

    private AdminDao kd_dao;

    private List<KoordinatDaten> kd_list_filtered;
    
    private int getListSize = 0; 
    
    public void update() {
        this.getKd_dao().update(kd_entity);
        this.kd_entity = new KoordinatDaten();
        this.kd_list = this.getKd_dao().readkdlist();
    }

    public void delete() {
        this.getKd_dao().delete(kd_entity);
        this.kd_list.clear();
        this.kd_list = this.kd_dao.readkdlist();
        this.kd_dao.clearList();
        this.kd_entity = new KoordinatDaten(); 
    }
    
    public void saveAnonymMeldung() {
        this.getKd_dao().saveAnonymMeldungsDaten(kd_entity);
        this.kd_entity = new KoordinatDaten(); 
    }
    
    public void saveUserMeldung() {
        this.getKd_dao().saveUserData(kd_entity);
        this.kd_entity = new KoordinatDaten();
    }

    public KoordinatDaten getKd_entity() {
        if (this.kd_entity == null) {
            kd_entity = new KoordinatDaten();
        }
        return kd_entity;
    }

    public void setKd_entity(KoordinatDaten kd_entity) {
        this.kd_entity = kd_entity;
    }

    public List<KoordinatDaten> getKd_list() {

        this.kd_list = this.getKd_dao().readkdlist();
        return kd_list;
    }

    public void setKd_list(List<KoordinatDaten> kd_list) {
        this.kd_list = kd_list;
    }

    public AdminDao getKd_dao() {
        if (this.kd_dao == null) {
            kd_dao = new AdminDao();
        }
        return kd_dao;
    }

    public void setKd_dao(AdminDao kd_dao) {
        this.kd_dao = kd_dao;
    }

    public List<KoordinatDaten> getKd_list_filtered() {
        kd_list_filtered = this.getKd_dao().readkdlist().stream()
                .filter(f -> !f.isSituation())
                .collect(Collectors.toList());
        return kd_list_filtered;
    }

    public void setKd_list_filtered(List<KoordinatDaten> kd_list_filtered) {
        this.kd_list_filtered = kd_list_filtered;
    }

    public int getGetListSize() {
        this.getListSize = kd_list_filtered.size();
        return getListSize;
    }

}
