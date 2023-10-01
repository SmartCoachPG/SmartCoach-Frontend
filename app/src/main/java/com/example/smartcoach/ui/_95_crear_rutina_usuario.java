package com.example.smartcoach.ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartcoach.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class _95_crear_rutina_usuario extends AppCompatActivity {

    ImageView ovaloSup;
    TextView tituloPt2, bienvenida, setTextNombreUser;
    Button btnCrearRutina;
    private final Map<ImageButton, Integer> originalImages = new HashMap<>();
    private final Map<ImageButton, Integer> selectedImages = new HashMap<>();
    private int selectedDay = -1;

    // Elementos inflados del otro diseño
    ImageButton imageLunes, imageMartes, imageMiercoles, imageJueves, imageViernes, imageSabado, imageDomingo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._95_crear_rutina_usuario);
        getSupportActionBar().hide();

        tituloPt2 = findViewById(R.id.tituloPt2);
        bienvenida = findViewById(R.id.bienvenida);
        setTextNombreUser = findViewById(R.id.setTextNombreUser);
        btnCrearRutina = findViewById(R.id.btnCrearRutina);

        // Inflar el diseño del archivo XML secundario
        View diasSemanaView = LayoutInflater.from(this).inflate(R.layout._otros_dias_semana_ne, null);

        // Encuentra los ImageButtons en el diseño secundario
        imageLunes = diasSemanaView.findViewById(R.id.imageLunes);
        imageMartes = diasSemanaView.findViewById(R.id.imageMartes);
        imageMiercoles = diasSemanaView.findViewById(R.id.imageMiercoles);
        imageJueves = diasSemanaView.findViewById(R.id.imageJueves);
        imageViernes = diasSemanaView.findViewById(R.id.imageViernes);
        imageSabado = diasSemanaView.findViewById(R.id.imageSabado);
        imageDomingo = diasSemanaView.findViewById(R.id.imageDomingo);

        // Agrega aquí tu lógica para interactuar con los elementos de ambos diseños
        originalImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_ne);
        originalImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_ne);

        selectedImages.put((ImageButton) findViewById(R.id.imageLunes), R.drawable.icon_lunes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMartes), R.drawable.icon_martes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageMiercoles), R.drawable.icon_miercoles_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageJueves), R.drawable.icon_jueves_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageViernes), R.drawable.icon_viernes_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageSabado), R.drawable.icon_sabado_na);
        selectedImages.put((ImageButton) findViewById(R.id.imageDomingo), R.drawable.icon_domingo_na);
        configureDayClickListeners();

        btnCrearRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_95_crear_rutina_usuario.this, _98_ver_rutina_ejercicio_usuario.class);
                startActivity(intent);
            }
        });
    }

    private void configureDayClickListeners() {
        ImageButton imageLunes = findViewById(R.id.imageLunes);
        ImageButton imageMartes = findViewById(R.id.imageMartes);
        ImageButton imageMiercoles = findViewById(R.id.imageMiercoles);
        ImageButton imageJueves = findViewById(R.id.imageJueves);
        ImageButton imageViernes = findViewById(R.id.imageViernes);
        ImageButton imageSabado = findViewById(R.id.imageSabado);
        ImageButton imageDomingo = findViewById(R.id.imageDomingo);


        imageLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.MONDAY;
                restoreOriginalImages();
                updateSelectedImage(imageLunes);
            }
        });
        imageMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.TUESDAY;
                restoreOriginalImages();
                updateSelectedImage(imageMartes);
            }
        });
        imageMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.WEDNESDAY;
                restoreOriginalImages();
                updateSelectedImage(imageMiercoles);
            }
        });
        imageJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.THURSDAY;
                restoreOriginalImages();
                updateSelectedImage(imageJueves);
            }
        });
        imageViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.FRIDAY;
                restoreOriginalImages();
                updateSelectedImage(imageViernes);
            }
        });
        imageSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = Calendar.SATURDAY;
                restoreOriginalImages();
                updateSelectedImage(imageSabado);
            }
        });
        imageDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreOriginalImages();
                updateSelectedImage(imageDomingo);
                selectedDay = Calendar.SUNDAY;
            }
        });
    }

    private void restoreOriginalImages() {
        for (Map.Entry<ImageButton, Integer> entry : originalImages.entrySet()) {
            ImageButton button = entry.getKey();
            int drawableId = entry.getValue();
            button.setImageResource(drawableId);
        }
    }

    private void updateSelectedImage(ImageButton button) {
        if (button != null && selectedImages.containsKey(button)) {
            int drawableId = selectedImages.get(button);
            button.setImageResource(drawableId);
        }
    }

}

