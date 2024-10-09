package rafaelmarcal.ifsp.edu.quizaedes.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private MutableLiveData<Boolean> _loginResultado = new MutableLiveData<>();
    public LiveData<Boolean> loginResultado = _loginResultado;
    public LiveData<Boolean> getLoginResultado(){
        return loginResultado;
    }

    private MutableLiveData<String> _erro = new MutableLiveData<>();
    public LiveData<String> getErro(){
        return _erro;
    }

    public void fazerLogin(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _loginResultado.setValue(true);
                    } else {
                        _loginResultado.setValue(false);
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            _erro.setValue("Usuário não encontrado");
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            _erro.setValue("Senha incorreta");
                        } else {
                            _erro.setValue(task.getException() != null ? task.getException().getMessage() : "Erro desconhecido");
                        }
                    }
                });
    }
}
