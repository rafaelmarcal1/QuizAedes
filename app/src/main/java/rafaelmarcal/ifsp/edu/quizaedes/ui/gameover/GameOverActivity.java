package rafaelmarcal.ifsp.edu.quizaedes.ui.gameover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityGameOverBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class GameOverActivity extends AppCompatActivity {

    private ActivityGameOverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperando a pontuação e moedas da Intent
        int pontuacao = getIntent().getIntExtra("PONTUACAO", 0);
        int moedas = getIntent().getIntExtra("MOEDAS", 0);  // Recupera as moedas

        // Configurando os textos da tela de game over
        binding.tvPontuacaoFinal.setText("Pontuação Final: " + pontuacao);
        binding.tvMoedas.setText("Moedas: " + moedas);  // Atualiza o TextView com as moedas

        // Configurando o botão para jogar novamente
        binding.btnJogarNovamente.setOnClickListener(v -> reiniciarQuiz());

        // Configurando o botão para sair
        binding.btnSair.setOnClickListener(v -> finish());  // Fecha a atividade
    }

    private void reiniciarQuiz() {
        // Reinicia o quiz e retorna para a tela de PerguntasActivity
        Intent intent = new Intent(GameOverActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}
