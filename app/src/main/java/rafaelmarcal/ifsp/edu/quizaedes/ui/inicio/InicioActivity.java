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

public class InicioActivity extends AppCompatActivity {

    private ActivityInicioBinding binding; // Declarando o binding
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true); // Define o loop
        mediaPlayer.start(); // Começa a reprodução

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
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Pausa a música ao sair da Activity
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Retoma a música ao voltar à Activity
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Libera os recursos ao destruir a Activity
            mediaPlayer = null;
            binding = null; // Liberar o binding quando a Activity for destruída
        }
    }
}