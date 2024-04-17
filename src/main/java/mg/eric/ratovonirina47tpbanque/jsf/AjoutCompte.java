/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import mg.eric.ratovonirina47tpbanque.entity.CompteBancaire;
import mg.eric.ratovonirina47tpbanque.jsf.util.Util;
import mg.eric.ratovonirina47tpbanque.service.GestionnaireCompte;

/**
 *
 * @author Ratovonirina
 */
@Named(value = "ajoutCompte")
@ViewScoped
public class AjoutCompte implements Serializable {

    private String nom ;

    @PositiveOrZero
    private int solde ;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    @Inject
    private GestionnaireCompte gestionnaireCompte ;
    /**
     * Creates a new instance of AjoutCompte
     */
    public AjoutCompte() {
    }

    public String ajout()
    {
        CompteBancaire compteBancaire = new CompteBancaire( nom , solde );
        gestionnaireCompte.creerCompte(compteBancaire);
        Util.addFlashInfoMessage("Le compte de " + nom + " a été bien créé avec un solde inital de " + solde);
        return "listeComptes?faces-redirect=true" ;
    }
    
}
