package com.example.smartcoach.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartcoach.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

public class _45_Registrar_Usuario_4 extends AppCompatActivity {
    TextView titulo, descripcionCC, tituloPeso, kgPeso, tituloAltura, cmAltura, tituloIMC, tituloGrasaCorporal, porcentajeGC, tituloAgua, porcentajeAgua, tituloMasaMuscular, porcentajeMM, tituloMasaOsea, porcentajeMO, tituloMB, tituloGV, tituloMMC, kgMMC, tituloMGC, kgMGC, tituloMasaOseaKg, kgMasaOsea, tituloMasaMuscularKg, kgMM;
    EditText infoPeso, infoAltura, infoIMC, infoGrasaCorporal, infoAgua, infoMasaMuscular, infoMasaOsea, infoMB, infoGV, infoMMC, infoMGC, infoMasaOseaKg, infoMM;
    ImageButton btnInfoPeso, btnInfoAltura, btnInfoMB, btnInfoGC, btnInfoIMC, btnInfoAgua, btnInfoMMP, btnInfoMOP, btnInfoGV, btnInfoMMC, btnInfoMGC, btnInfoMOKG, btnInfoMMKG;
    Button btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._45_registrar_usuario_4);
        getSupportActionBar().hide();

        titulo = findViewById(R.id.titulo);
        descripcionCC = findViewById(R.id.descripcionCC);
        tituloPeso = findViewById(R.id.tituloPeso);
        kgPeso = findViewById(R.id.kgPeso);
        tituloAltura = findViewById(R.id.tituloComposicionCorporal);
        cmAltura = findViewById(R.id.cmAltura);
        tituloIMC = findViewById(R.id.tituloIMC);
        tituloGrasaCorporal = findViewById(R.id.tituloGrasaCorporal);
        porcentajeGC = findViewById(R.id.porcentajeGC);
        tituloAgua = findViewById(R.id.tituloAgua);
        porcentajeAgua = findViewById(R.id.porcentajeAgua);
        tituloMasaMuscular = findViewById(R.id.tituloMasaMuscular);
        porcentajeMM = findViewById(R.id.porcentajeMM);
        tituloMasaOsea = findViewById(R.id.tituloMasaOsea);
        porcentajeMO = findViewById(R.id.porcentajeMO);
        tituloMB = findViewById(R.id.tituloMB);
        tituloGV = findViewById(R.id.tituloGV);
        tituloMMC = findViewById(R.id.tituloMMC);
        kgMMC = findViewById(R.id.kgMMC);
        tituloMGC = findViewById(R.id.tituloMGC);
        kgMGC = findViewById(R.id.kgMGC);
        tituloMasaOseaKg = findViewById(R.id.tituloMasaOseaKg);
        kgMasaOsea = findViewById(R.id.kgMasaOsea);
        tituloMasaMuscularKg = findViewById(R.id.tituloMasaMuscularKg);
        kgMM = findViewById(R.id.kgMM);
        infoPeso = findViewById(R.id.infoPeso);
        infoAltura = findViewById(R.id.infoAltura);
        infoIMC = findViewById(R.id.infoIMC);
        infoGrasaCorporal = findViewById(R.id.infoGrasaCorporal);
        infoAgua = findViewById(R.id.infoAgua);
        infoMasaMuscular = findViewById(R.id.infoMasaMuscular);
        infoMasaOsea = findViewById(R.id.infoMasaOsea);
        infoMB = findViewById(R.id.infoMB);
        infoGV = findViewById(R.id.infoGV);
        infoMMC = findViewById(R.id.infoMMC);
        infoMGC = findViewById(R.id.infoMGC);
        infoMasaOseaKg = findViewById(R.id.infoMasaOseaKg);
        infoMM = findViewById(R.id.infoMM);
        btnInfoPeso = findViewById(R.id.btnInfoPeso);
        btnInfoAltura = findViewById(R.id.btnInfoAltura);
        btnInfoMB = findViewById(R.id.btnInfoMB);
        btnInfoGC = findViewById(R.id.btnInfoGC);
        btnInfoIMC = findViewById(R.id.btnInfoIMC);
        btnInfoAgua = findViewById(R.id.btnInfoAgua);
        btnInfoMMP = findViewById(R.id.btnInfoMMP);
        btnInfoMOP = findViewById(R.id.btnInfoMOP);
        btnInfoGV = findViewById(R.id.btnInfoGV);
        btnInfoMMC = findViewById(R.id.btnInfoMMC);
        btnInfoMGC = findViewById(R.id.btnInfoMGC);
        btnInfoMOKG = findViewById(R.id.btnInfoMOKG);
        btnInfoMMKG = findViewById(R.id.btnInfoMMKG);
        btnSiguiente = findViewById(R.id.btnSiguienteCC);

       btnInfoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoPeso clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoAltura clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoIMC clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnInfoGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoGrasaCorporal clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoAgua clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoMMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaMuscularPorcentaje clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoMOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaOseaPorcentaje clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoMB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMB clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoGrasaVisceral clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfoMMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaMagraCorporal clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoMGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaGrasaCorporal clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoMOKG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaOseaKg clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnInfoMMKG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "btnInfoMasaMuscularKg clickeado");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_45_Registrar_Usuario_4.this);

                View dialogView = getLayoutInflater().inflate(R.layout._50_descripcion_valor_composicion_5, null);
                dialogBuilder.setView(dialogView);

                AlertDialog dialog = dialogBuilder.create();
                AppCompatImageButton btnCerrar = dialogView.findViewById(R.id.btnSalirComposicionCorporal);

                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cierra el diálogo
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_45_Registrar_Usuario_4.this, _59_registrar_usuario_5.class);
                startActivity(intent);
            }
        });

    }
}
