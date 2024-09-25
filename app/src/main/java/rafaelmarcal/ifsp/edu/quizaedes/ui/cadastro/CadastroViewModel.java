package rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirestoreKt;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.remote.FirestoreChannel;

public class CadastroViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    // https://firebase.google.com/docs/firestore/quickstart?hl=pt-br#java


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