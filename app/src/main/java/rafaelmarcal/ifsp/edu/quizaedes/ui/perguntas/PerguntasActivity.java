package rafaelmarcal.ifsp.edu.quizaedes.ui.perguntas;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rafaelmarcal.ifsp.edu.quizaedes.data.model.Pergunta;
import rafaelmarcal.ifsp.edu.quizaedes.databinding.ActivityPerguntasBinding;
import rafaelmarcal.ifsp.edu.quizaedes.ui.gameover.GameOverActivity;
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

        // Observando as moedas para atualizar a interface
        viewModel.getMoedasLiveData().observe(this, moedas -> {
            binding.tvMoedas.setText("Moedas: " + moedas);
        });

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

        // Observando o LiveData do nível para mostrar a mensagem quando o nível for alterado
        viewModel.getNivelLiveData().observe(this, nivel -> {
            if (nivel != null) {
                // Exibir a mensagem informando que o nível foi alterado
                mostrarMensagemNivel(nivel);
            }
        });

        binding.btnConfirmarResposta.setOnClickListener(v -> conferirResposta());

        // Atualizar a pontuação inicial na interface
        atualizarPontuacao();
        atualizarNivel(); // Atualiza o nível no início
    }

    private void mostrarMensagemNivel(int nivel) {
        String mensagem = "Parabéns! Você atingiu o Nível " + nivel + "!";
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void mostrarTelaVitoria() {
        Intent intent = new Intent(PerguntasActivity.this, VitoriaActivity.class);
        intent.putExtra("PONTUACAO", viewModel.getPontuacao());
        startActivity(intent);
        finish();
    }

    private void exibirPergunta(Pergunta pergunta) {
        if (pergunta != null) {
            // Atualiza o número da pergunta
            int numeroPergunta = viewModel.getNumeroPerguntaAtual(); // Obtém o número da pergunta
            binding.tvNumeroPergunta.setText("PERGUNTA " + numeroPergunta); // Atualiza o texto do TextView

            // Exibe o texto da pergunta
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
            binding.rgOpcoes.clearCheck();
        }
    }

    private void conferirResposta() {
        Pergunta perguntaAtual = viewModel.getPerguntaAtual();
        if (perguntaAtual != null) {
            int posicaoCorreta = (int) binding.rgOpcoes.getTag();
            int respostaSelecionada = binding.rgOpcoes.indexOfChild(findViewById(binding.rgOpcoes.getCheckedRadioButtonId()));

            if (respostaSelecionada == posicaoCorreta) {
                // Resposta correta
                viewModel.adicionarPontos(10); // Adiciona pontos
                viewModel.adicionarMoedas(10); // Adiciona moedas
                viewModel.incrementarNivel(); // Incrementa o nível
                viewModel.verificarVitoria();
                mostrarFeedback("Resposta correta!", true);
            } else {
                // Resposta errada
                erros++;
                viewModel.adicionarMoedas(-3); // Subtrai moedas pela resposta errada
                if (viewModel.getPontuacao() > 0) {
                    viewModel.adicionarPontos(-5); // Subtrai pontos
                }
                if (erros >= errosPermitidos) {
                    mostrarTelaGameOver();
                }
                mostrarFeedback("Resposta incorreta!", false);
            }

            viewModel.registrarPerguntaRespondida(perguntaAtual);
            viewModel.avancarPergunta();
            exibirPergunta(viewModel.getPerguntaAtual());

            // Atualizar a interface
            atualizarPontuacao();
            atualizarMoedas(); // Atualizar saldo de moedas
            atualizarProgresso();
            atualizarNivel();
        }
    }

    private void atualizarMoedas() {
        binding.tvMoedas.setText("Moedas: " + viewModel.getMoedas());
    }

    private void mostrarFeedback(String mensagem, boolean isCorreta) {
        // Atualiza o texto do Feedback com a mensagem correta
        binding.tvFeedback.setText(mensagem);

        // Altera a cor de acordo com a resposta (verde para correta, vermelho para incorreta)
        if (isCorreta) {
            binding.tvFeedback.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            binding.tvFeedback.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        // Torna o Feedback visível
        binding.tvFeedback.setVisibility(View.VISIBLE);

        // Esconde o feedback após um breve período (2 segundos, por exemplo)
        binding.tvFeedback.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvFeedback.setVisibility(View.GONE); // Esconde o Feedback
            }
        }, 2000); // 2 segundos de exibição
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

    private void atualizarProgresso() {
        int perguntasRespondidas = viewModel.getPerguntaAtualIndex();  // Número da pergunta atual (0 a 8)
        binding.progressBar.setProgress(perguntasRespondidas);  // Atualiza o progresso da barra
    }
}