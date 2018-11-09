package org.foi.nwtis.nikfluks.dretve;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nikola
 */
public class JMS1 implements Serializable {
    int redniBroj;
    Date vrijemeTrenutne;
    Date vrijemePrethodne;
    int trajanje;
    int brojNWTiSPoruka;

    public JMS1() {
    }

    public JMS1(int redniBroj, Date vrijemeTrenutne, Date vrijemePrethodne, int trajanje, int brojNWTiSPoruka) {
        this.redniBroj = redniBroj;
        this.vrijemeTrenutne = vrijemeTrenutne;
        this.vrijemePrethodne = vrijemePrethodne;
        this.trajanje = trajanje;
        this.brojNWTiSPoruka = brojNWTiSPoruka;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public Date getVrijemeTrenutne() {
        return vrijemeTrenutne;
    }

    public void setVrijemeTrenutne(Date vrijemeTrenutne) {
        this.vrijemeTrenutne = vrijemeTrenutne;
    }

    public Date getVrijemePrethodne() {
        return vrijemePrethodne;
    }

    public void setVrijemePrethodne(Date vrijemePrethodne) {
        this.vrijemePrethodne = vrijemePrethodne;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public int getBrojNWTiSPoruka() {
        return brojNWTiSPoruka;
    }

    public void setBrojNWTiSPoruka(int brojNWTiSPoruka) {
        this.brojNWTiSPoruka = brojNWTiSPoruka;
    }


}
