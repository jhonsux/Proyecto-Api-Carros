package com.example.autosisc;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AutoDBHelper dbHelper;
    private EditText txtFolio;
    private EditText txtMarca;
    private EditText txtModelo;
    private EditText txtAño;
    private EditText txtColor;
    private EditText txtSerie;
    private EditText txtPrecio;
    private Button btnGuardar;
    private Button btnBuscar;
    private Button btnEliminar;
    private Button btnActualizar;
    private ListView lvAutos;
    ArrayList<String> origendata;
    ArrayAdapter<String> adapter;


    public void obtenerAutos(){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        String[] campos = {
                AutosISCContrac.Automobil.COLUMN_NAME_FOLIO,
                AutosISCContrac.Automobil.COLUMN_NAME_MARCA,
                AutosISCContrac.Automobil.COLUMN_NAME_MODELO
        };
        Cursor cursor= db.query(
                AutosISCContrac.Automobil.TABLE_NAME,
                campos, // Lista de Columnas a consultar
                null, // Columnas para la cláusula WHERE
                null,
                null,
                null,
                null
        );
        origendata= new ArrayList<>();
        while (cursor.moveToNext()){
            origendata.add(cursor.getString(0)+"::"+cursor.getString(1)+"::"+cursor.getString(2));
        }
        cursor.close();
        adapter= new ArrayAdapter<String>(MainActivity.this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,origendata);
        lvAutos.setAdapter(adapter);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtFolio = findViewById(R.id.txtFolio);
        txtMarca=findViewById(R.id.txtMarca);
        txtModelo=findViewById(R.id.txtModelo);
        txtAño=findViewById(R.id.txtAño);
        txtColor=findViewById(R.id.txtColor);
        txtSerie=findViewById(R.id.txtSerie);
        txtPrecio=findViewById(R.id.txtPrecio);
        btnGuardar=findViewById(R.id.btnGuardar);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnEliminar=findViewById(R.id.btnEliminar);
        btnActualizar=findViewById(R.id.btnActualizar);
        lvAutos=findViewById(R.id.lvAutos);
        dbHelper=new AutoDBHelper(getApplicationContext());

        obtenerAutos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values= new ContentValues();
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_FOLIO,txtFolio.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_MARCA,txtMarca.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_MODELO,txtModelo.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_AÑO,txtAño.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_COLOR,txtColor.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_SERIE,txtSerie.getText().toString());
                values.put(AutosISCContrac.Automobil.COLUMN_NAME_PRECIO,Float.parseFloat(txtPrecio.getText().toString()));
                long newRowId=db.insert(AutosISCContrac.Automobil.TABLE_NAME,null,values);
                try {
                    if (newRowId!=1){
                        Toast.makeText(MainActivity.this, "Guardado Con Exito", Toast.LENGTH_SHORT).show();
                        obtenerAutos();
                        limpiar();
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Ya Existe Uno", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getReadableDatabase();
                String[] campos={
                        AutosISCContrac.Automobil.COLUMN_NAME_FOLIO,
                        AutosISCContrac.Automobil.COLUMN_NAME_MARCA,
                        AutosISCContrac.Automobil.COLUMN_NAME_MODELO,
                        AutosISCContrac.Automobil.COLUMN_NAME_AÑO,
                        AutosISCContrac.Automobil.COLUMN_NAME_COLOR,
                        AutosISCContrac.Automobil.COLUMN_NAME_SERIE,
                        AutosISCContrac.Automobil.COLUMN_NAME_PRECIO
                };
                String condicion = AutosISCContrac.Automobil.COLUMN_NAME_FOLIO+" = ?";
                String[] conditionArgs={txtFolio.getText().toString()};
                Cursor cursor=db.query(
                        AutosISCContrac.Automobil.TABLE_NAME,
                        campos, // Lista de Columnas a consultar
                        condicion, // Columnas para la cláusula WHERE
                        conditionArgs, // Valores a comparar con las columnas del WHERE
                        null, // Agrupar con GROUP BY
                        null, // Condición HAVING para GROUP BY
                        null // Cláusula ORDER BY
                );
                if(cursor.moveToNext()){
                    txtMarca.setText(cursor.getString(1));
                    txtModelo.setText(cursor.getString(2));
                    txtAño.setText(cursor.getString(3));
                    txtColor.setText(cursor.getString(4));
                    txtSerie.setText(cursor.getString(5));
                    txtPrecio.setText(String.valueOf(cursor.getFloat(6)));
                }else {
                    limpiar();
                    Toast.makeText(MainActivity.this, "No hay con ese folio", Toast.LENGTH_SHORT).show();
                }cursor.close();
            }
            
        });
        
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getReadableDatabase();
                String seleccion=AutosISCContrac.Automobil.COLUMN_NAME_FOLIO+" = ?";
                String [] seleccionArgs={txtFolio.getText().toString()};
                int filasrobadas = db.delete(
                        AutosISCContrac.Automobil.TABLE_NAME,
                        seleccion,
                        seleccionArgs
                );
                if (filasrobadas>0){
                    Toast.makeText(MainActivity.this, "Eliminado con exito", Toast.LENGTH_SHORT).show();
                    limpiar();
                    obtenerAutos();
                }else {
                    Toast.makeText(MainActivity.this, "no hay servicio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues newValues=new ContentValues();
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_MARCA,txtMarca.getText().toString());
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_MODELO,txtModelo.getText().toString());
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_AÑO,txtAño.getText().toString());
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_COLOR,txtColor.getText().toString());
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_SERIE,txtSerie.getText().toString());
                newValues.put(AutosISCContrac.Automobil.COLUMN_NAME_PRECIO,Float.parseFloat(txtPrecio.getText().toString()));
                String selection = AutosISCContrac.Automobil.COLUMN_NAME_FOLIO+" = ?";
                String[] selectionArgs={txtFolio.getText().toString()};
                int filasActualizadas=db.update(AutosISCContrac.Automobil.TABLE_NAME,
                        newValues,
                        selection,
                        selectionArgs
                );
                if (filasActualizadas>0){
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void limpiar(){
        txtFolio.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAño.setText("");
        txtColor.setText("");
        txtSerie.setText("");
        txtPrecio.setText("");
    }
}