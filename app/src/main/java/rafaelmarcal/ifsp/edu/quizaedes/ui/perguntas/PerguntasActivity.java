package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;

public class PerguntasActivity extends AppCompatActivity {

    private PerguntasViewModel viewModel;
    private TextView tvPergunta;
    private RadioGroup rgOpcoes;
    private Button btnConfirmarResposta;
    private TextView tvFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas);

        tvPergunta = findViewById(R.id.tvPergunta);
        rgOpcoes = findViewById(R.id.rgOpcoes);
        btnConfirmarResposta = findViewById(R.id.btnConfirmarResposta);
        tvFeedback = findViewById(R.id.tvFeedback);

        viewModel = new ViewModelProvider(this).get(PerguntasViewModel.class);

        observarViewModel();
        configurarBotaoConfirmar();
    }

    private void observarViewModel() {
        // Observa a pergunta atual
        viewModel.getPerguntaAtual().observe(this, pergunta -> {
            tvPergunta.setText(pergunta.getEnunciado());
            ((RadioButton) rgOpcoes.getChildAt(0)).setText(pergunta.getOpcoes().get(0));
            ((RadioButton) rgOpcoes.getChildAt(1)).setText(pergunta.getOpcoes().get(1));
            ((RadioButton) rgOpcoes.getChildAt(2)).setText(pergunta.getOpcoes().get(2));
            ((RadioButton) rgOpcoes.getChildAt(3)).setText(pergunta.getOpcoes().get(3));
            rgOpcoes.clearCheck();
        });

        // Observa o resultado da resposta
        viewModel.getRespostaCorreta().observe(this, correta -> {
            if (correta) {
                tvFeedback.setText("Resposta Correta!");
                tvFeedback.setVisibility(TextView.VISIBLE);
            } else {
                tvFeedback.setText("Resposta Errada!");
                tvFeedback.setVisibility(TextView.VISIBLE);
            }
        });

        // Observa o número de erros
        viewModel.getErros().observe(this, erros -> {
            if (erros >= 2) {
                Toast.makeText(this, "Você perdeu o jogo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void configurarBotaoConfirmar() {
        btnConfirmarResposta.setOnClickListener(v -> {
            int idSelecionado = rgOpcoes.getCheckedRadioButtonId();
            if (idSelecionado != -1) {
                int indiceResposta = rgOpcoes.indexOfChild(findViewById(idSelecionado));
                viewModel.verificarResposta(indiceResposta);
            } else {
                Toast.makeText(this, "Por favor, selecione uma resposta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}