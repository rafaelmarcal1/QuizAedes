package rafaelmarcal.ifsp.edu.quizaedes.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityInicioBinding; // Importando o binding gerado automaticamente
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.conquistas.ConquistasActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.ranking.RankingActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perfil.PerfilActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.configuracoes.ConfiguracoesActivity;

public class InicioActivity extends AppCompatActivity {

    private ActivityInicioBinding binding; // Declarando o binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o ViewBinding
        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Definir listeners para os botões usando binding
        binding.btnIniciarJogo.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, PerguntasActivity.class);
            startActivity(intent);
        });

        binding.btnConquistas.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, ConquistasActivity.class);
            startActivity(intent);
        });

        binding.btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, RankingActivity.class);
            startActivity(intent);
        });

        binding.btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, PerfilActivity.class);
            startActivity(intent);
        });

        binding.btnConfiguracoes.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, ConfiguracoesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Liberar o binding quando a Activity for destruída
    }
}