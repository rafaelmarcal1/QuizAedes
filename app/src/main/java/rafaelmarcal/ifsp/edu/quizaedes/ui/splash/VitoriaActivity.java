package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityVitoriaBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class VitoriaActivity extends AppCompatActivity {

    private ActivityVitoriaBinding binding;

    // Definindo as chaves para SharedPreferences
    private static final String PREFS_NAME = "UserStatsPrefs";
    private static final String KEY_PONTUACAO_TOTAL = "pontuacao_total";
    private static final String KEY_MAIOR_NIVEL = "maior_nivel";
    private static final String KEY_MOEDAS_TOTAL = "moedas_total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitoriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperando a pontuação, moedas e nível da Intent
        int pontuacao = getIntent().getIntExtra("PONTUACAO", 0);
        int moedas = getIntent().getIntExtra("MOEDAS", 0);  // Recupera as moedas
        int nivelAtual = getIntent().getIntExtra("NIVEL", 1);  // Recupera o nível atual

        // Exibindo as informações na tela de vitória
        binding.tvMensagemVitoria.setText("Parabéns! Você venceu!");
        binding.tvPontuacao.setText("Sua pontuação: " + pontuacao);
        binding.tvMoedasVitoria.setText("Moedas: " + moedas);

        // Atualizando as estatísticas
        atualizarEstatisticas(pontuacao, moedas, nivelAtual);

        // Configurando o botão para reiniciar o quiz
        binding.btnReiniciarQuiz.setOnClickListener(v -> reiniciarQuiz());
    }

    private void atualizarEstatisticas(int pontuacao, int moedas, int nivelAtual) {
        // Recuperando as preferências
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Recuperando os valores anteriores
        int pontuacaoTotal = prefs.getInt(KEY_PONTUACAO_TOTAL, 0);
        int moedasTotal = prefs.getInt(KEY_MOEDAS_TOTAL, 0);
        int maiorNivel = prefs.getInt(KEY_MAIOR_NIVEL, 0);

        // Atualizando as pontuações e moedas
        pontuacaoTotal += pontuacao;
        moedasTotal += moedas;

        // Verificando o maior nível alcançado
        if (nivelAtual > maiorNivel) {
            maiorNivel = nivelAtual;
        }

        // Salvando as novas estatísticas
        editor.putInt(KEY_PONTUACAO_TOTAL, pontuacaoTotal);
        editor.putInt(KEY_MOEDAS_TOTAL, moedasTotal);
        editor.putInt(KEY_MAIOR_NIVEL, maiorNivel);
        editor.apply();

        // Exibindo um toast para confirmar
        Toast.makeText(this, "Estatísticas Atualizadas!", Toast.LENGTH_SHORT).show();
    }

    private void reiniciarQuiz() {
        // Reinicia o quiz e retorna para a tela de PerguntasActivity
        Intent intent = new Intent(VitoriaActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}