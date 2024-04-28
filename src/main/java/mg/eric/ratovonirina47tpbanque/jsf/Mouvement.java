/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.jsf;

import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
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

    public void verificationSolde(FacesContext fc, UIComponent composant, Object value) {
        UIInput composantTypeMouvement = (UIInput) composant.findComponent("type");
        String TypeDeMouvement = (String) composantTypeMouvement.getLocalValue();
        if (TypeDeMouvement == null) {
            return;
        }
        int sommeMouvement = (int) value;
        if (TypeDeMouvement.equals("retrait")) {
            if (compteBancaire.getSolde() < sommeMouvement ) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Solde insuffisante","");
                throw new ValidatorException(message);
            }
        }else if (TypeDeMouvement.equals("depot")) {
            return;
        }else{
            Util.messageErreur("Choisissez entre retait et depot", "Choisissez entre retait et depot", "form:source");
            return;
        }
    }

    public String faireMouvement() {
        try {
            if(somme <= 0){
                Util.addFlashInfoMessage("Entrer une somme supérieur à 0");
                return null;
            }
            if (type.equals("depot")) {
                compteBancaire.deposer(somme);
                gestionnaireCompte.update(compteBancaire);
                Util.addFlashInfoMessage("Depot effectué pour " + compteBancaire.getNom() + " avec la somme de " + somme);
                return "listeComptes?faces-redirect=true";
            } else if (type.equals("retrait")) {
                compteBancaire.retirer(somme);
                gestionnaireCompte.update(compteBancaire);
                Util.addFlashInfoMessage("Retrait effectué pour " + compteBancaire.getNom() + " avec la somme de " + somme);
                return "listeComptes?faces-redirect=true";
            } else {
                Util.messageErreur("Choisissez entre retait et depot", "Choisissez entre retait et depot", "form:source");
                return null;
            }
        } catch (OptimisticLockException e) {
            Util.addFlashInfoMessage("Réessayer plus tard : Le compte de " + compteBancaire.getNom() + " a été modifié ou supprimé par un autre utilisateur !");
            return null;
        }

    }

}
