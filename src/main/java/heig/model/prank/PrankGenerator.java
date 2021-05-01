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


/**
 * Cette clases permet la génération des pranks générant des groupes de personnes aléatoires et leur attribuant un message au hazard
 *
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */
@NoArgsConstructor
public class PrankGenerator {
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    /**
     * Génération d'une liste de prank.
     * @param nbGroups Nombre de groupes à créer (au maximum, pourrait être moins si le nombre de personne minimum ne peut pas être respecté)
     * @param nbMembers Nombre de membre MINIMUM par groupe
     * @param victims Liste des victimes/Person
     * @param ccWitnesses Liste des témoins
     * @param messages Liste des messages
     * @return une liste de prank
     */
    public List<Email> generatePranks(int nbGroups, int nbMembers, List<Person> victims, List<Person> ccWitnesses, List<Message> messages ) {

        // S'il n'y a pas assez de victimes pour faire un groupe, on adapte le nombre de groupes
        // En gardant le même nombre de victimes par groupes.
        // eg:  2 personnes minimum par groupe, 11 victimes. 6 groupes désirés ce qui n'est pas possible
        //      on crée alors 5 groupes au lieu de 6 (un des groupes sera composé de 3 personnes).
        if (victims.size() / nbGroups < nbMembers) {
            LOG.warning("Not enough members");
            // s'il n'y a pas assez de personne pour créer le nombre de groupe désiré avec le nombre de membre minium
            nbGroups = victims.size() / nbMembers;

            // S'il est impossible de respecter le nombre de personne miminum dans un groupe, une erreur est levée.
            if(nbGroups < 1){
                throw new InvalidPrankConfiguration("The number of members for a group exceeds the number of victims emails");
            }
        }

        // On génére des groupes de victimes
        List<Group> groups = generateGroups(nbGroups, victims);
        List<Email> pranks = new ArrayList<>(groups.size());
        Collections.shuffle(messages);

        int indexMessage = 0;

        for (Group group : groups) {
            Prank prank = new Prank();

            // On supprime le 1er membre du groupe pour qu'il devienne le dindon de la farce..Autrement dit, l'expéditeur
            // du prank
            prank.setSender(group.removeMember(0));

            // Les autres membre du groupe seront des destinataires
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

    /**
     * Génération aléatoire de groupe de personnes
     * @param nbGroups nombre de groupes à créer
     * @param people listes des personnes à ajouter aux différents groupes
     * @return une liste de groupe généré aléatoirement.
     */
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
