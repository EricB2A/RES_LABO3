package heig.model.prank;

import heig.model.email.Group;
import heig.model.email.Message;
import heig.model.email.Person;

public interface Email {
    public Person getSender();
    public Group getReceivers();
    public Group getCCReceivers();
    public Message getMessage();
}
