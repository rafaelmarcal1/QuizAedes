package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.data.repository.PerguntasRepository;
import rafaelmarcal.ifsp.edu.quizaedes.ui.gameover.GameOverActivity;

public class PerguntasViewModel extends ViewModel {
    private final PerguntasRepository repository;
    private final MutableLiveData<List<Pergunta>> perguntasLiveData;
    private final MutableLiveData<Boolean> quizTerminadoLiveData = new MutableLiveData<>(false);
    private int perguntaAtualIndex = 0;
    private int pontuacao = 0;
    private final List<Pergunta> perguntasRespondidas = new ArrayList<>(); // Para evitar repetição
    private int respostasCorretas = 0; // Contador de respostas corretas
    private int nivel = 1; // Nível inicial

    public PerguntasViewModel() {
        repository = new PerguntasRepository();
        perguntasLiveData = repository.getPerguntas();
    }

    public LiveData<List<Pergunta>> getPerguntas() {
        return perguntasLiveData;
    }

    public LiveData<Boolean> getQuizTerminado() {
        return quizTerminadoLiveData;
    }

    // Avançar para a próxima pergunta
    public void avancarPergunta() {
        List<Pergunta> perguntas = perguntasLiveData.getValue();
        if (perguntas != null && !perguntas.isEmpty()) {
            perguntaAtualIndex++;
            if (perguntaAtualIndex >= perguntas.size()) {
                // Quando todas as perguntas forem respondidas, finaliza o quiz
                quizTerminadoLiveData.setValue(true);
            }
        }
    }

    // Obter a pergunta atual, excluindo as já respondidas
    public Pergunta getPerguntaAtual() {
        List<Pergunta> perguntas = perguntasLiveData.getValue();
        if (perguntas != null && !perguntas.isEmpty()) {
            // Pega a próxima pergunta que ainda não foi respondida
            while (perguntaAtualIndex < perguntas.size() && perguntasRespondidas.contains(perguntas.get(perguntaAtualIndex))) {
                perguntaAtualIndex++;
            }

            // Caso tenha acabado as perguntas, retornamos null
            if (perguntaAtualIndex >= perguntas.size()) {
                return null;
            }

            return perguntas.get(perguntaAtualIndex);
        }
        return null;
    }

    // Adicionar pontos ao usuário
    public void adicionarPontos(int pontos) {
        pontuacao += pontos;
    }

    public void embaralharPerguntas() {
        if (perguntasLiveData.getValue() != null) {
            Collections.shuffle(perguntasLiveData.getValue());
        }
    }

    // Incrementar o nível
    public void incrementarNivel() {
        respostasCorretas++;
        if (respostasCorretas % 2 == 0) { // A cada 2 respostas corretas
            nivel++;
        }
    }

    // Getters e setters para pontuação
    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getNivel() {
        return nivel;
    }

    // Registrar perguntas respondidas para evitar repetir
    public void registrarPerguntaRespondida(Pergunta pergunta) {
        perguntasRespondidas.add(pergunta);
    }

    // Resetar o quiz
    public void reiniciarQuiz() {
        perguntaAtualIndex = 0;
        perguntasRespondidas.clear();
        quizTerminadoLiveData.setValue(false);
        respostasCorretas = 0;
        nivel = 1; // Resetar o nível
    }
}