package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivitySplashBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.main.MainActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carregarSplashScreen();
    }

    private void carregarSplashScreen() {
        // Simula carregamento com uma barra de progresso
        new Thread(() -> {
            for (int progresso = 0; progresso <= 100; progresso++) {
                try {
                    Thread.sleep(30); // Simula um carregamento mais lento
                    int finalProgresso = progresso;
                    runOnUiThread(() -> binding.progressBar.setProgress(finalProgresso));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Após o carregamento, inicia a próxima atividade
            new Handler(Looper.getMainLooper()).post(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}