package com.ulp.loginobjetoutputtp2.ui.registro;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.loginobjetoutputtp2.model.Usuario;
import com.ulp.loginobjetoutputtp2.request.ApiCliente;

public class RegistroActivityViewModel extends AndroidViewModel {

    // variable q voy a observar
    private MutableLiveData<Usuario> usuarioActual = new MutableLiveData<>();
    private MutableLiveData<Boolean> registroSuccess = new MutableLiveData<>();
    private MutableLiveData<String> registroError = new MutableLiveData<>();

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getUsuarioActual() {
        return usuarioActual;
    }

    public LiveData<Boolean> getRegistroSuccess() {
        return registroSuccess;
    }

    public LiveData<String> getRegistroError() {
        return registroError;
    }

    public void cargarUsuario(Usuario usuario) {
        usuarioActual.setValue(usuario);
    }

    public String obtenerValorDni(Usuario usuario) {
        return (usuario != null && usuario.getDni() != -1) ? String.valueOf(usuario.getDni()) : "";
    }

    public void registrarUsuario(Context context, Usuario usuario) {
        if (areFieldsValid(usuario)) {  // Validfo campos
            try {
                ApiCliente.guardar(context, usuario);  // guardo usuario
                registroSuccess.setValue(true);  // login exitoso
            } catch (Exception e) {
                registroError.setValue("Error al guardar los datos");  //  error
            }
        } else {
            registroError.setValue("Todos los campos son obligatorios");  //  error
        }
    }

    private boolean areFieldsValid(Usuario usuario) {
        return usuario.getDni() > 0 &&
                !usuario.getApellido().trim().isEmpty() &&
                !usuario.getNombre().trim().isEmpty() &&
                !usuario.getMail().trim().isEmpty() &&
                !usuario.getPassword().trim().isEmpty();
    }
}
