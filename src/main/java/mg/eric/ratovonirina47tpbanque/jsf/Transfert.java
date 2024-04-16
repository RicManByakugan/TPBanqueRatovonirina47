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
@Named(value = "transfert")
@ViewScoped
public class Transfert implements Serializable {

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    private Long idSource;
    private Long idDestination;
    private int montant;

    public Long getIdSource() {
        return idSource;
    }

    public Long getIdDestination() {
        return idDestination;
    }

    public int getMontant() {
        return montant;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public void setIdDestination(Long idDestination) {
        this.idDestination = idDestination;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String transferer() {
        boolean erreur = false;
        CompteBancaire source = gestionnaireCompte.findById(idSource);
        CompteBancaire destination = gestionnaireCompte.findById(idDestination);
        if (source == null || destination == null) {
            Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
            erreur = true;
        } else {
            if (source.getSolde() < montant) {
                Util.messageErreur("Solde insuffisant !", "Solde insuffisant !", "form:source");
                erreur = true;
            }
            if (montant < 0) {
                Util.messageErreur("", "Ne peut pas etre negative !", "form:source");
                erreur = true;
            }
        }
        if (erreur) {
            return null;
        }
        gestionnaireCompte.transferer(source, destination, montant);
        Util.addFlashInfoMessage("Transfert correctement effectué entre " + source.getId() + " et " + destination.getId() + " = " + montant);
        return "transfert?faces-redirect=true";
    }
}
