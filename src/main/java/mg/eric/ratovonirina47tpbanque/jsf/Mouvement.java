/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
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

    public Mouvement() {
    }

    public void loadCompte() {
        compteBancaire = gestionnaireCompte.findById(idCompte);
    }

    public String faireMouvement() {
        try {
            String resultat = gestionnaireCompte.FaireLeMouvement(type, somme, compteBancaire);
            return resultat;
        } catch (OptimisticLockException e) {
            Util.addFlashInfoMessage("Réessayer plus tard : Le compte de " + compteBancaire.getNom() + " a été modifié ou supprimé par un autre utilisateur !");
            return null;
        }
    }

}
