package heig.smtp;

import heig.model.prank.Email;
import heig.model.email.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Base64;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Eric Bousbaa, Ilias Goujgali
 * @version 1.0
 *
 * Implémentation d'un client SMTP, permettant d'envoyer des courriels via la procotole SMTP.
 * Ne gère pas les protocoles SSL/TLS.
 */
public class SmtpClient implements ISmtpClient {

    private String SMTPaddress;
    private int SMTPport;

    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private static final String CMD_HELLO = "HELO";
    private static final String CMD_DATA  = "DATA";
    private static final String CMD_FROM  = "MAIL FROM";
    private static final String CMD_TO    = "RCPT TO";
    private static final String CMD_QUIT  = "QUIT";

    private static final String CR_LF       = "\r\n";
    private static final String END_OF_MSG  = CR_LF + "." + CR_LF;

    private static final String MSG_FROM    = "From";
    private static final String MSG_TO      = "To";
    private static final String MSG_CC      = "Cc";
    private static final String MSG_SUBJECT = "Subject";

    private static final String SMTP_ACTION_OKAY = "250";

    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/plain";

    private static final String DOMAIN = "JAY-Z.COM";

    /**
     * Notre connexion au serveur SMTP nécessite deux éléments.
     * @param address l'adresse du serveur SMTP
     * @param port et le port bindé
     */
    public SmtpClient(String address, int port){
        this.SMTPaddress = address;
        this.SMTPport = port;
    }

    /**
     * Envoi les emails un a un au serveur SMTP, à travers la même connnexion.
     * @param emails liste d'emails à envoyer. CF. interface Email pour plus  d'infos. sur ses attributs.
     */
    public void sendEmails(List<Email> emails){
        try {
            Socket socket = new Socket(SMTPaddress, SMTPport);
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), CHARACTER_ENCODING));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARACTER_ENCODING));

            if(socket == null || printWriter == null || bufferedReader == null){
                throw new IOException("Erreur de connexion.. ");
            }

            printWriter.write(String.format("%s %s%s", CMD_HELLO, DOMAIN, CR_LF));
            printWriter.flush();
            ignoreLines();

            for(Email email : emails){
                sendEmail(email);
            }

            printWriter.write(String.format("%s%s", CMD_QUIT, CR_LF));
            printWriter.flush();

            printWriter.close();
            bufferedReader.close();
            socket.close();

        } catch (IOException e) {
            LOG.severe(e.getMessage());

        }
    }

    /**
     * Envoi d'un email. On suppose la connexion au serveur SMTP ouverte.
     * @param email email à envoyer.
     */
    private void sendEmail(Email email){

        printWriter.write(String.format("%s: %s%s", CMD_FROM, email.getSender().getEmail(), CR_LF));
        printWriter.flush();
        ignoreLines();

        for(Person p : email.getReceivers().getMembers()){
            printWriter.write(String.format("%s: %s%s", CMD_TO, p.getEmail(), CR_LF));
            printWriter.flush();
            ignoreLines();
        }

        if(email.getCCReceivers() != null){
            for(Person p : email.getCCReceivers().getMembers()){
                printWriter.write(String.format("%s: %s%s", CMD_TO, p.getEmail(), CR_LF));
                printWriter.flush();
                ignoreLines();
            }
        }


        printWriter.write(String.format("%s%s", CMD_DATA, CR_LF));
        printWriter.flush();


        printWriter.write(String.format("%s: %s%s",MSG_FROM, email.getSender().getEmail(), CR_LF));
        printWriter.flush();


        for (Person victim : email.getReceivers().getMembers()) {
            printWriter.write(String.format("%s: %s%s", MSG_TO, victim.getEmail(), CR_LF));
        }
        printWriter.flush();

        if(email.getCCReceivers() != null){
            for (Person victim : email.getCCReceivers().getMembers()) {
                printWriter.write(String.format("%s: %s%s", MSG_CC, victim.getEmail(), CR_LF));
            }
            printWriter.flush();
        }

        printWriter.write(String.format("Content-Type: %s; charset=%s %s", CONTENT_TYPE, CHARACTER_ENCODING.toLowerCase(), CR_LF));
        printWriter.write(String.format("%s: =?utf-8?B?%s?=%s%s", MSG_SUBJECT, Base64.getEncoder().encodeToString(email.getMessage().getSubject().getBytes()), CR_LF, CR_LF));

        printWriter.write(String.format("%s%s", email.getMessage().getContent(), END_OF_MSG));
        printWriter.flush();

        ignoreLines();
    }


    /**
     * Logs les lignes envoyées par le serveur.
     */
    private void ignoreLines(){
        try{
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LOG.info("SRV: " + line);
                if(line.startsWith(SMTP_ACTION_OKAY+" ")){
                    break;
                }
            }
        }catch(Exception e){
            LOG.severe("WE DO NOT UNDERSTAND THE SERVER.");
            LOG.severe(e.getMessage());
        }
    }

}
