package com.example.smartcoach.ui;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;
        import com.example.smartcoach.R;

public class _23_modificar_equipo extends AppCompatActivity {

    ImageView imagenEquipoImageView;
    ImageButton atrasButton, subirImagenButton, masButton;
    EditText nombreEquipoEditText, referenciaEquipoEditText, descripcionEquipoEditText;
    ScrollView scrollView;
    TextView tituloModificarTextView, descripcionModificarTextView, subaImagenTextView, textoMusculosTextView;
    Button btnGuardarCambios;
    View menuAdmin_23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._23_modificar_equipo);

        atrasButton = findViewById(R.id.atras_23);
        tituloModificarTextView = findViewById(R.id.tituloModificar_23);
        descripcionModificarTextView = findViewById(R.id.descripcionModificar_23);
        nombreEquipoEditText = findViewById(R.id.setTextnombreEquipo_23);
        referenciaEquipoEditText = findViewById(R.id.setTextReferenciaEquipo_23);
        imagenEquipoImageView = findViewById(R.id.imagenEquipo_23);
        subirImagenButton = findViewById(R.id.imageSubirImagen);
        subaImagenTextView = findViewById(R.id.textSubaImagen_23);
        textoMusculosTextView = findViewById(R.id.textoMusculos_23);
        scrollView = findViewById(R.id.scrollView2);
        masButton = findViewById(R.id.imageButton7);
        descripcionEquipoEditText = findViewById(R.id.descripcionEquipo_23);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_23);
        menuAdmin_23 = findViewById(R.id.include2);

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
