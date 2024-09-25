package rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityCadastroBinding;
import rafaelmarcal.ifsp.edu.quizaedes.viewmodel.CadastroViewModel;

public class CadastroActivity extends AppCompatActivity {
    private ActivityCadastroBinding binding;
    private CadastroViewModel cadastroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o ViewBinding
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializando o ViewModel
        cadastroViewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

        // Configurando o clique do botão de criação de conta
        binding.btnCriarConta.setOnClickListener(view -> {
            String email = binding.etEmail.getText().toString().trim();
            String senha = binding.etPassword.getText().toString().trim();

            if (!email.isEmpty() && !senha.isEmpty()) {
                cadastroViewModel.criarConta(email, senha);
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        observarViewModel();
    }

    private void observarViewModel() {
        // Observa o sucesso do registro
        cadastroViewModel.getRegistroResultado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sucesso) {
                if (sucesso) {
                    Toast.makeText(CadastroActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                    // Navegar para a tela de login ou principal
                } else {
                    Toast.makeText(CadastroActivity.this, "Falha ao criar conta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observa os erros
        cadastroViewModel.getErro().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensagemErro) {
                if (mensagemErro != null) {
                    Toast.makeText(CadastroActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}