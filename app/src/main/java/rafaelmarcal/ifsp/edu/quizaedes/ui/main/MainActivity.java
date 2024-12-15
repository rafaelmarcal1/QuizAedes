package rafaelmarcal.ifsp.edu.quizaedes.ui.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityMainBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usando ViewBinding para substituir o findViewById
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListener();
    }

    private void setupClickListener() {
        // Botão de Login
        binding.btnLogin.setOnClickListener(view -> {
            //Navegar para a LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Botão de Cadastro
        binding.btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar para a CadastroActivity
                Intent intent = new Intent(MainActivity.this, rafaelmarcal.ifsp.edu.quizaedes.ui.cadastro.CadastroActivity.class);
                startActivity(intent);
            }
        });

        //Botão para retornar ao Quiz
        binding.btnRetornarQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar para a QuizActivity
                Intent intent = new Intent(MainActivity.this, rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas.PerguntasActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSairInicio.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmação")
                    .setMessage("Você realmente deseja sair do APP?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        finish(); // Fecha a Activity atual
                        System.exit(0); // Encerra o processo do aplicativo
                    })
                    .setNegativeButton("Não", (dialog, which) -> dialog.dismiss()) // Fecha o diálogo
                    .show();
        });
    }
}