package rafaelmarcal.ifsp.edu.quizaedes.ui.inicio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityInicioBinding; // Importando o binding gerado automaticamente
import rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.conquistas.ConquistasActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.ranking.RankingActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.perfil.PerfilActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.configuracoes.ConfiguracoesActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.splash.CountdownActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.telas_explicativas.SejaBemVindoActivity;

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
            //Intent intent = new Intent(this, CountdownActivity.class);
            //startActivity(intent);
            Intent intent = new Intent(InicioActivity.this, CountdownActivity.class);
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

        binding.btnComoJogar.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, SejaBemVindoActivity.class);
            startActivity(intent);
        });
    }
}