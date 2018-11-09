package org.foi.nwtis.nikfluks.ejb.sb;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import org.foi.nwtis.nikfluks.dretve.ObradaEmaila;
import org.foi.nwtis.nikfluks.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author Nikola
 */
@Singleton
@LocalBean
public class SingletonBean {

    ObradaEmaila obradaEmaila;

    public void pokreniObraduEmaila(BP_Konfiguracija bpk) {
        obradaEmaila = new ObradaEmaila(bpk);
        obradaEmaila.start();
    }

    @PreDestroy
    public void zaustaviObraduEmaila() {
        System.out.println("brisem sb");
        if (obradaEmaila != null) {
            obradaEmaila.interrupt();
            System.out.println("interuptam sb");
        }
    }

}
