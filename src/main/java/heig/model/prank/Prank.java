package heig.model.prank;

import heig.model.email.Group;
import heig.model.email.Message;
import heig.model.email.Person;
import lombok.Getter;
import lombok.Setter;

public class Prank implements Email {
    @Getter
    @Setter
    private Person sender;

    @Getter
    @Setter
    private Group receivers;

    @Getter
    @Setter
    private Group CCReceivers;

    @Getter
    @Setter
    private Message message;
}
