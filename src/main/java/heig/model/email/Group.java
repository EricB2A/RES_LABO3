package heig.model.email;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de représenter un groupe de personne modélisé par la classe Person
 *
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */

public class Group {
    private List<Person> members;

    /**
     * Construction d'un groupe avec une liste de personness
     * @param initialMembers liste des membres initiaux du groupe
     */
    public Group(List<Person> initialMembers){
        members = new ArrayList<Person>();
        members.addAll(initialMembers);
    }

    /**
     * Construction d'un groupe vide
     */
    public Group(){
        members = new ArrayList<>();
    }

    /**
     * Constructeur de copie
     * @param otherGroup
     */
    public Group(Group otherGroup){
        this(otherGroup.members);
    }

    /**
     * Ajout d'un membre au groupe
     * @param newMember
     */
    public void addMembers(Person newMember){
        if(members.contains(newMember)) return;
        members.add(newMember);
    }

    /**
     * Retourne le membre à l'index i
     * @param index index du membre
     * @return Person membre
     */
    public Person getMember(int index ) {
        return members.get(index);
    }

    /**
     * Supprime un membre à l'index spécifique
     * @param index index du membre à supprimer 🔫🔫🔫🔫🔫🔫🔫🔫🔫🔫⛵🔪🔪🔪🔪🗡🗡🗡
     * @return
     */
    public Person removeMember(int index){
        return members.remove(index);
    }

    /**
     * Nombre de membres dans le groupes
     * @return le nombre de membres
     */
    public int size(){
        return members.size();
    }

    /**
     * Retourne les membres du groupe sous la forme d'un ArrayList
     * @return Liste des membres du groupes
     */
    public ArrayList<Person> getMembers(){
        return new ArrayList<Person>(members);
    }

}
