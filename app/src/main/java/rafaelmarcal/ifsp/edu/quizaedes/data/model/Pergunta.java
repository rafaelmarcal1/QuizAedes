package rafaelmarcal.ifsp.edu.quizaedes.data.model;

import java.util.Collections;
import java.util.List;

public class Pergunta {
    private String textoPergunta;
    private List<String> opcoes;
    private int respostaCorreta;

    // Construtor vazio necessário para o Firebase
    public Pergunta() {}

    public Pergunta(String pergunta, List<String> opcoes, int respostaCorreta) {
        this.textoPergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    // Getters e setters
    public String getTextoPergunta() { return textoPergunta; }
    public List<String> getOpcoes() { return opcoes; }
    public int getRespostaCorreta() { return respostaCorreta; }

    // Método para embaralhar as opções
    public void embaralharOpcoes() {
        String respostaCorretaTexto = opcoes.get(respostaCorreta);
        Collections.shuffle(opcoes);
        respostaCorreta = opcoes.indexOf(respostaCorretaTexto);
    }
}
