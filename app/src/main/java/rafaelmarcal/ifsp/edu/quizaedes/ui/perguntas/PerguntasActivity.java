package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityPerguntasBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.gameover.GameOverActivity;


public class PerguntasActivity extends AppCompatActivity {

    private ActivityPerguntasBinding binding;
    private PerguntasViewModel viewModel;
    private int errosPermitidos = 2;
    private int erros = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerguntasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PerguntasViewModel.class);
        viewModel.getPerguntas().observe(this, perguntas -> {
            if (perguntas != null && !perguntas.isEmpty()) {
                viewModel.embaralharPerguntas();
                exibirPergunta(viewModel.getPerguntaAtual());
            } else {
                Toast.makeText(this, "Erro ao carregar perguntas.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnConfirmarResposta.setOnClickListener(v -> conferirResposta());
    }

    private void exibirPergunta(Pergunta pergunta) {
        if (pergunta != null) {
            binding.tvPergunta.setText(pergunta.getTextoPergunta());
            binding.rbOpcaoA.setText(pergunta.getOpcoes().get(0));
            binding.rbOpcaoB.setText(pergunta.getOpcoes().get(1));
            binding.rbOpcaoC.setText(pergunta.getOpcoes().get(2));
            binding.rbOpcaoD.setText(pergunta.getOpcoes().get(3));
            binding.tvFeedback.setVisibility(View.GONE);
            binding.rgOpcoes.clearCheck();
        }
    }

    private void conferirResposta() {
        Pergunta perguntaAtual = viewModel.getPerguntaAtual();
        if (perguntaAtual != null) {
            int respostaSelecionada = binding.rgOpcoes.indexOfChild(findViewById(binding.rgOpcoes.getCheckedRadioButtonId()));
            if (respostaSelecionada == perguntaAtual.getRespostaCorreta()) {
                binding.tvFeedback.setText("Resposta correta!");
                binding.tvFeedback.setVisibility(View.VISIBLE);
                viewModel.avancarPergunta();
                exibirPergunta(viewModel.getPerguntaAtual());
            } else {
                erros++;
                if (erros > errosPermitidos) {
                    // Redirecionar para a tela de Game Over
                    Intent intent = new Intent(this, GameOverActivity.class);
                    startActivity(intent);
                    finish(); // Finaliza a activity atual
                } else {
                    binding.tvFeedback.setText("Resposta incorreta. Tente novamente.");
                    binding.tvFeedback.setVisibility(View.VISIBLE);
                }
            }
        } else {
            Toast.makeText(this, "Fim do quiz!", Toast.LENGTH_SHORT).show();
            viewModel.reiniciarQuiz();
            erros = 0;
            exibirPergunta(viewModel.getPerguntaAtual());
        }
    }
}