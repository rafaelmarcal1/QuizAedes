package rafaelmarcal.ifsp.edu.quizaedes.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.User;
import rafaelmarcal.ifsp.edu.quizaedes.util.Constants;

public class UserRepository {
    private FirebaseFirestore firestore;

    public UserRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void addUser(String name, String email, String password, OnCompleteListener listener) {
        // Criação de um novo documento
        /*
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("name", name);
        usuario.put("email", email);
        usuario.put("password", password);

        // Adiciona usuário à coleção com id aleatório
        firestore.collection("Usuarios")
                .add(usuario)
                .addOnCompleteListener(listener);
         */


        User user = new User(name, email, password);
//        firestore.collection(Constants.USER_COLLECTION)
//                .document(user.getEmail())
//                .set(user)
//                .addOnCompleteListener(listener);
            firestore.collection(Constants.USER_COLLECTION)
                    .add(user)
                    .addOnCompleteListener(listener);


    }

}
