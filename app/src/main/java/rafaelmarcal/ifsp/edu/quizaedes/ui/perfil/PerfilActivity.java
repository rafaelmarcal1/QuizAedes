package rafaelmarcal.ifsp.edu.quizaedes.ui.perfil;

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

public class PerfilActivity extends AppCompatActivity {
    private TextView tvNomeUsuario;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar o FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Referenciar o TextView onde o e-mail do usuário será exibido
        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);

        // Obter o usuário logado
        FirebaseUser user = mAuth.getCurrentUser();

        // Verificar se o usuário está logado
        if (user != null) {
            // Exibir o e-mail do usuário logado
            String emailUsuario = user.getEmail();  // Pega o e-mail do usuário
            tvNomeUsuario.setText(emailUsuario);  // Exibe o e-mail no TextView
        } else {
            // Se não houver usuário logado, exibir mensagem
            tvNomeUsuario.setText("Usuário não logado");
        }

        // Configurações de barra de status e navegação (se necessário)
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}