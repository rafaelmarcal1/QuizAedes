package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivitySplashScreenBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int TEMPO_SPLASH = 2000; // Tempo de exibição da splash screen (2 segundos)
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflando o layout com ViewBinding
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperando o nível e a pontuação passados pela PerguntasActivity
        int nivelAtual = getIntent().getIntExtra("NIVEL", 1); // Padrão para o nível 1
        int pontuacao = getIntent().getIntExtra("PONTUACAO", 0); // Padrão para 0 pontos

        // Exibir o nível na Splash Screen
        binding.tvNivel.setText("Nível: " + nivelAtual);

        // Usando Handler para esperar o tempo da splash screen antes de continuar
        new Handler().postDelayed(() -> {
            // Após o tempo da splash screen, iniciar a PerguntasActivity passando o nível e a pontuação
            Intent intent = new Intent(SplashScreenActivity.this, PerguntasActivity.class);
            intent.putExtra("NIVEL", nivelAtual); // Passar o nível atual
            intent.putExtra("PONTUACAO", getIntent().getIntExtra("PONTUACAO", 0)); // Recupera a pontuação
            startActivity(intent);
            finish(); // Finaliza a SplashScreenActivity para não voltar a ela
        }, TEMPO_SPLASH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Garantir que o binding seja limpo quando a Activity for destruída
    }
}