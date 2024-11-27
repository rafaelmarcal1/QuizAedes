package rafaelmarcal.ifsp.edu.quizaedes.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;

public class CountdownActivity extends AppCompatActivity {

    private TextView tvCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        tvCountdown = findViewById(R.id.tvCountdown);
        TextView tvMessage = findViewById(R.id.tvMessage);

        iniciarContagemRegressiva();
    }

    private void iniciarContagemRegressiva() {
        Animation pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int segundosRestantes = (int) millisUntilFinished / 1000;
                tvCountdown.setText(String.valueOf(segundosRestantes));

                // Aplicar animação ao TextView
                tvCountdown.startAnimation(pulseAnimation);
            }

            @Override
            public void onFinish() {
                // Navegar para a tela do quiz
                Intent intent = new Intent(CountdownActivity.this, PerguntasActivity.class);
                startActivity(intent);
                finish(); // Finalizar a tela de contagem regressiva
            }
        }.start();
    }
}












