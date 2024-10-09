package rafaelmarcal.ifsp.edu.quizaedes.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.concurrent.CompletableFuture;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.User;
import rafaelmarcal.ifsp.edu.quizaedes.data.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository repository = new UserRepository();

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
        CompletableFuture<User> future = repository.findByEmail(email);

        future.thenAccept(user -> {
            if (user.getPassword().equals(senha)) {
                _loginResultado.setValue(true);
            } else {
                _loginResultado.setValue(false);
                _erro.setValue("Senha incorreta.");
            }
        }).exceptionally(ex -> {
            _loginResultado.setValue(false);
            _erro.setValue("Usuário não enconrtado.");
            return null;
        });
    }
}
