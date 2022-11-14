package com.senac.edukykids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Objetos
    Button btnJogoMatematica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurando os Objetos
        btnJogoMatematica = findViewById(R.id.btnJogoMatematica);
        btnJogoMatematica.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnJogoMatematica:
                Intent telaJogoMatematica = new Intent(this, AtvMatematica.class);
                startActivity(telaJogoMatematica);
                break;

            default:
            Toast.makeText(getApplicationContext(), "Opção Inválida!", Toast.LENGTH_LONG);
        }
    }
}