package rafaelmarcal.ifsp.edu.quizaedes.data.model;

import java.util.List;

public class Pergunta {
    private String enunciado;
    private List<String> opcoes;
    private int respostaCorreta; // índice da resposta correta na lista de opções

    public Pergunta(String enunciado, List<String> opcoes, int respostaCorreta) {
        this.enunciado = enunciado;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }

}
