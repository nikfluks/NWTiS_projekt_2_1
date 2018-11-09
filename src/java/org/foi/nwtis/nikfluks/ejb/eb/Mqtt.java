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
@Table(name = "MQTT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mqtt.findAll", query = "SELECT m FROM Mqtt m")
    , @NamedQuery(name = "Mqtt.findById", query = "SELECT m FROM Mqtt m WHERE m.id = :id")
    , @NamedQuery(name = "Mqtt.findByParkiralisteId", query = "SELECT m FROM Mqtt m WHERE m.parkiralisteId = :parkiralisteId")
    , @NamedQuery(name = "Mqtt.findByRegistracija", query = "SELECT m FROM Mqtt m WHERE m.registracija = :registracija")
    , @NamedQuery(name = "Mqtt.findByAkcija", query = "SELECT m FROM Mqtt m WHERE m.akcija = :akcija")
    , @NamedQuery(name = "Mqtt.findByVrijeme", query = "SELECT m FROM Mqtt m WHERE m.vrijeme = :vrijeme")})
public class Mqtt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARKIRALISTE_ID")
    private int parkiralisteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "REGISTRACIJA")
    private String registracija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AKCIJA")
    private int akcija;
    @Column(name = "VRIJEME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijeme;

    public Mqtt() {
    }

    public Mqtt(Integer id) {
        this.id = id;
    }

    public Mqtt(Integer id, int parkiralisteId, String registracija, int akcija) {
        this.id = id;
        this.parkiralisteId = parkiralisteId;
        this.registracija = registracija;
        this.akcija = akcija;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getParkiralisteId() {
        return parkiralisteId;
    }

    public void setParkiralisteId(int parkiralisteId) {
        this.parkiralisteId = parkiralisteId;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public int getAkcija() {
        return akcija;
    }

    public void setAkcija(int akcija) {
        this.akcija = akcija;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
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
        if (!(object instanceof Mqtt)) {
            return false;
        }
        Mqtt other = (Mqtt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.nikfluks.ejb.eb.Mqtt[ id=" + id + " ]";
    }

}
