package rafaelmarcal.ifsp.edu.quizaedes.data.model;

public class User {
    private String username;
    private String email;
    private String password;

    //Construtor vazio requirido para Firebase
    public User() {

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password; // Considere usar Hash em vez de texto simples
    }

    //Getters e Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
