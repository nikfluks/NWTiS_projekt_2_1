/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.nikfluks.ejb.eb.Mqtt;

/**
 *
 * @author Nikola
 */
@Stateless
public class MqttFacade extends AbstractFacade<Mqtt> {

    @PersistenceContext(unitName = "nikfluks_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MqttFacade() {
        super(Mqtt.class);
    }

}
