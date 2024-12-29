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
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int nivel = getIntent().getIntExtra("NIVEL", 1);

        // Mostrar o nível no layout (adapte conforme necessário)
        TextView tvNivel = findViewById(R.id.tvNivel);
        tvNivel.setText("Nível " + nivel + "!");

        // Retornar para PerguntasActivity após 3 segundos
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, PerguntasActivity.class);
            startActivity(intent);
            finish(); // Fechar SplashScreen
        }, 3000); // 3 segundos
    }
}