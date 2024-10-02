package rafaelmarcal.ifsp.edu.quizaedes.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private FirebaseFirestore firestore;

    public UserRepository(){
        firestore = FirebaseFirestore.getInstance();
    }

    public void addUser(String name, String email, String password, OnCompleteListener<DocumentReference> listener) {
        // Criação de um novo documento
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("name", name);
        usuario.put("email", email);
        usuario.put("password", password);

        // Adiciona usuário à coleção com id aleatório
        firestore.collection("Usuarios")
                .add(usuario)
                .addOnCompleteListener(listener);
    }

}
