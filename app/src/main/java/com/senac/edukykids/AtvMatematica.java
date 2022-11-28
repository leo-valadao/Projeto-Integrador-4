package com.senac.edukykids;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AtvMatematica extends AppCompatActivity implements View.OnClickListener {

    // Objetos
    ImageView imageViewMeme;

    EditText edtTerceiroNumero;

    TextView textPrimeiroNumero;
    TextView textSegundoNumero;
    TextView textPrimeiroOperador;
    TextView textSegundoOperador;
    TextView textViewPontuacao;

    Button btnTentarCalculo;

    Spinner spinnerDificuldade;

    Double resultado = 0.0;

    Integer pontuacao = 0;

    MediaPlayer mediaPlayer;

    String memeAtual = "netrual";

    private static final String[] dificuladades = {"Fácil", "Médio", "Difícil"};
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atv_matematica);

        // Configurando os Objetos
        imageViewMeme = findViewById(R.id.imageViewMeme);

        textPrimeiroNumero = findViewById(R.id.textPrimeiroNumero);
        textSegundoNumero = findViewById(R.id.textSegundoNumero);
        edtTerceiroNumero = findViewById(R.id.textTerceiroNumero);

        textPrimeiroOperador = findViewById(R.id.textPrimeiroOperador);
        textSegundoOperador = findViewById(R.id.textSegundoOperador);

        textViewPontuacao = findViewById(R.id.textViewPontuacao);

        btnTentarCalculo = findViewById(R.id.btnTentarCalculo);
        btnTentarCalculo.setOnClickListener(this);

        spinnerDificuldade = findViewById(R.id.spinnerDificuldade);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dificuladades);
        spinnerDificuldade.setAdapter(adapter);
        spinnerDificuldade.setSelection(0);

        // Inicializar o Desafio
        try {
            inicializarDesafio();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicia a Pontuação com 0 Pontos
        textViewPontuacao.setText(String.valueOf(pontuacao));

        // Inicia o Meme
        definirMeme();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTentarCalculo:
                String terceiroNumero = df.format(Double.valueOf(edtTerceiroNumero.getText().toString()));
                String resultadoReal = df.format(this.resultado);
                if (terceiroNumero.equals(resultadoReal)) {
                    Toast.makeText(this, "Resposta CORRETA!", Toast.LENGTH_LONG).show();
                    mudarPontuacao(true);
                    try {
                        inicializarDesafio();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Resposta INCORRETA!", Toast.LENGTH_LONG).show();
                    mudarPontuacao(false);
                }
                break;
            default:
                Toast.makeText(this, "Opção Inválida!", Toast.LENGTH_LONG).show();
        }
    }

    public void inicializarDesafio() throws Exception {
        // Inicializa o Desafio de Acordo com a Dificuldade Escolhida
        switch (spinnerDificuldade.getSelectedItem().toString()) {
            case "Difícil":
                definirOperador("Difícil");
                definirNumero("Difícil", textPrimeiroNumero);
                definirNumero("Difícil", textSegundoNumero);
                break;
            case "Médio":
                definirOperador("Médio");
                definirNumero("Médio", textPrimeiroNumero);
                definirNumero("Médio", textSegundoNumero);
                break;
            case "Fácil":
                definirOperador("Fácil");
                definirNumero("Fácil", textPrimeiroNumero);
                definirNumero("Fácil", textSegundoNumero);
                break;
            default:
                throw new Exception();
        }

        // Define o Resultado Esperado
        definirResultado(textPrimeiroOperador);
    }

    public void definirOperador(String dificuldade) {
        // Inicializar a Lista de Operadores que Serão Usados no Desafio:
        List<String> operadores = new ArrayList<>();

        if (dificuldade.equals("Difícil")) {
            operadores.add("+");
            operadores.add("-");
            operadores.add("×");
            operadores.add("÷");
        } else {
            operadores.add("+");
            operadores.add("-");
        }

        // Selecionar Aleatóriamente o Operador:
        Random aleatorio = new Random();
        int numeroAleatorio = aleatorio.nextInt((operadores.size() - 1) + 1);

        // Modificar o 1º Operador na Tela:
        textPrimeiroOperador.setText(operadores.get(numeroAleatorio));
    }

    public void definirNumero(String dificuldade, TextView texto) {
        // Definir o Alcance do Número:
        int maximo;
        int minimo = 1;

        // Evitar Números Negativos e Divisões com o Denominador Menor que o Numerador:
        if (texto == textSegundoNumero && (textPrimeiroOperador.getText().toString().equals("-") ||
                textPrimeiroOperador.getText().toString().equals("÷"))) {
            maximo = Integer.parseInt(textPrimeiroNumero.getText().toString());
        } else if (dificuldade.equals("Fácil")) {
            maximo = 10;
        } else {
            maximo = 100;
        }

        // Gerar o Número Aleatório no Campo de Texto:
        Integer numero = (int) ((Math.random() * (maximo - minimo)) + minimo);
        texto.setText(String.valueOf(numero));
    }

    public void definirResultado(TextView operador) throws Exception {
        // Coletar o Valor dos Números na Tela:
        Double primeiroNumero = Double.valueOf(this.textPrimeiroNumero.getText().toString());
        Double segundoNumero = Double.valueOf(this.textSegundoNumero.getText().toString());

        // Calcular o Resultado:
        switch (operador.getText().toString()) {
            case "+":
                this.resultado = primeiroNumero + segundoNumero;
                break;
            case "-":
                this.resultado = primeiroNumero - segundoNumero;
                break;
            case "×":
                this.resultado = primeiroNumero * segundoNumero;
                break;
            case "÷":
                this.resultado = primeiroNumero / segundoNumero;
                break;
            default:
                throw new Exception();
        }
    }

    public void definirMeme() {

        // Define o Meme e a Música
        if (this.pontuacao == 0 && !memeAtual.equals("neutral")) {
            memeAtual = "neutral";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.neutral);
            mediaPlayer = MediaPlayer.create(this, R.raw.songneutral);
            mediaPlayer.start();
        } else if (estaEntre(0, this.pontuacao, 10) && !memeAtual.equals("good1")) {
            memeAtual = "good1";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good1);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood1);
            mediaPlayer.start();
        } else if (estaEntre(10, this.pontuacao, 20) && !memeAtual.equals("good2")) {
            memeAtual = "good2";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good2);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood2);
            mediaPlayer.start();
        } else if (estaEntre(20, this.pontuacao, 30) && !memeAtual.equals("good3")) {
            memeAtual = "good3";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good3);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood3);
            mediaPlayer.start();
        } else if (estaEntre(30, this.pontuacao, 40) && !memeAtual.equals("good4")) {
            memeAtual = "good4";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good4);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood4);
            mediaPlayer.start();
        } else if (estaEntre(40, this.pontuacao, 50) && !memeAtual.equals("good5")) {
            memeAtual = "good5";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good5);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood5);
            mediaPlayer.start();
        } else if (estaEntre(50, this.pontuacao, 60) && !memeAtual.equals("good6")) {
            memeAtual = "good6";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good6);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood6);
            mediaPlayer.start();
        } else if (this.pontuacao > 70 && !memeAtual.equals("good7")) {
            memeAtual = "good7";
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            imageViewMeme.setImageResource(R.drawable.good7);
            mediaPlayer = MediaPlayer.create(this, R.raw.songgood7);
            mediaPlayer.start();
        }
    }

    public void mudarPontuacao(Boolean acertou) {

        // Define a Pontuação a ser Ganha
        if (acertou) {
            switch (spinnerDificuldade.getSelectedItem().toString())  {
                case "Difícil":
                    this.pontuacao += 5;
                    break;
                case "Médio":
                    this.pontuacao += 3;
                    break;
                case "Fácil":
                    this.pontuacao += 1;
                    break;
                default:
                    System.out.println("Erro!");
            }
        } else {
            switch (spinnerDificuldade.getSelectedItem().toString())  {
                case "Difícil":
                    this.pontuacao -= 5;
                    break;
                case "Médio":
                    this.pontuacao -= 3;
                    break;
                case "Fácil":
                    this.pontuacao -= 1;
                    break;
                default:
                    System.out.println("Erro!");
            }
        }

        // Redefine o Meme
        definirMeme();

        // Muda o Número da Pontuação na Tela
        textViewPontuacao.setText(String.valueOf(this.pontuacao));
    }

    public static boolean estaEntre(int menor, int x, int maior) {
        return menor < x && x <= maior;
    }
}