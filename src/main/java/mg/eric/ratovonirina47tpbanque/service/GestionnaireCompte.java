/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import mg.eric.ratovonirina47tpbanque.entity.CompteBancaire;
import mg.eric.ratovonirina47tpbanque.jsf.util.Util;

/**
 *
 * @author Ratovonirina
 */

@DataSourceDefinition(className = "com.mysql.cj.jdbc.MysqlDataSource", name = "java:app/jdbc/banque", serverName = "localhost", portNumber = 3306, user = "root", password = "root", databaseName = "banque", properties = {
        "useSSL=false",
        "allowPublicKeyRetrieval=true",
        "driverClass=com.mysql.cj.jdbc.Driver"
})
@ApplicationScoped
public class GestionnaireCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    @Transactional
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public List<CompteBancaire> getAllComptes() {
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }

    public long nbComptes() {
        TypedQuery<Long> query = em.createNamedQuery("CompteBancaire.countAll", Long.class);
        return query.getSingleResult();
    }

    public CompteBancaire findById(Long id) {
        return em.find(CompteBancaire.class, id);
    }

    @Transactional
    public String transferer(CompteBancaire source, CompteBancaire destination, int montant) {
        boolean erreur = false;
        if (source == null || destination == null) {
            Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
            erreur = true;
        } else {
            if (montant <= 0) {
                Util.messageErreur("Entrer une somme supérieur à 0");
                erreur = true;
            }
            if (source.getSolde() < montant) {
                Util.addFlashInfoMessage("Solde insuffisant pour le compte source (Montant : " + source.getSolde() +")");
                return null;
            }
            if (source.getSolde() < montant) {
                Util.messageErreur("Solde insuffisant !");
                erreur = true;
            }
        }
        if (erreur) {
            return null;
        }
        source.retirer(montant);
        destination.deposer(montant);
        update(source);
        update(destination);
        Util.addFlashInfoMessage("Transfert correctement effectué");
        return "listeComptes";
    }

    @Transactional
    public String FaireLeMouvement(String type, int somme, CompteBancaire compte) {
        if (somme <= 0) {
            Util.addFlashInfoMessage("Entrer une somme supérieur à 0");
            return null;
        }
        if (compte.getSolde() < somme) {
            Util.addFlashInfoMessage("Solde insuffisant");
            return null;
        }
        if (type.equals("depot")) {
            compte.deposer(somme);
            update(compte);
            Util.addFlashInfoMessage("Depot effectué pour " + compte.getNom() + " avec la somme de " + somme);
            return "listeComptes?faces-redirect=true";
        } else if (type.equals("retrait")) {
            compte.retirer(somme);
            update(compte);
            Util.addFlashInfoMessage("Retrait effectué pour " + compte.getNom() + " avec la somme de " + somme);
            return "listeComptes?faces-redirect=true";
        } else {
            Util.messageErreur("Choisissez entre retait et depot", "Choisissez entre retait et depot", "form:source");
            return null;
        }
    }

    @Transactional
    public CompteBancaire update(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }

    @Transactional
    public void supprimerCompte(CompteBancaire compte) {
        em.remove(em.merge(compte));
    }
}
