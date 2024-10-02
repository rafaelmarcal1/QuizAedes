package rafaelmarcal.ifsp.edu.quizaedes.data.model;

public class User {
    private String userId;
    private String email;
    private String password;

    //Construtor vazio requirido para Firebase
    public User(){

    }

    public User(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password; // Considere usar Hash em vez de texto simples
    }

    //Getters e Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
