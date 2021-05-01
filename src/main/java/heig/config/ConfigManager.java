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

public class ConfigManager {
    private final Logger LOG = Logger.getLogger(ConfigManager.class.getName());
    private static final String PATH_TO_CONFIG_FOLDER = "./config/";
    private static final String PATH_TO_CONFIG = PATH_TO_CONFIG_FOLDER + "config.json";
    private static final String PATH_TO_VICTIMS = PATH_TO_CONFIG_FOLDER + "victims.json";
    private static final String PATH_TO_MESSAGES = PATH_TO_CONFIG_FOLDER + "messages.json";

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

    private void setConfig(String filename) throws IOException, JsonException {
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


    private Object getJson(String filename) throws IOException, JsonException {
        Object parser = null;

        // create a reader
        Reader reader = new BufferedReader(new FileReader(filename));

        // create parser
        parser = Jsoner.deserialize(reader);

        //close reader
        reader.close();
        return parser;
    }

    private List<Person> getVictimsFromFile(String filename) throws IOException, JsonException {
        List<Person> people = new ArrayList<>();
        JsonArray jsonVictims = (JsonArray) getJson(filename);
        for (Object objectVictim : jsonVictims) {
            people.add(new Person((String) objectVictim));
        }
        return people;
    }

    static public ConfigManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
}
