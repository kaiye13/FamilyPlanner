package the.family.planner.models;

public class User {

    private String name;
    private String user_id;
    private String family_id;
    private String birthdate;
    private String email;
    private int age;



    public User() {

    }

    public String getFamily_id() {
        return family_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String user_id, String family_id, String birthdate, String email, int age) {
        this.name = name;
        this.user_id = user_id;
        this.family_id = family_id;
        this.birthdate = birthdate;
        this.email = email;
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFamily_id(String family_id){
        this.family_id = family_id;
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
                ", family_id='" + family_id + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
