package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class VitoriaActivity extends AppCompatActivity {

    private TextView tvMensagemVitoria;
    private TextView tvPontuacao;
    private Button btnReiniciarQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitoria);

        tvMensagemVitoria = findViewById(R.id.tvMensagemVitoria);
        tvPontuacao = findViewById(R.id.tvPontuacao);
        btnReiniciarQuiz = findViewById(R.id.btnReiniciarQuiz);

        // Recuperando a pontuação da Intent
        int pontuacao = getIntent().getIntExtra("PONTUACAO", 0);
        tvMensagemVitoria.setText("Parabéns! Você venceu!");
        tvPontuacao.setText("Sua pontuação: " + pontuacao);

        // Ao clicar no botão, reinicia o quiz
        btnReiniciarQuiz.setOnClickListener(v -> reiniciarQuiz());
    }

    private void reiniciarQuiz() {
        // Reinicia o quiz e retorna para a tela de PerguntasActivity
        Intent intent = new Intent(VitoriaActivity.this, PerguntasActivity.class);
        startActivity(intent);
        finish();
    }
}