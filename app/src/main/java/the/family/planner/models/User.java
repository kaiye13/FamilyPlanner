package the.family.planner.models;

public class User {

    private String name;
    private String user_id;
    private String famimy_id;

    public User(String name, String user_id, String family_id) {
        this.name = name;
        this.user_id = user_id;
        this.famimy_id = family_id;

    }

    public User() {

    }

    public String getFamimy_id(){
        return famimy_id;
    }

    public void setFamily_id(String family_id){
        this.famimy_id = famimy_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", family_id='" + famimy_id + '\'' +
                '}';
    }
}
