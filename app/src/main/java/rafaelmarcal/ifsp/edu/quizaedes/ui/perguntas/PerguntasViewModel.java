package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.data.repository.PerguntasRepository;

public class PerguntasViewModel extends ViewModel {
    private final PerguntasRepository repository;
    private final MutableLiveData<List<Pergunta>> perguntasLiveData;
    private int perguntaAtualIndex = 0;
    private int pontuacao = 0;
    private final List<Pergunta> perguntasRespondidas = new ArrayList<>(); // Para evitar repetição

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
        if (perguntas != null && !perguntas.isEmpty()) {
            return perguntas.get(perguntaAtualIndex);
        }
        return null;
    }

    public void avancarPergunta() {
        if (perguntasLiveData.getValue() != null) {
            perguntaAtualIndex++;
            if (perguntaAtualIndex >= perguntasLiveData.getValue().size()) {
                perguntaAtualIndex = 0; // Reinicia a lista se acabar
            }
        }
    }

    public void adicionarPontos(int pontos) {
        pontuacao += pontos;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void registrarPerguntaRespondida(Pergunta pergunta) {
        perguntasRespondidas.add(pergunta);
    }

    public void atualizarPerguntasNivel(int nivel) {
        List<Pergunta> todasPerguntas = repository.getPerguntas().getValue();
        if (todasPerguntas != null) {
            List<Pergunta> perguntasNivelAtual = new ArrayList<>();
            for (Pergunta pergunta : todasPerguntas) {
                if (!perguntasRespondidas.contains(pergunta)) {
                    perguntasNivelAtual.add(pergunta);
                }
                if (perguntasNivelAtual.size() == 5) break; // Seleciona 5 perguntas
            }
            perguntasLiveData.setValue(perguntasNivelAtual);
            perguntaAtualIndex = 0; // Reiniciar índice para o novo nível
        }
    }
    }
