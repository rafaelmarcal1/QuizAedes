package rafaelmarcal.ifsp.edu.quizaedes.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityLoginBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.inicio.InicioActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar se o usuário já está logado
        String emailSalvo = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                .getString("user_email", null);

        if (emailSalvo != null) {
            // Redirecionar para a tela principal
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
            finish();
            return; // Evitar que o restante do código seja executado
        }

        // Configuração inicial do LoginActivity
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

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
        if (email.isEmpty()) {
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void observarViewModel() {
        viewModel.getLoginResultado().observe(this, sucesso -> {
            if (sucesso) {
                String email = binding.etEmail.getText().toString().trim();

                // Salvar o email no SharedPreferences
                getSharedPreferences("AppPrefs", MODE_PRIVATE)
                        .edit()
                        .putString("user_email", email)
                        .apply();

                Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                // Navegar para a tela principal
                Intent intent = new Intent(this, InicioActivity.class);
                startActivity(intent);
                finish(); // Finaliza a tela de login
            } else {
                Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErro().observe(this, mensagemErro -> {
            if (mensagemErro != null) {
                Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
