package com.ulp.loginobjetoutputtp2.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.loginobjetoutputtp2.model.Usuario;
import com.ulp.loginobjetoutputtp2.request.ApiCliente;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>(); // Email observable no se si dejarlo, VER OJO
    public MutableLiveData<String> password = new MutableLiveData<>(); // Password observable
    private MutableLiveData<Usuario> loginSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public void login(Context context, String mail, String password) {
        if (isInputValid(mail, password)) {  // valido
            Usuario usuario = ApiCliente.login(context, mail, password); // autentico usuario
            if (usuario != null) {
                loginSuccess.setValue(usuario);  //  exito
            } else {
                loginError.setValue("Credenciales incorrectas");  // AVISO error
            }
        } else {
            loginError.setValue("Por favor, ingrese su correo y contraseña");  // aviso si hay campo vacio ERROR
        }
    }

    private boolean isInputValid(String mail, String password) {
        return !mail.trim().isEmpty() && !password.trim().isEmpty();  // valido que los campos no esten vacios


    }
}
