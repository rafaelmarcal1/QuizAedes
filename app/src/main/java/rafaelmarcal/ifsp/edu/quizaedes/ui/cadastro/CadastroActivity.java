package rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityCadastroBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.login.LoginActivity;

public class CadastroActivity extends AppCompatActivity {
    private ActivityCadastroBinding binding;
    private CadastroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o ViewBinding
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializando o ViewModel
        viewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

        // Configurando o clique do botão de criação de conta
        binding.btnCriarConta.setOnClickListener(view -> {
            handleCriarConta();
        });

        observarViewModel();
    }

    private void handleCriarConta() {
        String nome = binding.etNome.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String senha = binding.etPassword.getText().toString().trim();
        String confirmSenha = binding.etConfirmPassword.getText().toString().trim();

        // Verificação do nome
        if (!isNomeValido(nome)) {
            Toast.makeText(this, "O nome deve ter pelo menos 2 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificação de email
        if (!isEmailValido(email)) {
            Toast.makeText(this, "Digite um email válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificação do tamanho da senha
        if (!isSenhaTamanhoValido(senha)) {
            Toast.makeText(this, "A senha deve ter pelo menos 8 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificação de força da senha
        if (!isSenhaForte(senha)) {
            Toast.makeText(this, "A senha deve conter ao menos um número e um caractere especial.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificação de confirmação de senha
        if (!isSenhaConfirmada(senha, confirmSenha)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se todas as verificações forem bem-sucedidas, cria a conta
        viewModel.criarConta(nome, email, senha);
    }

    // Verificação de formato de email
    private boolean isEmailValido(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Verificação de tamanho mínimo de senha
    private boolean isSenhaTamanhoValido(String senha) {
        return senha.length() >= 8;
    }

    // Verificação de força da senha (número e caractere especial)
    private boolean isSenhaForte(String senha) {
        return senha.matches(".*\\d+.*") && senha.matches(".*[!@#\\$%^&*()_+\\-=]+.*");
    }

    // Verificação de confirmação de senha
    private boolean isSenhaConfirmada(String senha, String confirmSenha) {
        return senha.equals(confirmSenha);
    }

    // Verificação de comprimento do nome
    private boolean isNomeValido(String nome) {
        return nome.length() >= 2;
    }

    private void observarViewModel() {
        // Observa o sucesso do registro
        viewModel.getRegistroResultado().observe(this, sucesso -> {
            if (sucesso) {
                Toast.makeText(CadastroActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                // Navegar para a tela de login ou principal
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(CadastroActivity.this, "Falha ao criar conta", Toast.LENGTH_SHORT).show();
            }
        });

        // Observa os erros
        viewModel.getErro().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensagemErro) {
                if (mensagemErro != null) {
                    Toast.makeText(CadastroActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}