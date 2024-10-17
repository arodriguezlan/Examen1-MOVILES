package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.Period
import java.util.Locale



class MainActivity : AppCompatActivity() {
    private lateinit var playerName: EditText
    private lateinit var birthdatePicker: DatePicker
    private lateinit var languageSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerName = findViewById(R.id.playerName)
        birthdatePicker = findViewById(R.id.birthdatePicker)
        languageSwitch = findViewById(R.id.languageSwitch)

        // Configurar el switch de idioma
        languageSwitch.setOnCheckedChangeListener { _, isChecked ->
            changeLanguage(if (isChecked) "en" else "es")
        }

        // Inicializar textos
        updateTexts()

        findViewById<Button>(R.id.ingresarButton).setOnClickListener {
            validateInputs()
        }


    }
    // Cambiar el idioma de la app
    private fun changeLanguage(languageCode: String) {
        val config = resources.configuration
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Actualizar textos en lugar de recrear la actividad
        updateTexts()
    }

    // Método para actualizar los textos en función del idioma
    private fun updateTexts() {
        val titleText: TextView = findViewById(R.id.titleText)
        val birthdateLabel: TextView = findViewById(R.id.birthdateLabel)
        val fundsInput: EditText = findViewById(R.id.fundsInput)
        val warningText: TextView = findViewById(R.id.warningText)
        val ingresarButton: Button = findViewById(R.id.ingresarButton)

        // Cambia los textos según el idioma
        titleText.text = if (Locale.getDefault().language == "en") "Casino" else "Casino"
        birthdateLabel.text = if (Locale.getDefault().language == "en") "Birth Date" else "Fecha Nacimiento"
        fundsInput.hint = if (Locale.getDefault().language == "en") "Enter your initial funds (minimum ₡2,000,000)" else "Ingresa tus fondos iniciales (mínimo ₡2,000,000)"
        ingresarButton.text = if (Locale.getDefault().language == "en") "Enter" else "Ingresar"
        warningText.text = if (Locale.getDefault().language == "en") "Remember that gambling is harmful and can cause addiction." else "Recuerda que los juegos de azar son nocivos y pueden generar adicción."
    }

    private fun validateInputs() {
        val name = playerName.text.toString()
        val birthDate = getBirthDate()

        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val age = calculateAge(birthDate)

        if (age < 21) {
            Toast.makeText(this, "Rechazado por edad. Debe ser mayor de 21 años.", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí se puede proceder a la lógica del juego
        // Iniciar GameActivity y pasar los datos
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("playerName", name) // Puedes pasar el nombre del jugador o lo que necesites
        startActivity(intent)
    }

    private fun getBirthDate(): LocalDate {
        return LocalDate.of(
            birthdatePicker.year,
            birthdatePicker.month + 1,
            birthdatePicker.dayOfMonth
        )
    }

    private fun calculateAge(birthDate: LocalDate): Int {
        val today = LocalDate.now()
        return Period.between(birthDate, today).years
    }



}
