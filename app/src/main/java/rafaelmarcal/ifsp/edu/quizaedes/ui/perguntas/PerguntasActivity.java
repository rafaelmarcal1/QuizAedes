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
import rafaelmarcal.ifsp.edu.quizaedes.ui.splash.VitoriaActivity;

public class PerguntasActivity extends AppCompatActivity {

    private ActivityPerguntasBinding binding;
    private PerguntasViewModel viewModel;
    private int errosPermitidos = 2;
    private int erros = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerguntasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PerguntasViewModel.class);

        // Recuperando a pontuação passada pela SplashScreen
        if (getIntent() != null) {
            int pontuacaoRecuperada = getIntent().getIntExtra("PONTUACAO", 0);
            viewModel.setPontuacao(pontuacaoRecuperada); // Atualiza a pontuação
        }

        // Observando as perguntas
        viewModel.getPerguntas().observe(this, perguntas -> {
            if (perguntas != null && !perguntas.isEmpty()) {
                viewModel.embaralharPerguntas();
                exibirPergunta(viewModel.getPerguntaAtual());
            } else {
                Toast.makeText(this, "Erro ao carregar perguntas.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observando o estado de "quiz terminado"
        viewModel.getQuizTerminado().observe(this, quizTerminado -> {
            if (quizTerminado != null && quizTerminado) {
                // O quiz acabou, redireciona para a tela de vitória
                mostrarTelaVitoria();
            }
        });

        binding.btnConfirmarResposta.setOnClickListener(v -> conferirResposta());

        // Atualizar a pontuação inicial na interface
        atualizarPontuacao();
        atualizarNivel(); // Atualiza o nível no início
    }

    private void mostrarTelaVitoria() {
        Intent intent = new Intent(PerguntasActivity.this, VitoriaActivity.class);
        intent.putExtra("PONTUACAO", viewModel.getPontuacao());
        startActivity(intent);
        finish();
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
                viewModel.adicionarPontos(10); // Adiciona pontos pela resposta correta
                viewModel.incrementarNivel(); // Incrementa o nível a cada 2 respostas corretas
                binding.tvFeedback.setText("Resposta correta!");
                binding.tvFeedback.setTextColor(getResources().getColor(R.color.green));
                binding.tvFeedback.setVisibility(View.VISIBLE);

                // Verifica se o usuário alcançou 9 respostas corretas
                viewModel.verificarVitoria();
            } else {
                // Resposta errada
                erros++;
                if (erros >= errosPermitidos) {
                    // Se o número de erros atingir o limite
                    mostrarTelaGameOver();
                } else {
                    binding.tvFeedback.setText("Resposta incorreta. Tente novamente.");
                    binding.tvFeedback.setTextColor(getResources().getColor(R.color.red));
                    binding.tvFeedback.setVisibility(View.VISIBLE);
                }
            }

            // Registrar a pergunta respondida
            viewModel.registrarPerguntaRespondida(perguntaAtual);

            // Avançar para a próxima pergunta
            viewModel.avancarPergunta();
            exibirPergunta(viewModel.getPerguntaAtual());

            // Atualizar a pontuação e o nível
            atualizarPontuacao();
            atualizarNivel();
        }
    }

    private void atualizarPontuacao() {
        // Atualiza o TextView da pontuação com o valor atual
        binding.tvPontuacao.setText("Pontos: " + viewModel.getPontuacao());
    }

    private void atualizarNivel() {
        // Atualiza o TextView do nível com o valor atual
        binding.tvNivel.setText("Nível: " + viewModel.getNivel());
    }

    private void mostrarTelaGameOver() {
        Intent intent = new Intent(PerguntasActivity.this, GameOverActivity.class);
        intent.putExtra("PONTUACAO", viewModel.getPontuacao());
        startActivity(intent);
        finish();
    }
}