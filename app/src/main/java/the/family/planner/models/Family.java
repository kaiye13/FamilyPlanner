package the.family.planner.models;

import java.util.List;

public class Family {
    private String name;
    private String family_id;
    private List<User> members;


    public Family(String name, String family_id, List<User> members) {
        this.name = name;
        this.family_id = family_id;
        this.members = members;
    }

    public Family() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Family{" +
                "name='" + name + '\'' +
                ", family_id='" + family_id + '\'' +
                ", members=" + members +
                '}';
    }
}
