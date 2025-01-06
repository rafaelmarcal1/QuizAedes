package rafaelmarcal.ifsp.edu.quizaedes.ui.gameover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityGameOverBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.conquistas.ConquistasActivity;
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
        int moedas = getIntent().getIntExtra("MOEDAS", 0); // Recupera as moedas da partida

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

        // Exibe a pontuação e moedas da partida
        binding.tvPontuacao.setText("Pontuação Final: " + pontuacao);
        binding.tvMoedas.setText("Moedas: " + moedas);

        // Atualizando os dados no SharedPreferences
        atualizarDados(pontuacao, moedas);

        // Botões para reiniciar ou sair
        binding.btnJogarNovamente.setOnClickListener(v -> reiniciarQuiz());
        binding.btnSair.setOnClickListener(v -> finish());
        binding.btnVerConquistas.setOnClickListener(v -> abrirConquistas());
    }

    private void atualizarDados(int pontuacao, int moedas) {
        SharedPreferences sharedPreferences = getSharedPreferences("game_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Atualiza o maior nível alcançado
        int maiorNivel = sharedPreferences.getInt("MAIOR_NIVEL", 1);
        if (maiorNivel < getIntent().getIntExtra("MAIOR_NIVEL", 1)) {
            maiorNivel = getIntent().getIntExtra("MAIOR_NIVEL", 1);
        }
        editor.putInt("MAIOR_NIVEL", maiorNivel);

        // Atualiza a pontuação total
        int pontosTotais = sharedPreferences.getInt("PONTUACAO_TOTAL", 0) + pontuacao;
        editor.putInt("PONTUACAO_TOTAL", pontosTotais);

        // Atualiza as moedas totais
        int moedasTotais = sharedPreferences.getInt("MOEDAS_TOTAL", 0) + moedas;
        editor.putInt("MOEDAS_TOTAL", moedasTotais);

        editor.apply();
    }

    private void reiniciarQuiz() {
        Intent intent = new Intent(GameOverActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirConquistas() {
        Intent intent = new Intent(GameOverActivity.this, ConquistasActivity.class);
        startActivity(intent);
    }
}
