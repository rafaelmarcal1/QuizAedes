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
    private final MutableLiveData<Boolean> quizTerminadoLiveData = new MutableLiveData<>(false);
    private int perguntaAtualIndex = 0;
    private int pontuacao = 0;
    private int moedas = 0; // Saldo inicial de moedas
    private final List<Pergunta> perguntasRespondidas = new ArrayList<>(); // Para evitar repetição
    private int respostasCorretas = 0; // Contador de respostas corretas
    private int nivel = 1; // Nível inicial
    private final MutableLiveData<Integer> moedasLiveData = new MutableLiveData<>(moedas); // LiveData para moedas
    private final MutableLiveData<Integer> nivelLiveData = new MutableLiveData<>();

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

    public LiveData<Integer> getNivelLiveData() {
        return nivelLiveData;
    }

    // Método para adicionar moedas
    public void adicionarMoedas(int quantidade) {
        moedas += quantidade;
        if (moedas < 0) {
            moedas = 0; // Garantir que as moedas não fiquem negativas
        }
        moedasLiveData.setValue(moedas);
    }

    // Getter para LiveData de moedas
    public LiveData<Integer> getMoedasLiveData() {
        return moedasLiveData;
    }


    // Método para obter o saldo atual de moedas
    public int getMoedas() {
        return moedas;
    }

    public int getNumeroPerguntaAtual() {
        return perguntaAtualIndex + 1; // Como o índice começa de 0, somamos 1 para exibir "Pergunta 1", "Pergunta 2", etc.
    }

    public int getPerguntaAtualIndex() {
        return perguntaAtualIndex;  // Retorna o índice da pergunta atual (de 0 a 8)
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
        if (pontuacao < 0) {
            pontuacao = 0; // Evitar que a pontuação seja negativa
        }
    }

    public void embaralharPerguntas() {
        if (perguntasLiveData.getValue() != null) {
            Collections.shuffle(perguntasLiveData.getValue());
        }
    }

    public void verificarVitoria() {
        if (respostasCorretas >= 9) {
            quizTerminadoLiveData.setValue(true);  // Finaliza o quiz
        }
    }

    // Incrementar o nível
    public void incrementarNivel() {
        respostasCorretas++;
        if (respostasCorretas % 3 == 0) { // A cada 3 respostas corretas
            nivel++;
            nivelLiveData.setValue(nivel); // Notificar a alteração do nível
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
        moedas = 0; // Resetar o saldo de moedas
    }

}