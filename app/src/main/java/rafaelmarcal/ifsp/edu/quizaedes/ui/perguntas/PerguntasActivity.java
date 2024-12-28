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
            // Exibir o texto da pergunta
            binding.tvPergunta.setText(pergunta.getTextoPergunta());

            // Criar uma lista temporária das opções e embaralhá-la
            List<String> opcoesEmbaralhadas = new ArrayList<>(pergunta.getOpcoes());
            Collections.shuffle(opcoesEmbaralhadas);

            // Identificar a nova posição da resposta correta
            int novaPosicaoCorreta = opcoesEmbaralhadas.indexOf(pergunta.getOpcoes().get(pergunta.getRespostaCorreta()));

            // Configurar o texto das opções no RadioGroup
            binding.rbOpcaoA.setText(opcoesEmbaralhadas.get(0));
            binding.rbOpcaoB.setText(opcoesEmbaralhadas.get(1));
            binding.rbOpcaoC.setText(opcoesEmbaralhadas.get(2));
            binding.rbOpcaoD.setText(opcoesEmbaralhadas.get(3));

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
                binding.tvFeedback.setTextColor(getResources().getColor(R.color.green)); // Define a cor do texto para verde
                binding.tvFeedback.setVisibility(View.VISIBLE);

                // Adicionar pontos e atualizar a interface
                viewModel.adicionarPontos(10);
                atualizarPontuacao();

                // Aguardar um pequeno atraso antes de avançar para a próxima pergunta
                binding.btnConfirmarResposta.postDelayed(() -> {
                    viewModel.avancarPergunta();
                    exibirPergunta(viewModel.getPerguntaAtual());
                }, 1000); // 1 segundo de atraso
            } else {
                // Resposta incorreta
                erros++;
                if (erros > errosPermitidos) {
                    // Redirecionar para a tela de GameOver com a pontuação
                    Intent intent = new Intent(PerguntasActivity.this, GameOverActivity.class);
                    intent.putExtra("PONTUACAO", viewModel.getPontuacao());
                    startActivity(intent);
                    finish();
                } else {
                    binding.tvFeedback.setText("Resposta incorreta. Tente novamente.");
                    binding.tvFeedback.setTextColor(getResources().getColor(R.color.red)); // Define a cor do texto para vermelho
                    binding.tvFeedback.setVisibility(View.VISIBLE);
                }
            }
        } else {
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
}