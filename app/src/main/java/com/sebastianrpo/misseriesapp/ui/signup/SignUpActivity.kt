package com.sebastianrpo.misseriesapp.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sebastianrpo.misseriesapp.R
import com.sebastianrpo.misseriesapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        val view = signUpBinding.root
        setContentView(view)
        signUpViewModel.setContext(this)
        signUpBinding.dateBirthEditText.setOnClickListener { showDatePickerDialog() }
        signUpBinding.registerButton.setOnClickListener {
            try {
                validarCampos()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                signUpBinding.infoTextView.text = ""
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val selectedMonth = month + 1
        signUpBinding.dateBirthEditText.setText("$day/$selectedMonth/$year")
    }

    private fun validarCampos() {
        val nombre = signUpBinding.nameEditText.text.toString()
        val correo = signUpBinding.emailEditText.text.toString()
        val password = signUpBinding.passwordEditText.text.toString()
        val repetirPassword = signUpBinding.repPasswordEditText.text.toString()

        val dateOfBirth = signUpBinding.dateBirthEditText.text.toString()
        if (dateOfBirth.isEmpty()) {
            Toast.makeText(
                this,
                "Por favor ingrese la fecha de nacimiento",
                Toast.LENGTH_SHORT
            ).show()
        }
        val placeOfBirth = signUpBinding.placeBirthSpinner.selectedItem.toString()
        if (placeOfBirth == "-") {
            Toast.makeText(
                this,
                "Por favor ingrese la ciudad de nacimiento",
                Toast.LENGTH_SHORT
            ).show()
            signUpBinding.infoTextView.text = ""
        }

        if (signUpViewModel.validarNulls(nombre, correo, password, repetirPassword)) {
            if (signUpViewModel.validarPassword(password, repetirPassword)) {
                Toast.makeText(this, getString(R.string.passwords_dont_match), Toast.LENGTH_SHORT)
                    .show()
                signUpBinding.infoTextView.text = ""
            } else {
                if (signUpViewModel.validarEmail(correo)) {
                    try {
                        signUpViewModel.guardarDatos(
                            nombre,
                            correo,
                            password,
                            signUpBinding.genreRadioGroup.checkedRadioButtonId,
                            signUpBinding.actionCheckBox.isChecked,
                            signUpBinding.adventureCheckBox.isChecked,
                            signUpBinding.comedyCheckBox.isChecked,
                            signUpBinding.romanceCheckBox.isChecked,
                            signUpBinding.suspenseCheckBox.isChecked,
                            signUpBinding.horrorCheckBox.isChecked,
                            placeOfBirth,
                            dateOfBirth
                        )
                        signUpBinding.infoTextView.text = signUpViewModel.obtenerDatos()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        signUpBinding.infoTextView.text = ""
                    }
                } else {
                    Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT)
                        .show()
                    signUpBinding.infoTextView.text = ""
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.blank_fields), Toast.LENGTH_SHORT).show()
            signUpBinding.infoTextView.text = ""
        }

    }
}