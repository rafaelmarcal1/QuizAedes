package rafaelmarcal.ifsp.edu.quizaedes.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityLoginBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializando o ViewModel
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Configurando o clique do botão de login
        binding.btnFazerLogin.setOnClickListener(view -> {
            String email = binding.etEmail.getText().toString().trim();
            String senha = binding.etPassword.getText().toString().trim();

            if (validarCampos(email, senha)) {
                viewModel.fazerLogin(email, senha);
            }
        });

        observarViewModel();
    }

    private boolean validarCampos(String email, String senha) {
//        if (email.isEmpty()) {
//            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (senha.isEmpty()) {
//            Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (senha.length() < 6) {
//            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    private void observarViewModel() {
        // Observa o sucesso do login
        viewModel.getLoginResultado().observe(this, sucesso -> {
            if (sucesso) {
                Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                // Navegar para a tela principal
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();  // Finaliza a tela de login para que não possa voltar para ela com o botão de voltar
            } else {
                Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
            }
        });

        // Observa os erros
        viewModel.getErro().observe(this, mensagemErro -> {
            if (mensagemErro != null) {
                Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
