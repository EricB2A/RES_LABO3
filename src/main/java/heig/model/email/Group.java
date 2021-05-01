package heig.model.email;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Person> members;

    public Group(List<Person> initialMembers){
        members = new ArrayList<Person>();
        members.addAll(initialMembers);
    }

    public Group(){
        members = new ArrayList<>();
    }

    public Group(Group otherGroup){
        this(otherGroup.members);
    }

    public void addMembers(Person newMember){
        if(members.contains(newMember)) return;
        members.add(newMember);
    }


}
