package heig.model.prank;

import heig.exception.InvalidPrankConfiguration;
import heig.model.email.Group;
import heig.model.email.Message;
import heig.model.email.Person;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@NoArgsConstructor
public class PrankGenerator {
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    public List<Email> generatePranks(int nbGroups, int nbMembers, List<Person> victims, List<Person> ccWitnesses, List<Message> messages ) {

        // TODO check config : throw des exceptions si la taille ne sont pas ok
        // S'il n'y a pas assez de victims pour faire un groupe, on adapte la taille des groupes
        // En gardant le même nombre de victimes par groupes.
        if (victims.size() / nbGroups < nbMembers) {
            LOG.warning("Not enough members");
            nbGroups = victims.size() / nbMembers;
            if(nbGroups < 1){
                throw new InvalidPrankConfiguration("The number of members for a group exceeds the number of victims emails");
            }
        }

        List<Group> groups = generateGroups(nbGroups, victims);
        List<Email> pranks = new ArrayList<>(groups.size());
        Collections.shuffle(messages);

        int indexMessage = 0;
        for (Group group : groups) {
            Prank prank = new Prank();

            // TODO voir si nécessaire de faire un random pour le sender sachant qu'il y a déjà un random pour générer les groupes
            // TODO voir si ok de supprimer l'envoyer du groupe
            prank.setSender(group.removeMember(0));
            prank.setReceivers(group);
            prank.setMessage(messages.get(indexMessage));

            // On ajoute les CC s'il en existe
            if(ccWitnesses != null){
                prank.setCCReceivers(new Group(ccWitnesses));
            }

            indexMessage = (indexMessage + 1) % messages.size();
            pranks.add(prank);
        }
        return pranks;
    }

    private List<Group> generateGroups(int nbGroups, List<Person> people) {
        List<Group> groups = new ArrayList<>();
        List<Person> availablePeople = new LinkedList<>(people);
        Collections.shuffle(availablePeople);
        for (int i = 0; i < nbGroups; ++i) {
            groups.add(new Group());
        }
        int indexGroup = 0;
        while (availablePeople.size() > 0) {
            groups.get(indexGroup).addMembers(availablePeople.remove(0));
            indexGroup = (indexGroup + 1) % nbGroups;
        }
        return groups;
    }
}
