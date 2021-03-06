/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.ejb.eb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "DNEVNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnevnik.findAll", query = "SELECT d FROM Dnevnik d")
    , @NamedQuery(name = "Dnevnik.findById", query = "SELECT d FROM Dnevnik d WHERE d.id = :id")
    , @NamedQuery(name = "Dnevnik.findByKorisnickoime", query = "SELECT d FROM Dnevnik d WHERE d.korisnickoime = :korisnickoime")
    , @NamedQuery(name = "Dnevnik.findByUrl", query = "SELECT d FROM Dnevnik d WHERE d.url = :url")
    , @NamedQuery(name = "Dnevnik.findByIpadresa", query = "SELECT d FROM Dnevnik d WHERE d.ipadresa = :ipadresa")
    , @NamedQuery(name = "Dnevnik.findByVrijemeprijema", query = "SELECT d FROM Dnevnik d WHERE d.vrijemeprijema = :vrijemeprijema")
    , @NamedQuery(name = "Dnevnik.findByTrajanje", query = "SELECT d FROM Dnevnik d WHERE d.trajanje = :trajanje")})
public class Dnevnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "KORISNICKOIME")
    private String korisnickoime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "URL")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IPADRESA")
    private String ipadresa;
    @Column(name = "VRIJEMEPRIJEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijemeprijema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAJANJE")
    private int trajanje;

    public Dnevnik() {
    }

    public Dnevnik(Integer id) {
        this.id = id;
    }

    public Dnevnik(Integer id, String korisnickoime, String url, String ipadresa, int trajanje) {
        this.id = id;
        this.korisnickoime = korisnickoime;
        this.url = url;
        this.ipadresa = ipadresa;
        this.trajanje = trajanje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIpadresa() {
        return ipadresa;
    }

    public void setIpadresa(String ipadresa) {
        this.ipadresa = ipadresa;
    }

    public Date getVrijemeprijema() {
        return vrijemeprijema;
    }

    public void setVrijemeprijema(Date vrijemeprijema) {
        this.vrijemeprijema = vrijemeprijema;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
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
        if (!(object instanceof Dnevnik)) {
            return false;
        }
        Dnevnik other = (Dnevnik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.nikfluks.ejb.eb.Dnevnik[ id=" + id + " ]";
    }

}
