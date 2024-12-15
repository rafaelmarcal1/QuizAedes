package rafaelmarcal.ifsp.edu.quizaedes.ui.telas_explicativas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityRecompensasBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.inicio.InicioActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.main.MainActivity;

public class RecompensasActivity extends AppCompatActivity {

    private ActivityRecompensasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecompensasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnFinishIntro.setOnClickListener(v -> {
            Intent intent = new Intent(RecompensasActivity.this, InicioActivity.class);
            startActivity(intent);
            finish();
        });
    }
}