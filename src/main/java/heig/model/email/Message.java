package heig.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe représentant un message composé d'un titre et d'un contenu du message
 *
 *  @author Eric Bousbaa & Ilias Goujgali
 *  @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Getter
    @Setter
    private String subject; // Titre

    @Getter
    @Setter
    private String content; // Contenu du message
}
