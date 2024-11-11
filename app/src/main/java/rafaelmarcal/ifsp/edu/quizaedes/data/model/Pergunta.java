package rafaelmarcal.ifsp.edu.quizaedes.data.model;

import java.util.List;

public class Pergunta {
    private String pergunta;
    private List<String> opcoes;
    private int respostaCorreta;

    // Construtor vazio necessário para o Firebase
    public Pergunta() {}

    public Pergunta(String pergunta, List<String> opcoes, int respostaCorreta) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    // Getters e setters
    public String getPergunta() { return pergunta; }
    public List<String> getOpcoes() { return opcoes; }
    public int getRespostaCorreta() { return respostaCorreta; }
}
