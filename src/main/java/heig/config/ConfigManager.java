package heig.config;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import heig.exception.InvalidPrankConfiguration;
import heig.model.email.Message;
import heig.model.email.Person;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *  Cette classe permet de lire et de stocker les fichiers de configuration :
 *         - config.json contenant la configuration du serveur SMTP ainsi que les configurations sur les campagnes de prank
 *         - victimes.json contenant les adresses e-mails des victimes
 *         - messages.json contenant la listes de message
 *  Cette classe est un singleton.
 *  @author Eric Bousbaa & Ilias Goujgali
 *  @version 1.0
 */
public class ConfigManager {
    private final Logger LOG = Logger.getLogger(ConfigManager.class.getName());

    // Adresses relatives des fichiers de config
    private static final String PATH_TO_CONFIG_FOLDER = "./config/";
    private static final String PATH_TO_CONFIG = PATH_TO_CONFIG_FOLDER + "config.json";
    private static final String PATH_TO_VICTIMS = PATH_TO_CONFIG_FOLDER + "victims.json";
    private static final String PATH_TO_MESSAGES = PATH_TO_CONFIG_FOLDER + "messages.json";

    // Instance du singleton
    private static ConfigManager instance;

    @Getter
    private List<Person> victims = null;

    @Getter
    private List<Person> ccWitnesses = null;

    @Getter
    private List<Message> messages = null;

    @Getter
    private String smtpServerIp;

    @Getter
    private int smtpServerPort;

    @Getter
    private int nbGroups;

    @Getter
    private int nbMembers;

    /**
     * Contructeur, on lit et stocke les fichiers de configs
     * @throws NullPointerException lors qu'une clé est manquante dans les fichiers JSON
     * @throws IOException lors d'un problème de lecture du fichier
     * @throws ClassCastException si le type d'un des attributs JSON n'est pas correcte (eg: un nombre au lieu d'un string)
     */
    private ConfigManager() throws NullPointerException, IOException, ClassCastException {
        try {
            setConfig(PATH_TO_CONFIG);
            victims = getVictimsFromFile(PATH_TO_VICTIMS);
            messages = getMessageFromFile(PATH_TO_MESSAGES);
        } catch (NullPointerException | ClassCastException | JsonException e) {

            LOG.severe("WRONG JSON FORMAT. PLEASE REFER TO THE README");
            throw new InvalidPrankConfiguration(e);
        }
        catch(IOException e){
            LOG.severe("ERROR WHILE READING CONFIG FILE.");
            throw e;
        }
    }

    /**
     * Lit et stocke les paramètres de configuration du fichier config.json
     * @param filename
     * @throws IOException S'il y a un problème lors de la lecture du fichier
     * @throws JsonException S'il y a un problème lors du parsing.
     */
    private void setConfig(String filename) throws JsonException, IOException {
        JsonObject obj = (JsonObject) getJson(filename);
        nbGroups = ((BigDecimal) obj.get("nbGroups")).intValueExact();
        nbMembers = ((BigDecimal) obj.get("nbMembers")).intValueExact();
        if(nbMembers < 3){
            throw new InvalidPrankConfiguration("A GROUP CONTAINS AT LEAST 3 PEOPLE");
        }
        smtpServerIp = (String) obj.get("smtpServerIp");
        smtpServerPort = ((BigDecimal) obj.get("smtpServerPort")).intValueExact();;


        JsonArray jsonAryCCEmails = (JsonArray)  obj.get("witnessesCC");
        if(jsonAryCCEmails.size() > 0) {
            ccWitnesses = new ArrayList<Person>();
            for (Object objEmail : jsonAryCCEmails) {
                ccWitnesses.add(new Person((String) objEmail));
            }
        }
    }


    /**
     *
     * Lit et retourne les message du fichier de configuration
     * @param filename chemin vers le fichier des messages
     * @return Liste de Message qui seront les victimes de notre application
     * @throws IOException Lors d'un problème de lecture du fichier
     * @throws JsonException Lors d'une erreur de formatage
     */
    private List<Message> getMessageFromFile(String filename) throws IOException, JsonException {
        JsonArray jsonMessages = (JsonArray) getJson(filename);
        List<Message> messages = new ArrayList<>();
        for (Object objMessage : jsonMessages) {
            JsonObject jsonMessage = (JsonObject) objMessage;
            String title = (String) jsonMessage.get("title");
            String content = (String) jsonMessage.get("content");
            messages.add(new Message(title, content));
        }
        return messages;
    }


    /**
     * Lit et parse le fichier json passé en paramètre
     * @param filename chemin vers le fichier à parser
     * @return object du fichier json
     * @throws IOException Lors d'une erreur lors de la lecture du fichier
     * @throws JsonException Lors qu'un fichier est mal formé.
     */
    private Object getJson(String filename) throws IOException, JsonException {
        Object parser = null;

        Reader reader = new BufferedReader(new FileReader(filename));
        parser = Jsoner.deserialize(reader);

        reader.close();
        return parser;
    }

    /**
     * Lit et retourne les victimes du fichier de configuration
     * @param filename chemin vers le fichier des victimes
     * @return Liste de Person qui seront les victimes de notre application
     * @throws IOException Lors d'un problème de lecture du fichier
     * @throws JsonException Lors d'une erreur de formatage
     */
    private List<Person> getVictimsFromFile(String filename) throws IOException, JsonException {
        List<Person> people = new ArrayList<>();
        JsonArray jsonVictims = (JsonArray) getJson(filename);
        for (Object objectVictim : jsonVictims) {
            people.add(new Person((String) objectVictim));
        }
        return people;
    }

    /**
     * Méthodes permettant de créer ou recevoir l'instance de la classe.
     * @return ConfigManager l'instance
     * @throws IOException Lors de la création d'un fichier
     */
    static public ConfigManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
}
