package org.foi.nwtis.nikfluks.dretve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.foi.nwtis.nikfluks.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author Nikola
 */
public class ObradaEmaila extends Thread {

    BP_Konfiguracija bpk;
    String emailServer;
    String emailPrimatelj;
    String emailLozinka;
    String emailFolder;
    int port;
    int intervalDretve;
    String predmet;

    boolean radi = true;
    Session session;
    Properties properties = System.getProperties();
    private Store store;
    private Folder folderInbox;
    private Folder folderNWTiS;
    List<Message> grupaEmailPoruka = new ArrayList<>();
    int nPorukaZaCitanje;
    int trenutniBrojNWTiSPoruka;
    int redniBrojJMSPoruke;
    Date vrijemePrethodnePoruke;
    Date vrijemeTrenutnePoruke;
    int trajanje;
    //int ukupniBrojNWTiSPoruka;

    public ObradaEmaila(BP_Konfiguracija bpk) {
        this.bpk = bpk;
    }

    @Override
    public void interrupt() {
        radi = false;
        super.interrupt();
    }

    @Override
    public void run() {
        try {
            while (radi) {
                System.out.println("dretva email radi");
                long pocetak = System.currentTimeMillis();
                citajPorukePoGrupama();
                long kraj = System.currentTimeMillis();
                trajanje = (int) (kraj - pocetak);
                posaljiJMSPoruku();
                sleep(intervalDretve);
            }
        } catch (Exception ex) {
            System.out.println("Greška kod obrade emaila: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public synchronized void start() {
        if (dohvatiPodatkeIzKonfiguracije()) {
            super.start();
        } else {
            System.err.println("Greska kod dohvacanja podataka iz konfiguracije! Dretva nije startana!");
        }
    }

    private boolean dohvatiPodatkeIzKonfiguracije() {
        try {
            emailServer = bpk.getEmailServer_();
            emailPrimatelj = bpk.getEmailPrimatelj_();
            emailLozinka = bpk.getEmailPrimateljLozinka_();
            intervalDretve = Integer.parseInt(bpk.getIntervalDretveMail_()) * 1000;
            port = Integer.parseInt(bpk.getMailImapPort());
            emailFolder = bpk.getMailFolderNWTiS();
            nPorukaZaCitanje = Integer.parseInt(bpk.getMailNumMessagesToRead());
            predmet = bpk.getEmailPredmet_();
            return true;
        } catch (Exception ex) {
            System.out.println("wtf: " + ex.getLocalizedMessage());
            return false;
        }
    }

    private void postaviFoldere() {
        try {
            properties.put("mail.smtp.host", emailServer);
            session = Session.getInstance(properties, null);
            store = session.getStore("imap");
            store.connect(emailServer, port, emailPrimatelj, emailLozinka);
            folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);

            folderNWTiS = store.getFolder(emailFolder);
            if (!folderNWTiS.exists()) {
                folderNWTiS.create(Folder.HOLDS_MESSAGES);
            }
        } catch (MessagingException ex) {
            System.err.println("Pogreška kod postavljanja email foldera: " + ex.getLocalizedMessage());
        }
    }

    private void citajPorukePoGrupama() {
        postaviFoldere();
        trenutniBrojNWTiSPoruka = 0;
        try {
            if (folderInbox.getMessageCount() > 0) {
                int ukupanBrojPoruka = folderInbox.getMessageCount();
                for (int i = 0; i < ukupanBrojPoruka; i += nPorukaZaCitanje) {
                    grupaEmailPoruka.clear();
                    if (ukupanBrojPoruka < i + nPorukaZaCitanje) {
                        grupaEmailPoruka.addAll(Arrays.asList(folderInbox.getMessages(i + 1, ukupanBrojPoruka)));
                    } else {
                        grupaEmailPoruka.addAll(Arrays.asList(folderInbox.getMessages(i + 1, i + nPorukaZaCitanje)));
                    }
                    odrediNWTiSPoruke(grupaEmailPoruka);
                }
            }
            //ukupniBrojNWTiSPoruka = folderNWTiS.getMessageCount();
            folderInbox.close(true);
            store.close();
        } catch (MessagingException ex) {
            System.err.println("Greška kod čitanja poruka po grupama: " + ex.getLocalizedMessage());
        }
    }

    private void odrediNWTiSPoruke(List<Message> grupaEmailPoruka) throws MessagingException {
        for (Message poruka : grupaEmailPoruka) {
            if (!poruka.isSet(Flags.Flag.SEEN)) {
                if (poruka.getSubject().equals(predmet) && (poruka.isMimeType("text/json") || poruka.isMimeType("application/json"))) {
                    //nwtis poruka
                    poruka.setFlag(Flags.Flag.SEEN, true);
                    prebaciPorukuUNWTiSFolder(poruka);
                    trenutniBrojNWTiSPoruka++;
                } else {
                    poruka.setFlag(Flags.Flag.SEEN, false);
                }
            }
        }
    }

    private void prebaciPorukuUNWTiSFolder(Message poruka) {
        try {
            Message[] messages = new Message[1];
            messages[0] = poruka;
            folderInbox.copyMessages(messages, folderNWTiS);
            folderInbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
        } catch (MessagingException ex) {
            System.err.println("Greška kod prebacivanja poruke u nwtis folder");
        }
    }

    private void posaljiJMSPoruku() {
        if (trenutniBrojNWTiSPoruka > 0) {
            redniBrojJMSPoruke++;
            if (redniBrojJMSPoruke == 1) {
                vrijemePrethodnePoruke = null;
            }
            vrijemeTrenutnePoruke = new Date();
            JMS1 jms = new JMS1(redniBrojJMSPoruke, vrijemeTrenutnePoruke, vrijemePrethodnePoruke, trajanje, trenutniBrojNWTiSPoruka);
            try {
                sendJMSMessageToNWTiS_nikfluks_1(jms);
            } catch (JMSException | NamingException ex) {
                System.err.println("Greska kod slanja JMS poruke: " + ex.getLocalizedMessage());
            }
            vrijemePrethodnePoruke = vrijemeTrenutnePoruke;
        }
    }

    private javax.jms.Message createJMSMessageForjmsNWTiS_nikfluks_1(javax.jms.Session session, JMS1 messageData) throws JMSException {
        ObjectMessage om = session.createObjectMessage();
        om.setObject(messageData);
        return om;
    }

    private void sendJMSMessageToNWTiS_nikfluks_1(JMS1 messageData) throws JMSException, NamingException {
        Context c = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) c.lookup("jms/NWTiS_QF_nikfluks_1");
        Connection conn = null;
        javax.jms.Session s = null;
        try {
            conn = cf.createConnection();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) c.lookup("jms/NWTiS_nikfluks_1");
            MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessageForjmsNWTiS_nikfluks_1(s, messageData));
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
