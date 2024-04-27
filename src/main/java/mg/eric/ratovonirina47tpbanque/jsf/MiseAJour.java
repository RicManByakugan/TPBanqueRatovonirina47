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
@Named(value = "miseAJour")
@ViewScoped
public class MiseAJour implements Serializable {
    
    @Inject
    private GestionnaireCompte gestionnaireCompte;
    private Long idCompte;
    private CompteBancaire compteBancaire;
    
    /**
     * Creates a new instance of MiseAJour
     */
    public MiseAJour() {
    }
    
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
    
    public void mettreAjour(){
        Util.addFlashInfoMessage("Mise a jour effectué");
        gestionnaireCompte.update(compteBancaire);
    }
    
    public void loadCompte() {
        compteBancaire = gestionnaireCompte.findById(idCompte);
    }
    
}
