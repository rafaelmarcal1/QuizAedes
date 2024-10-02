package rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirestoreKt;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.remote.FirestoreChannel;

import rafaelmarcal.ifsp.edu.quizaedes.data.repository.UserRepository;

public class CadastroViewModel extends ViewModel {
    private UserRepository repository = new UserRepository();


    //private FirebaseAuth auth = FirebaseAuth.getInstance();
    // https://firebase.google.com/docs/firestore/quickstart?hl=pt-br#java


    private MutableLiveData<Boolean> registroResultado = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();

    public LiveData<Boolean> getRegistroResultado(){
        return registroResultado;
    }

    public LiveData<String> getErro(){
        return erro;
    }

    public void criarConta(String nome, String email, String senha){
//        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
//            if (task.isSuccessful()){
//                registroResultado.setValue(true);
//            } else {
//                registroResultado.setValue(false);
//                erro.setValue(task.getException() != null ? task.getException().getMessage() : "Erro!");
//            }
//        });

        repository.addUser(nome, email, senha, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    registroResultado.setValue(true);
                }
            }
        });
    }
}