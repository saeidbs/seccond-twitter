package ir.mkp.second_twitter.module;

import ir.mkp.second_twitter.utility.Values;

public class User {
    public enum  Role{
        NORMAL,
        ADMIN,
        ANALYSER;

        @Override
        public String toString() {
            switch (this){
                case ADMIN:
                    return "admin";
                case NORMAL:
                    return "normal";
                case ANALYSER:
                    return "analyser";
            }
            return super.toString();
        }

        public static Role getRole(String role){
            if (role.equals(ADMIN.toString()))
                return ADMIN;
            if (role.equals(ANALYSER.toString()))
                return ANALYSER;
            return NORMAL;
        }
    }

    private long id;
    private String email;
    private String username;
    private String password;
    private Role role;
    private String bio;
    private String secureQuestion;
    private String secureAnswer;
    public User(){
    }

    public User( String email, String username, String password, Role role, String bio, String secureQuestion,String secureAnswer) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.bio = bio;
        this.secureQuestion = secureQuestion;
        this.secureAnswer = secureAnswer;
    }

    public User(long id) {
        this.id = id;
        User temp = Values.dataManager.getUserDAO().getUserById(id);
        this.bio = temp.bio;
        this.email = temp.email;
        this.password = temp.password;
        this.role = temp.role;
        this.username = temp.username;
        this.secureAnswer = temp.secureAnswer;
        this.secureQuestion = temp.secureQuestion;

    }

    public User(long id, String email, String username, String password, Role role, String bio, String secureQuestion, String secureAnswer) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.bio = bio;
        this.secureQuestion = secureQuestion;
        this.secureAnswer = secureAnswer;
    }

    public Role getRole() {
        return role;
    }

    public String getBio() {
        return bio;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecureQuestion() {
        return secureQuestion;
    }

    public String getSecureAnswer() {
        return secureAnswer;
    }
}
