package rafaelmarcal.ifsp.edu.quizaedes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<Boolean> registroResultado = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();

    public LiveData<Boolean> getRegistroResultado(){
        return registroResultado;
    }

    public LiveData<String> getErro(){
        return erro;
    }

    public void criarConta(String email, String senha){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                registroResultado.setValue(true);
            } else {
                registroResultado.setValue(false);
                erro.setValue(task.getException() != null ? task.getException().getMessage() : "Erro!");
            }
        });
    }
}