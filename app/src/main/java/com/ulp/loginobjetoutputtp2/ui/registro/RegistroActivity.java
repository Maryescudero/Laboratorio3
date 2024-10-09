package com.ulp.loginobjetoutputtp2.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.loginobjetoutputtp2.R;
import com.ulp.loginobjetoutputtp2.databinding.ActivityRegistroBinding;
import com.ulp.loginobjetoutputtp2.model.Usuario;
import com.ulp.loginobjetoutputtp2.ui.login.MainActivity;

public class RegistroActivity extends AppCompatActivity {

    private RegistroActivityViewModel registroActivityViewModel;
    private ActivityRegistroBinding binding; // variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater()); // Inflate el layout
        setContentView(binding.getRoot()); // la vista

        // inicializo el viewModel
        registroActivityViewModel = new ViewModelProvider(this).get(RegistroActivityViewModel.class);

        //obttengo datos de usuario del intent
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null) {
            registroActivityViewModel.cargarUsuario(usuario);
        }

        // observo datos del usuartio desde viewModel y actualizo la interfaz
        registroActivityViewModel.getUsuarioActual().observe(this, usuarioActual -> {
            binding.etDni.setText(registroActivityViewModel.obtenerValorDni(usuarioActual));
            binding.etApellido.setText(usuarioActual.getApellido() != null ? usuarioActual.getApellido() : "");
            binding.etNombre.setText(usuarioActual.getNombre() != null ? usuarioActual.getNombre() : "");
            binding.etMail.setText(usuarioActual.getMail() != null ? usuarioActual.getMail() : "");
            binding.etPass.setText(usuarioActual.getPassword() != null ? usuarioActual.getPassword() : "");
        });

        // evento del boton guardar
        binding.btnGuardar.setOnClickListener(view -> {
            Usuario usuarioActualizado = new Usuario(
                    Long.parseLong(binding.etDni.getText().toString().trim()),
                    binding.etApellido.getText().toString().trim(),
                    binding.etNombre.getText().toString().trim(),
                    binding.etMail.getText().toString().trim(),
                    binding.etPass.getText().toString().trim()
            );
            registroActivityViewModel.registrarUsuario(this, usuarioActualizado);  // Llamo al viewModel para registrar el usuario
        });

        //veo si el registr fue exitoso
        registroActivityViewModel.getRegistroSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //veo errores
        registroActivityViewModel.getRegistroError().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }
}