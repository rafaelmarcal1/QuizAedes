package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityVitoriaBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class VitoriaActivity extends AppCompatActivity {

    private ActivityVitoriaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitoriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperando a pontuação e moedas da Intent
        int pontuacao = getIntent().getIntExtra("PONTUACAO", 0);
        int moedas = getIntent().getIntExtra("MOEDAS", 0);  // Recupera as moedas

        // Configurando os textos da tela de vitória
        binding.tvMensagemVitoria.setText("Parabéns! Você venceu!");
        binding.tvPontuacao.setText("Sua pontuação: " + pontuacao);
        binding.tvMoedasVitoria.setText("Moedas: " + moedas);  // Atualiza o TextView com as moedas

        // Configurando o botão para reiniciar o quiz
        binding.btnReiniciarQuiz.setOnClickListener(v -> reiniciarQuiz());
    }

    private void reiniciarQuiz() {
        // Reinicia o quiz e retorna para a tela de PerguntasActivity
        Intent intent = new Intent(VitoriaActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}