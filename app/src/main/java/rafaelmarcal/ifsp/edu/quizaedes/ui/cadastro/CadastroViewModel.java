package rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


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

    public void criarConta(String nome, String email, String senha) {
//
        repository.addUser(nome, email, senha, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    registroResultado.setValue(true); //
                } else {
                    erro.setValue("Falha ao criar conta. Tente novamente.");
                }
            }
        }, task -> erro.setValue("O e-mail já está cadastrado."));
    }
}