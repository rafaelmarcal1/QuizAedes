package rafaelmarcal.ifsp.edu.quizaedes.ui.conquistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityConquistasBinding;

public class ConquistasActivity extends AppCompatActivity {

    private ActivityConquistasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConquistasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recuperar o total de moedas acumuladas
        SharedPreferences prefs = getSharedPreferences("game_data", MODE_PRIVATE);
        int moedasTotais = prefs.getInt("MOEDAS_TOTAL", 0);

        // Exibir no TextView
        binding.tvTotalMoedas.setText("Total de moedas: " + moedasTotais);
    }
}