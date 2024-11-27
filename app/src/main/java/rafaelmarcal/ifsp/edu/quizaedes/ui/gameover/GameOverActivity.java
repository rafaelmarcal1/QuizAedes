package rafaelmarcal.ifsp.edu.quizaedes.ui.gameover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.ui.inicio.InicioActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Receber a pontuação final da Intent
        int finalScore = getIntent().getIntExtra("FINAL_SCORE", 0);

        // Atualizar o texto da pontuação
        TextView tvFinalScore = findViewById(R.id.tvFinalScore);
        tvFinalScore.setText("Sua Pontuação: " + finalScore);

        // Botão Reiniciar
        findViewById(R.id.btnRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a tela principal ou reiniciar o jogo
                Intent intent = new Intent(GameOverActivity.this, PerguntasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Botão Sair
        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, InicioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}