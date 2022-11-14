package com.senac.edukykids.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;

public class DataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "eduky.db";
    private static final int VERSION =1;
    private static final String SQL_URL = "d"

    private static final String SQL_CREATE =
            "create table imagens ( " +
                    " id integer primary key autoincrement, " +
                    " nome varchar(255), " +
                    " imagem_blob blob); ";

    public DataBase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar o Banco de Dados:
        db.execSQL(SQL_CREATE);

        ContentValues valores = new ContentValues();

        for (File arquivo : )
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
