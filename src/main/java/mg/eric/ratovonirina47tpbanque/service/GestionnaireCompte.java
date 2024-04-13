/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.eric.ratovonirina47tpbanque.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import mg.eric.ratovonirina47tpbanque.entity.CompteBancaire;

/**
 *
 * @author Ratovonirina
 */

@DataSourceDefinition (
    className="com.mysql.cj.jdbc.MysqlDataSource",
    name="java:app/jdbc/banque",
    serverName="localhost",
    portNumber=3306,
    user="root",              
    password="mysql1234MYSQL",
    databaseName="banque",
    properties = {
      "useSSL=false",
      "allowPublicKeyRetrieval=true",
      "driverClass=com.mysql.cj.jdbc.Driver"
    }
)
@ApplicationScoped
public class GestionnaireCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public List<CompteBancaire> getAllComptes() {
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }

    public long nbComptes() {
        TypedQuery<Long> query = em.createNamedQuery("CompteBancaire.countAll",Long.class);
        return query.getSingleResult();
    }
}
