package rafaelmarcal.ifsp.edu.quizaedes.ui.telas_explicativas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivitySejaBemVindoBinding;

public class SejaBemVindoActivity extends AppCompatActivity {

    private ActivitySejaBemVindoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySejaBemVindoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNextWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(SejaBemVindoActivity.this, RecompensasActivity.class);
            startActivity(intent);
        });
    }
}