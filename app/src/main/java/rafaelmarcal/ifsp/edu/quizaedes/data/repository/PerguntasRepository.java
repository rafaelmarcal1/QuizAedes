package rafaelmarcal.ifsp.edu.quizaedes.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.util.Constants;

public class PerguntasRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Pergunta>> perguntasLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Pergunta>> getPerguntas() {
        db.collection(Constants.QUESTIONS_COLLECTION)
                // Usando a constante aqui
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Pergunta> perguntas = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pergunta pergunta = document.toObject(Pergunta.class);
                            perguntas.add(pergunta);
                        }
                        perguntasLiveData.setValue(perguntas);
                    } else {
                        perguntasLiveData.setValue(null);
                    }
                });
        return perguntasLiveData;
    }
}
