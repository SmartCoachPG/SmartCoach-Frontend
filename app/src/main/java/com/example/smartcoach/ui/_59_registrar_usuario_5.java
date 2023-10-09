package com.example.smartcoach.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.smartcoach.R;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import model.Exercise.Rutina;
import model.User.RestriccionMedica;
import model.User.UsuarioCliente;
import model.User.Valor;

public class _59_registrar_usuario_5 extends AppCompatActivity {

    TextView tituloLimitacionesFisicas, descripcionLimitacionesFisicas, condicionesMedicas;
    ImageButton barraBusquedaB1, barraBusquedaB2, btnBusqueda;
    EditText textoBarraBusqueda;
    Button btnListo;
    ScrollView scrollView;

    List<RestriccionMedica> listaRestricciones= new ArrayList<>();
    UsuarioCliente usuarioCliente;
    int musculoObjetivo;

    ArrayList<Rutina> listaRutinas;

    ArrayList<Valor> listaValores;
    private static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._59_registrar_usuario_5);
        getSupportActionBar().hide();

        usuarioCliente = (UsuarioCliente) getIntent().getSerializableExtra("usuarioCliente");

        musculoObjetivo = getIntent().getIntExtra("musculoObjetivo", 0); // 0 es un valor predeterminado en caso de que no se encuentre el extra.

        listaRutinas = (ArrayList<Rutina>) getIntent().getSerializableExtra("listaRutinas");

        listaValores = (ArrayList<Valor>) getIntent().getSerializableExtra("listaValoresComposicionCorporal");

        tituloLimitacionesFisicas = findViewById(R.id.tituloLimitacionesFisicas_59);
        descripcionLimitacionesFisicas = findViewById(R.id.descripcionLimitacionesFisicas_59);
        barraBusquedaB1 = findViewById(R.id.barra_busqueda_b_59);
        barraBusquedaB2 = findViewById(R.id.rectanguloLargo);
        textoBarraBusqueda = findViewById(R.id.texto_barra_busqueda_59);
        btnBusqueda = findViewById(R.id.btnBusqueda);
        condicionesMedicas = findViewById(R.id.condicionesMedicas);
        btnListo = findViewById(R.id.btnListo_59);
        scrollView = findViewById(R.id.scrollView_59);

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnBusqueda clickeado");
                Intent intent = new Intent(_59_registrar_usuario_5.this, _59_2_registrar_usuario_5.class);
                intent.putExtra("textoBusqueda",textoBarraBusqueda.getText().toString());
                Log.d("Debug", "texto busqueda: "+textoBarraBusqueda.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnListo clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_59_registrar_usuario_5.this);

                View dialogView = getLayoutInflater().inflate(R.layout._60_mensaje_advertencia_limitaciones_fisicas, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();

                Button btnContinuar = dialogView.findViewById(R.id.btnContinuarLF);
                btnContinuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        crearUsuario();
                       // Intent intent = new Intent(_59_registrar_usuario_5.this, _63_mi_actividad.class);
                        //startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        actualizarLista();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<RestriccionMedica> restriccionesRecibidas = (ArrayList<RestriccionMedica>) data.getSerializableExtra("seleccionados");
            if (restriccionesRecibidas != null) {
                listaRestricciones.addAll(restriccionesRecibidas);
            }
            actualizarLista();
        }
    }


    private void actualizarLista(){
        LinearLayout linearLayout = findViewById(R.id.linear_layout_inside_scrollview);
        linearLayout.removeAllViews();

        if(listaRestricciones == null) {
            Log.e("Error", "La lista de restricciones médicas seleccionadas es null");
            return;
        } else {
            LayoutInflater inflater = LayoutInflater.from(this);
            for (RestriccionMedica item : listaRestricciones) {
                View itemView = inflater.inflate(R.layout.item_restriccion, linearLayout, false);
                TextView tvRestriccion = itemView.findViewById(R.id.textViewItem);
                tvRestriccion.setText(item.getNombreLimitacion());

                ImageButton btnEliminar = itemView.findViewById(R.id.btnEliminar);
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaRestricciones.remove(item);
                        actualizarLista();
                    }
                });

                linearLayout.addView(itemView);

            }
        }
    }

    private void crearUsuario()
    {
        // 1.crear Usuario , aqui se le asigna el objetivo Rutina, nivel de actividad fisica
        Log.d("InfoRecibida", "UsuarioCliente: " + usuarioCliente.toString());
        crearUsuarioCliente();
        // 2. crear rutinas ... creo que aqui se podria tomar la mitad del numero de rutinas y ponerle ese musculo
        Log.d("InfoRecibida", "MusculoObjetivo: " + musculoObjetivo);
        Log.d("InfoRecibida", "ListaRutinas: " + listaRutinas.toString());
        crearRutinas();
        // 3. crear perfil medico
        Log.d("InfoRecibida", "ListaValores: " + listaValores.toString()); // Asegúrate de que Valor tenga un método toString() adecuado.
        crearPefilMedico();

        // 4. asignar limitacions fisicas
        Log.d("InfoRecibida", "Lista restricciones: "+listaRestricciones.toString());
        asignarRestriccionesM();
    }

    private void crearUsuarioCliente()
    {
        UsuarioCliente newCliente = new UsuarioCliente();

    }

    private void crearRutinas()
    {

    }

    private void crearPefilMedico()
    {

    }

    private void asignarRestriccionesM()
    {

    }
}

