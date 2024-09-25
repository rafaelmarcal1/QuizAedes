package rafaelmarcal.ifsp.edu.quizaedes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private MutableLiveData<Boolean> loginResultado = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();

    public LiveData<Boolean> getLoginResultado(){
        return loginResultado;
    }

    public LiveData<String> getErro(){
        return erro;
    }

    public void fazerLogin(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginResultado.setValue(true);
                    } else {
                        loginResultado.setValue(false);
                        erro.setValue(task.getException() != null ? task.getException().getMessage() : "Erro desconhecido");
                    }
                });
    }
}