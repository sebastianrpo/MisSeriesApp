package com.sebastianrpo.misseriesapp.ui.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sebastianrpo.misseriesapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

        private lateinit var signUpbinding: ActivitySignUpBinding
        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                signUpbinding = ActivitySignUpBinding.inflate(layoutInflater)
                val view = signUpbinding.root
                setContentView(view)

                signUpbinding.dateBirthEditText.setOnClickListener { showDatePickerDialog() }
                signUpbinding.registerButton.setOnClickListener {
                        val name = signUpbinding.nameEditText.text.toString()
                        if (name.isEmpty()) {
                                signUpbinding.nameTextInputLayout.error = "El campo 'Nombre' es obligatorio"
                        } else {
                                signUpbinding.nameTextInputLayout.error = null
                        }
                        val email = signUpbinding.emailEditText.text.toString()
                                if (email.isEmpty()) {
                                        signUpbinding.emailTextInputLayout.error = "El campo 'Correo electrónico' es obligatorio"
                                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                        signUpbinding.emailTextInputLayout.error = "El correo electrónico ingresado no es válido"
                                } else {
                                        signUpbinding.emailTextInputLayout.error = null
                                }
                        val password = signUpbinding.passwordEditText.text.toString()
                        val repPassword = signUpbinding.repPasswordEditText.text.toString()
                        val genre = if (signUpbinding.maleRadioButton.isChecked)
                                "Masculino"
                        else
                                "Femenino"

                        var favoritesGenre = ""
                        if (signUpbinding.actionCheckBox.isChecked) favoritesGenre = "Acción"
                        if (signUpbinding.adventureCheckBox.isChecked) favoritesGenre += ", Aventura"
                        if (signUpbinding.comedyCheckBox.isChecked) favoritesGenre += ", Comedia"
                        if (signUpbinding.romanceCheckBox.isChecked) favoritesGenre += ", Romántico"
                        if (signUpbinding.suspenseCheckBox.isChecked) favoritesGenre += ", Suspenso"
                        if (signUpbinding.horrorCheckBox.isChecked) favoritesGenre += ", Terror"

                        val dateOfBirth = signUpbinding.dateBirthEditText.text.toString()
                        if (dateOfBirth.isEmpty()) {
                                Toast.makeText(
                                        this,
                                        "Por favor ingrese la fecha de nacimiento",
                                        Toast.LENGTH_SHORT
                                ).show()
                        }

                        val placeOfBirth = signUpbinding.placeBirthSpinner.selectedItem.toString()
                        if (placeOfBirth == "Seleccione una ciudad") {
                                Toast.makeText(
                                        this,
                                        "Por favor ingrese la ciudad de nacimiento",
                                        Toast.LENGTH_SHORT
                                ).show()
                        } else {

                                val info =
                                        "Nombre: $name\nCorreo: $email\nContraseña: $password\nGenero: $genre\nGéneros favoritos: $favoritesGenre\nFecha de nacimiento: $dateOfBirth\nLugar de nacimiento: $placeOfBirth"
                                if (password == repPassword)
                                        signUpbinding.infoTextView.setText(info)
                                else {
                                        /*Toast.makeText(applicationContext,"Las contraseñas no son iguales",Toast.LENGTH_LONG).show()
                        signUpbinding.repPasswordTextInputLayout.error = "Las contraseñas no son iguales"*/
                                        Snackbar.make(
                                                signUpbinding.linearLayout,
                                                "Las contraseñas no son iguales",
                                                Snackbar.LENGTH_INDEFINITE
                                        ).setAction("Aceptar") {}.show()
                                }

                        }
                }
        }

        private fun showDatePickerDialog() {
                val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day,month,year) }
                datePicker.show(supportFragmentManager, "datePicker")
        }
        private fun onDateSelected(day:Int, month:Int, year:Int){
                val selectedMonth = month + 1
                signUpbinding.dateBirthEditText.setText("$day/$selectedMonth/$year")

        }

}
