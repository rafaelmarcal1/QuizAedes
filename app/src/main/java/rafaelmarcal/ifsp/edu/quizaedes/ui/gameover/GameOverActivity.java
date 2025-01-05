package rafaelmarcal.ifsp.edu.quizaedes.ui.gameover;

import android.content.Intent;
import android.content.SharedPreferences;
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

        // Recuperando se o jogador venceu ou perdeu
        boolean venceu = getIntent().getBooleanExtra("VITORIA", false);

        // Atualizando o texto com base na vitória ou derrota
        if (venceu) {
            // Mensagem de Vitória
            binding.tvPontuacaoFinal.setText("Parabéns! Você venceu!");
            binding.tvPontuacaoFinal.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            // Mensagem de Game Over
            binding.tvPontuacaoFinal.setText("Game Over! Tente novamente!");
            binding.tvPontuacaoFinal.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        // Exibe a pontuação final
        binding.tvMoedas.setText("Moedas: " + moedas);

        // Exibe a pontuação final
        binding.tvPontuacao.setText("Pontuação Final: " + pontuacao);

        // Recupera o maior nível alcançado da SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("game_data", MODE_PRIVATE);
        int maiorNivel = sharedPreferences.getInt("MAIOR_NIVEL", 1); // 1 é o valor inicial

        // Atualizando o maior nível
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int pontosTotais = sharedPreferences.getInt("PONTUACAO_TOTAL", 0) + pontuacao;
        editor.putInt("PONTUACAO_TOTAL", pontosTotais);

        if (maiorNivel < getIntent().getIntExtra("MAIOR_NIVEL", 1)) {
            maiorNivel = getIntent().getIntExtra("MAIOR_NIVEL", 1);  // Atualiza o maior nível
        }
        editor.putInt("MAIOR_NIVEL", maiorNivel);

        int moedasTotais = sharedPreferences.getInt("MOEDAS_TOTAL", 0) + moedas;
        editor.putInt("MOEDAS_TOTAL", moedasTotais);
        editor.apply();

        // Botões para reiniciar ou sair
        binding.btnJogarNovamente.setOnClickListener(v -> reiniciarQuiz());
        binding.btnSair.setOnClickListener(v -> finish());
    }


    private void reiniciarQuiz() {
        // Reinicia o quiz e retorna para a tela de PerguntasActivity
        Intent intent = new Intent(GameOverActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}
