package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Collections;
import java.util.List;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.data.repository.PerguntasRepository;

public class PerguntasViewModel extends ViewModel {
    private final PerguntasRepository repository;
    private final MutableLiveData<List<Pergunta>> perguntasLiveData;
    private int perguntaAtualIndex = 0;

    public PerguntasViewModel() {
        repository = new PerguntasRepository();
        perguntasLiveData = repository.getPerguntas();
    }

    public LiveData<List<Pergunta>> getPerguntas() {
        return perguntasLiveData;
    }

    public void embaralharPerguntas() {
        if (perguntasLiveData.getValue() != null) {
            Collections.shuffle(perguntasLiveData.getValue());
        }
    }


    public Pergunta getPerguntaAtual() {
        List<Pergunta> perguntas = perguntasLiveData.getValue();
        return (perguntas != null && !perguntas.isEmpty()) ? perguntas.get(perguntaAtualIndex) : null;
    }

    public void avancarPergunta() {
        if (perguntasLiveData.getValue() != null && perguntaAtualIndex < perguntasLiveData.getValue().size() - 1) {
            perguntaAtualIndex++;
        }
    }

    public void reiniciarQuiz() {
        perguntaAtualIndex = 0;
    }
    }
