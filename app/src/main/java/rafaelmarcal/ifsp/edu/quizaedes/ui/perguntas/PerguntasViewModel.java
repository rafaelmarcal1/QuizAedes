package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;

public class PerguntasViewModel extends ViewModel {
    private List<Pergunta> listaPerguntas;
    private MutableLiveData<Pergunta> perguntaAtual = new MutableLiveData<>();
    private MutableLiveData<Boolean> respostaCorreta = new MutableLiveData<>();
    private MutableLiveData<Integer> erros = new MutableLiveData<>(0);
    private int indicePerguntaAtual = 0;
    private int maxErros = 2;

    public PerguntasViewModel() {
        // Inicialize a lista de perguntas
        listaPerguntas = criarPerguntas();
        // Carrega a primeira pergunta
        proximaPergunta();
    }

    public LiveData<Pergunta> getPerguntaAtual() {
        return perguntaAtual;
    }

    public LiveData<Boolean> getRespostaCorreta() {
        return respostaCorreta;
    }

    public LiveData<Integer> getErros() {
        return erros;
    }

    public void verificarResposta(int indiceResposta) {
        if (indiceResposta == perguntaAtual.getValue().getRespostaCorreta()) {
            respostaCorreta.setValue(true);
            proximaPergunta();
        } else {
            respostaCorreta.setValue(false);
            int errosAtuais = erros.getValue();
            if (errosAtuais < maxErros) {
                erros.setValue(errosAtuais + 1);
            }
        }
    }

    private void proximaPergunta() {
        if (indicePerguntaAtual < listaPerguntas.size()) {
            perguntaAtual.setValue(listaPerguntas.get(indicePerguntaAtual));
            indicePerguntaAtual++;
        } else {
            // Lógica para finalizar o jogo ou reiniciar
        }
    }

    private List<Pergunta> criarPerguntas() {
        List<Pergunta> perguntas = new ArrayList<>();

        perguntas.add(new Pergunta("Qual o vetor do vírus da dengue?",
                List.of("Aedes aegypti", "Culex", "Anopheles", "Culex quinquefasciatus"), 0));
        perguntas.add(new Pergunta("Qual o sintoma mais comum da dengue?",
                List.of("Febre alta", "Diarreia", "Falta de ar", "Tontura"), 0));
        perguntas.add(new Pergunta("Qual o nome da doença causada pelo Aedes aegypti além da dengue?",
                List.of("Zika", "Malária", "Febre tifoide", "Gripe"), 0));
        perguntas.add(new Pergunta("Qual medida preventiva é mais eficaz contra o Aedes aegypti?",
                List.of("Eliminar água parada", "Usar mosquiteiros", "Tomar vacina", "Evitar áreas rurais"), 0));
        perguntas.add(new Pergunta("Qual é o período mais ativo do Aedes aegypti?",
                List.of("Início da manhã e final da tarde", "Durante a noite", "Ao meio-dia", "24 horas por dia"), 0));
        perguntas.add(new Pergunta("Quanto tempo dura o ciclo de vida do Aedes aegypti?",
                List.of("7 a 10 dias", "1 mês", "3 dias", "2 semanas"), 0));
        perguntas.add(new Pergunta("Em que tipo de ambiente o Aedes aegypti se reproduz?",
                List.of("Água parada", "Solo seco", "Água corrente", "Florestas densas"), 0));
        perguntas.add(new Pergunta("Qual órgão do corpo humano é mais afetado pela dengue grave?",
                List.of("Fígado", "Pulmões", "Coração", "Intestinos"), 0));
        perguntas.add(new Pergunta("Qual é o método mais eficiente de diagnosticar a dengue?",
                List.of("Exame de sangue", "Raio-X", "Exame de urina", "Teste de alergia"), 0));
        perguntas.add(new Pergunta("Em qual continente o Aedes aegypti é mais prevalente?",
                List.of("América", "Ásia", "África", "Europa"), 0));

        // Embaralhar a lista de perguntas
        Collections.shuffle(perguntas);

        return perguntas;
    }

}
