package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rafaelmarcal.ifsp.edu.quizaedes.R;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityPerguntasBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.gameover.GameOverActivity;
import rafaelmarcal.ifsp.edu.quizaedes.ui.splash.SplashScreenActivity;

public class PerguntasActivity extends AppCompatActivity {

    private ActivityPerguntasBinding binding;
    private PerguntasViewModel viewModel;
    private int errosPermitidos = 2;
    private int erros = 0;
    private int nivelAtual = 1;
    private int respostasCorretasConsecutivas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerguntasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PerguntasViewModel.class);

        // Observando as perguntas
        viewModel.getPerguntas().observe(this, perguntas -> {
            if (perguntas != null && !perguntas.isEmpty()) {
                viewModel.embaralharPerguntas();
                exibirPergunta(viewModel.getPerguntaAtual());
            } else {
                Toast.makeText(this, "Erro ao carregar perguntas.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnConfirmarResposta.setOnClickListener(v -> conferirResposta());

        // Atualizar a pontuação inicial na interface
        atualizarPontuacao();
    }

    private void exibirPergunta(Pergunta pergunta) {
        if (pergunta != null) {
            binding.tvPergunta.setText(pergunta.getTextoPergunta());

            // Criar uma lista temporária das opções e embaralhá-la
            List<String> opcoesEmbaralhadas = new ArrayList<>(pergunta.getOpcoes());
            Collections.shuffle(opcoesEmbaralhadas);

            // Identificar a nova posição da resposta correta
            int novaPosicaoCorreta = opcoesEmbaralhadas.indexOf(pergunta.getOpcoes().get(pergunta.getRespostaCorreta()));

            // Configurar o texto das opções no RadioGroup com letras
            binding.rbOpcaoA.setText("A) " + opcoesEmbaralhadas.get(0));
            binding.rbOpcaoB.setText("B) " + opcoesEmbaralhadas.get(1));
            binding.rbOpcaoC.setText("C) " + opcoesEmbaralhadas.get(2));
            binding.rbOpcaoD.setText("D) " + opcoesEmbaralhadas.get(3));

            // Salvar a nova posição correta na tag do RadioGroup
            binding.rgOpcoes.setTag(novaPosicaoCorreta);

            // Resetar o RadioGroup para que nenhuma opção esteja selecionada
            binding.tvFeedback.setVisibility(View.GONE);
            binding.rgOpcoes.clearCheck();
        }
    }

    private void conferirResposta() {
        Pergunta perguntaAtual = viewModel.getPerguntaAtual();
        if (perguntaAtual != null) {
            // Obter a posição da resposta correta armazenada no RadioGroup
            int posicaoCorreta = (int) binding.rgOpcoes.getTag();

            // Obter a posição selecionada pelo usuário
            int respostaSelecionada = binding.rgOpcoes.indexOfChild(findViewById(binding.rgOpcoes.getCheckedRadioButtonId()));

            if (respostaSelecionada == posicaoCorreta) {
                // Resposta correta
                binding.tvFeedback.setText("Resposta correta!");
                binding.tvFeedback.setTextColor(getResources().getColor(R.color.green));
                binding.tvFeedback.setVisibility(View.VISIBLE);

                // Incrementar pontuação e respostas corretas consecutivas
                viewModel.adicionarPontos(10);
                respostasCorretasConsecutivas++;
                atualizarPontuacao();

                // Verificar se atingiu múltiplo de 5 respostas corretas consecutivas
                if (respostasCorretasConsecutivas % 5 == 0) {
                    nivelAtual++;
                    viewModel.atualizarPerguntasNivel(nivelAtual);  // Atualiza perguntas para o novo nível
                    mostrarSplashScreen();
                } else {
                    // Avançar para a próxima pergunta com atraso
                    binding.btnConfirmarResposta.postDelayed(() -> {
                        viewModel.avancarPergunta();
                        exibirPergunta(viewModel.getPerguntaAtual());
                    }, 1000);
                }
            } else {
                // Resposta incorreta
                respostasCorretasConsecutivas = 0; // Resetar contador de respostas corretas consecutivas
                erros++;

                if (erros > errosPermitidos) {
                    // Redirecionar para tela de Game Over
                    Intent intent = new Intent(PerguntasActivity.this, GameOverActivity.class);
                    intent.putExtra("PONTUACAO", viewModel.getPontuacao());
                    startActivity(intent);
                    finish();
                } else {
                    // Exibir feedback de erro
                    binding.tvFeedback.setText("Resposta incorreta. Tente novamente.");
                    binding.tvFeedback.setTextColor(getResources().getColor(R.color.red));
                    binding.tvFeedback.setVisibility(View.VISIBLE);
                }
            }
        } else {
            // Caso não existam mais perguntas
            Toast.makeText(this, "Fim do quiz!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PerguntasActivity.this, GameOverActivity.class);
            intent.putExtra("PONTUACAO", viewModel.getPontuacao());
            startActivity(intent);
            finish();
        }
    }

    private void atualizarPontuacao() {
        // Atualiza o TextView da pontuação com o valor atual
        binding.tvPontuacao.setText("Pontos: " + viewModel.getPontuacao());
    }

    private void mostrarSplashScreen() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.putExtra("NIVEL", nivelAtual); // Passar o nível atual
        startActivity(intent);
    }
}
