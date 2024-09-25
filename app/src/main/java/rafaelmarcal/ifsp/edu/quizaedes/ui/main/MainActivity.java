package rafaelmarcal.ifsp.edu.quizaedes.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Usando ViewBinding para substituir o findViewById
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Botão de Login
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navegar para a LoginActivity
                Intent intent = new Intent(MainActivity.this, rafaelmarcal.ifsp.edu.quizaedes.ui.login.LoginActivity.class);
                startActivity(intent);
            }
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
    }
}