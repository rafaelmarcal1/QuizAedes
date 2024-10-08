package rafaelmarcal.ifsp.edu.quizaedes.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.User;
import rafaelmarcal.ifsp.edu.quizaedes.util.Constants;

public class UserRepository {
    private FirebaseFirestore firestore;

    public UserRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void addUser(String name, String email, String password, OnCompleteListener listener, OnCompleteListener<Void> emailExistsListener) {
        // Verifica se o e-mail já exista na coleção "Usuarios"
        firestore.collection(Constants.USER_COLLECTION)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Verifica se já existe um usuário com e-mail
                            if (!task.getResult().isEmpty()) {
                                // Se e-mail já existe, retorna o listener de falha
                                emailExistsListener.onComplete(null);
                            } else {
                                // E-mail não existe, continua com a criação do usuário
                                User user = new User(name, email, password);
                                firestore.collection(Constants.USER_COLLECTION)
                                        .add(user)
                                        .addOnCompleteListener(listener);
                            }
                        } else {
                            // Se houve um erro na consulta, retorna um erro
                            listener.onComplete(null);
                        }
                    }
                });
    }
}