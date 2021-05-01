package heig.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe représantant une personne. Note: pour le moment seule l'adresse e-mail nous intéresse, c'est pour cette raison
 * qu'aucun autre attirbut n'est présent. Il aurait pu être envisable d'ajouter le nom et prénom des personnes afin d'avoir
 * des e-mails un peu plus personnels.
 *
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */
@AllArgsConstructor
public class Person {

    @Getter
    @Setter
    private String email; // Email de la personne
}
