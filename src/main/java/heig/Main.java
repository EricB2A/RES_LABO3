package heig;

import heig.exception.InvalidPrankConfiguration;
import heig.config.ConfigManager;
import heig.model.prank.Email;
import heig.model.prank.PrankGenerator;
import heig.smtp.SmtpClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {


        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();

            PrankGenerator prankGenerator = new PrankGenerator();
            List<Email> pranks = prankGenerator.generatePranks(config.getNbGroups(), config.getNbMembers(), config.getVictims(), config.getCcWitnesses(),config.getMessages());

            SmtpClient smtpClient = new SmtpClient(config.getSmtpServerIp(), config.getSmtpServerPort());
            smtpClient.sendEmails(pranks);

        } catch (InvalidPrankConfiguration | IOException e) {
            LOG.severe("AN ERROR OCCURRED");
            LOG.severe(e.getMessage());
            System.exit(1);
        }
    }
}
