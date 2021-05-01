package heig.model.prank;

import heig.model.email.Group;
import heig.model.email.Message;
import heig.model.email.Person;

/**
 * Interface qu'un e-mail doit posséder
 *
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */
public interface Email {
    /**
     * Retourne l'expéditeur de l'email
     * @return Person destinataire
     */
    Person getSender();

    /**
     * Retourne le groupe de  destinataires du message
     * @return Group de destinataires
     */
    Group getReceivers();

    /**
     * Retourne le groupe de destinataires CC du message
     * @return Group des destinataires CC
     */
    Group getCCReceivers();

    /**
     * Retourne le Message ( titre & contenu ) de l'email
     * @return Message de l'email
     */
    Message getMessage();
}
