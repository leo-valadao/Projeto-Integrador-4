package com.senac.edukykids;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AtvMatematica extends AppCompatActivity implements View.OnClickListener {

    // Objetos
    EditText edtTerceiroNumero;

    TextView textPrimeiroNumero;
    TextView textSegundoNumero;
    TextView textPrimeiroOperador;
    TextView textSegundoOperador;

    Button btnTentarCalculo;

    Spinner spinnerDificuldade;

    Double resultado = 0.0;

    private static final String[] dificuladades = {"Fácil", "Médio", "Difícil"};
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atv_matematica);

        // Configurando os Objetos
        textPrimeiroNumero = findViewById(R.id.textPrimeiroNumero);
        textSegundoNumero = findViewById(R.id.textSegundoNumero);
        edtTerceiroNumero = findViewById(R.id.textTerceiroNumero);

        textPrimeiroOperador = findViewById(R.id.textPrimeiroOperador);
        textSegundoOperador = findViewById(R.id.textSegundoOperador);

        btnTentarCalculo = findViewById(R.id.btnTentarCalculo);
        btnTentarCalculo.setOnClickListener(this);

        spinnerDificuldade = findViewById(R.id.spinnerDificuldade);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dificuladades);
        spinnerDificuldade.setAdapter(adapter);
        spinnerDificuldade.setSelection(0);

        try {
            inicializarDesafio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTentarCalculo:
                String terceiroNumero = df.format(Double.valueOf(edtTerceiroNumero.getText().toString()));
                String resultadoReal = df.format(this.resultado);
                if (terceiroNumero.equals(resultadoReal)) {
                    Toast.makeText(this, "Resposta CORRETA!", Toast.LENGTH_LONG).show();
                    try {
                        inicializarDesafio();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Resposta INCORRETA!", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(this, "Opção Inválida!", Toast.LENGTH_LONG).show();
        }
    }

    public void inicializarDesafio() throws Exception {

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

        definirResultado(textPrimeiroOperador);
    }

    public void definirOperador(String dificuldade) {
        // Inicializar a Lista de Operadores que Serão Usados no Desafio:
        List<String> operadores = new ArrayList<String>();

        if (dificuldade == "Difícil") {
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
        Integer numeroAleatorio = aleatorio.nextInt((operadores.size()-1) + 1) + 0;

        // Modificar o 1º Operador na Tela:
        textPrimeiroOperador.setText(operadores.get(numeroAleatorio));
    }

    public void definirNumero(String dificuldade, TextView texto) {
        // Definir o Alcance do Número:
        Integer maximo;
        Integer minimo = 1;

        // Evitar Números Negativos e Divisões com o Denominador Menor que o Numerador:
        if (texto == textSegundoNumero && (textPrimeiroOperador.getText().toString() == "-" ||
                textPrimeiroOperador.getText().toString() == "÷")) {
            maximo = Integer.valueOf(textPrimeiroNumero.getText().toString());
        } else if (dificuldade == "Fácil") {
            maximo = 10;
        } else {
            maximo = 100;
        }

        // Gerar o Número Aleatório no Campo de Texto:
        Integer numero = (int) ((Math.random() * (maximo - minimo)) + minimo);
        texto.setText(numero.toString());
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
}