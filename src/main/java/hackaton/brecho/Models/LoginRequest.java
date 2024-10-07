package hackaton.brecho.Models;

public class LoginRequest {
    private String email;  // Alterado de username para email
    private String password;

    // Getters e Setters
    public String getEmail() { // Alterado para getEmail
        return email;
    }

    public void setEmail(String email) { // Alterado para setEmail
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
