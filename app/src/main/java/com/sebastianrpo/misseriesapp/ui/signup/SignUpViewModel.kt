package com.sebastianrpo.misseriesapp.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sebastianrpo.misseriesapp.R

class SignUpViewModel : ViewModel() {
    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }

    private var nombre: String? = null
    private var correo: String? = null
    private var password: String? = null
    private var sexo: String? = null
    private var generos: MutableList<String> = mutableListOf()
    private var lugarNacimiento: String? = null
    private var fechaNacimiento : String? = null

    fun guardarDatos(
        nombre: String,
        correo: String,
        password: String,
        idRadioSexo: Int,
        genre1: Boolean,
        genre2: Boolean,
        genre3: Boolean,
        genre4: Boolean,
        genre5: Boolean,
        genre6: Boolean,
        lugarNacimiento: String,
        fechaNacimiento: String
    ) {

        //limpias la lista para que cada vez que se oprima el boton save se restablezca y no se acumulen
        generos.clear()

        this.nombre = nombre
        this.correo = correo
        this.fechaNacimiento = fechaNacimiento

        // Verificar la longitud de la contraseña
        if (password.length < 6) {
            throw Exception(context?.getString(R.string.invalid_password))
        }

        this.password = password

        sexo = if (idRadioSexo == R.id.female_radio_button) context?.getString(R.string.female) else context?.getString(R.string.male)

        if (genre1) generos.add(context?.getString(R.string.action) ?: "")
        if (genre2) generos.add(context?.getString(R.string.adventure) ?: "")
        if (genre3) generos.add(context?.getString(R.string.comedy) ?: "")
        if (genre4) generos.add(context?.getString(R.string.romance) ?: "")
        if (genre5) generos.add(context?.getString(R.string.suspense) ?: "")
        if (genre6) generos.add(context?.getString(R.string.horror) ?: "")



        this.lugarNacimiento = lugarNacimiento
    }

    fun obtenerDatos(): String {
        var datos = "${context?.getString(R.string.name)} $nombre\n"
        datos += "${context?.getString(R.string.email)} $correo\n"
        datos += "${context?.getString(R.string.genre)} $sexo\n"
        if (generos.isNotEmpty()) {
            datos += "${context?.getString(R.string.favorite_genre)} ${generos.joinToString(", ")}\n"
        }
        datos += "${context?.getString(R.string.birth_date)} $fechaNacimiento\n"
        datos += "${context?.getString(R.string.city_of_birth)} $lugarNacimiento\n"
        return datos
    }

    fun validarNulls(nombre: String, correo: String, password: String, repetirPassword: String): Boolean {
        return !(nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || repetirPassword.isEmpty())
    }

    fun validarEmail(correo: String): Boolean {
        return (esCorreoValido(correo))
    }

    fun validarPassword(password: String, repetirPassword: String): Boolean {
        return (password != repetirPassword)
    }

    fun esCorreoValido(correo: String): Boolean {
        val regex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regex.matches(correo)
    }
}