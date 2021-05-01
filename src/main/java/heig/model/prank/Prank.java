package heig.model.prank;

import heig.model.email.Group;
import heig.model.email.Message;
import heig.model.email.Person;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe repésentant un prank par email. Ce dernier est composé d'un expéditeur, d'un groupe de destinataire, d'un groupe CC
 * et d'un message.
 *
 *
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */
public class Prank implements Email {
    @Getter
    @Setter
    private Person sender; // Expéditeur

    @Getter
    @Setter
    private Group receivers; // Destinataires

    @Getter
    @Setter
    private Group CCReceivers; // Destinataires CC

    @Getter
    @Setter
    private Message message; // Message de l'e-mail (titre & message)
}
