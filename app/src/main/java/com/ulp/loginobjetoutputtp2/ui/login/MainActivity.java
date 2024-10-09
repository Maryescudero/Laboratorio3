package com.ulp.loginobjetoutputtp2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.loginobjetoutputtp2.databinding.ActivityMainBinding;
import com.ulp.loginobjetoutputtp2.model.Usuario;
import com.ulp.loginobjetoutputtp2.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {


    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding binding; // variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Inflo el layout
        setContentView(binding.getRoot());

        // inicializamos el viewModel
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observer el resultado del login
        mainActivityViewModel.getLoginSuccess().observe(this, usuario -> {
            if (usuario != null) {
                goToRegistroActivity(usuario);
            }
        });

        // observer  errores de login
        mainActivityViewModel.getLoginError().observe(this, errorMessage -> {
            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        // evento del botpn ingresar
        binding.btnIngresar.setOnClickListener(view -> {
            String mail = binding.etUsuario.getText().toString();
            String password = binding.etPassword.getText().toString();
            mainActivityViewModel.login(this, mail, password); // llamo metodo
        });

        // evento del boton registrar
        binding.btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void goToRegistroActivity(Usuario usuario) {
        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        intent.putExtra("usuario", usuario); // aqui uso el serializable
        startActivity(intent);
        finish();
    }
}