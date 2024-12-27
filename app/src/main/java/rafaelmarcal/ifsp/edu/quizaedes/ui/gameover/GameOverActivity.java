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

        // Pegar a pontuação da Intent
        int pontuacaoFinal = getIntent().getIntExtra("PONTUACAO", 0);
        binding.tvPontuacaoFinal.setText("Pontuação Final: " + pontuacaoFinal);

        binding.btnJogarNovamente.setOnClickListener(v -> {
            // Iniciar a PerguntasActivity novamente
            Intent intent = new Intent(GameOverActivity.this, PerguntasActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSair.setOnClickListener(v -> {
            // Finalizar a aplicação
            finish();
        });
    }
}
