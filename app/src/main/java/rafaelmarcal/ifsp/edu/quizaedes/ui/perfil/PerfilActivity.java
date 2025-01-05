package rafaelmarcal.ifsp.edu.quizaedes.ui.perfil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityPerfilBinding;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityPerguntasBinding;


public class PerfilActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityPerfilBinding binding;  // Instância do ViewBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o ViewBinding
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());  // Definir o layout com ViewBinding

        // Inicializar o FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Obter o usuário logado
        FirebaseUser user = mAuth.getCurrentUser();

        // Verificar se o usuário está logado
        if (user != null) {
            // Exibir o e-mail do usuário logado
            String emailUsuario = user.getEmail();  // Pega o e-mail do usuário
            binding.tvNomeUsuario.setText(emailUsuario);  // Exibe o e-mail no TextView
        } else {
            // Se não houver usuário logado, exibir mensagem
            binding.tvNomeUsuario.setText("Usuário não logado");
        }

        // Recuperar as estatísticas de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("game_data", MODE_PRIVATE);

        // Recupera os valores armazenados
        int pontosTotais = sharedPreferences.getInt("PONTUACAO_TOTAL", 0);
        int maiorNivel = sharedPreferences.getInt("MAIOR_NIVEL", 0);  // Se ainda não tiver nível, inicia com 0
        int conquistas = sharedPreferences.getInt("CONQUISTAS", 0);  // Exemplo: um contador de conquistas

        // Exibir as estatísticas no layout
        binding.tvNivel.setText("Maior Nível já alcançado numa partida: " + maiorNivel);
        binding.tvPontos.setText("Total de Pontos: " + pontosTotais);
        binding.tvConquistas.setText("Conquistas: " + conquistas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpar a referência ao binding para evitar vazamento de memória
        binding = null;
    }
}