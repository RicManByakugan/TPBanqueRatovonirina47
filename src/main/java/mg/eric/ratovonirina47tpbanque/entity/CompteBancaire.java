/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ratovonirina
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT DISTINCT c FROM CompteBancaire c LEFT JOIN FETCH c.operations"),
    @NamedQuery(name = "CompteBancaire.countAll", query = "SELECT COUNT(c) FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findById", query = "SELECT c FROM CompteBancaire c WHERE c.id=:id")})
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int solde;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OperationBancaire> operations = new ArrayList<>();
    @Version
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CompteBancaire() {
    }

    public CompteBancaire(String nom, int solde) {
        this.nom = nom;
        this.solde = solde;
        operations.add(new OperationBancaire("Création du compte", solde));
    }

    public List<OperationBancaire> getOperations() {
        return operations;
    }

    public void deposer(int montant) {
        solde += montant;
        operations.add(new OperationBancaire("Crédit", montant));
    }

    public void retirer(int montant) {
        if (montant < solde) {
            solde -= montant;
        } else {
            solde = 0;
        }
        operations.add(new OperationBancaire("Débit", -montant));
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public String getNom() {
        return nom;
    }

    public int getSolde() {
        return solde;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mg.eric.ratovonirina47tpbanque.entity.CompteBancaire[ id=" + id + " ]";
    }

}
