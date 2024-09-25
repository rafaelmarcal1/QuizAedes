package rafaelmarcal.ifsp.edu.quizaedes.ui.login;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityLoginBinding;

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

            if (!email.isEmpty() && !senha.isEmpty()) {
                viewModel.fazerLogin(email, senha);
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        observarViewModel();
    }

    private void observarViewModel() {
        // Observa o sucesso do login
        viewModel.getLoginResultado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sucesso) {
                if (sucesso) {
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                    // Navegar para a tela principal
                } else {
                    Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observa os erros
        viewModel.getErro().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensagemErro) {
                if (mensagemErro != null) {
                    Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}