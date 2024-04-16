/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import mg.eric.ratovonirina47tpbanque.entity.CompteBancaire;
import mg.eric.ratovonirina47tpbanque.jsf.util.Util;
import mg.eric.ratovonirina47tpbanque.service.GestionnaireCompte;

/**
 *
 * @author Ratovonirina
 */
@Named(value = "mouvement")
@ViewScoped
public class Mouvement implements Serializable {

    /**
     * Creates a new instance of Mouvement
     */
    @Inject
    private GestionnaireCompte gestionnaireCompte;

    private Long idCompte;
    private CompteBancaire compteBancaire;
    private int somme;
    private String type;

    public Long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Long idCompte) {
        this.idCompte = idCompte;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public int getSomme() {
        return somme;
    }

    public void setSomme(int somme) {
        this.somme = somme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Mouvement() {}

    public void loadCompte() {
        compteBancaire = gestionnaireCompte.findById(idCompte);
    }

    public String faireMouvement() {
        if (type.equals("depot")) {
            compteBancaire.deposer(somme);
            gestionnaireCompte.update(compteBancaire);
        } else if (type.equals("retrait")) {
            compteBancaire.retirer(somme);
            gestionnaireCompte.update(compteBancaire);
        } else {
            Util.messageErreur("Choisissez entre retait et depot", "Choisissez entre retait et depot", "form:source");
            return "listeComptes?faces-redirect=true";
        }        
        return "listeComptes?faces-redirect=true";
    }

}
