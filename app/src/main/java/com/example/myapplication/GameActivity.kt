package com.example.myapplication

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var diceImage3: ImageView
    private lateinit var diceImage4: ImageView
    private lateinit var diceImage5: ImageView
    private lateinit var diceImage6: ImageView

    private lateinit var betBoard: Spinner
    private lateinit var rollDiceButton: Button
    private lateinit var gameResultText: TextView
    private lateinit var playerFundsText: TextView
    private lateinit var betAmountInput: EditText
    private lateinit var resultEmoji: ImageView
    private lateinit var resultEmoji1: ImageView

    private var playerFunds: Int = 0
    private var diceCount: Int = 2
    private var winsInARow = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Inicializar las vistas
        initViews()

        // Obtener datos desde la MainActivity
        playerFunds = intent.getIntExtra("funds", 0)
        diceCount = intent.getIntExtra("diceCount", 2)

        diceImage1 = findViewById(R.id.dadouno)
        diceImage2 = findViewById(R.id.dadodos)
        diceImage3 = findViewById(R.id.dadotres)
        /*diceImage1 = findViewById(R.id.dadocuatro)
        diceImage2 = findViewById(R.id.dadocinco)
        diceImage3 = findViewById(R.id.dadoseis)*/

        betBoard = findViewById(R.id.betBoard)
        rollDiceButton = findViewById(R.id.rollDiceButton)
        gameResultText = findViewById(R.id.gameResultText)
        betAmountInput = findViewById(R.id.betAmountInput)

        // Configurar el tablero de apuestas según la cantidad de dados
        configureBetBoard(diceCount)

        // Mostrar el fondo de apuestas
        playerFundsText.text = "Fondo de Apuestas: ₡$playerFunds"

        // Mostrar o ocultar el tercer dado según la cantidad seleccionada
        diceImage3.visibility = if (diceCount == 3) ImageView.VISIBLE else ImageView.GONE

        // Configura el botón para lanzar los dados
        rollDiceButton.setOnClickListener {
            placeBet()
        }
    }

    private fun configureBetBoard(diceCount: Int) {

    }

    // Inicializa todas las vistas
    private fun initViews() {
        diceImage1 = findViewById(R.id.dadouno)
        diceImage2 = findViewById(R.id.dadodos)
        diceImage3 = findViewById(R.id.dadotres)
       /* diceImage4 = findViewById(R.id.dadocuatro)
        diceImage5 = findViewById(R.id.dadocinco)
        diceImage6 = findViewById(R.id.dadoseis)*/

        betBoard = findViewById(R.id.betBoard)
        rollDiceButton = findViewById(R.id.rollDiceButton)
        gameResultText = findViewById(R.id.gameResultText)
        betAmountInput = findViewById(R.id.betAmountInput)
        playerFundsText = findViewById(R.id.playerFundsText)
        /*resultEmoji = findViewById(R.id.resultEmoji*/
    }

    // Ejecuta la lógica de la apuesta
    private fun placeBet() {


        val betAmount = betAmountInput.text.toString().toIntOrNull() ?: 0

        // Validar que la apuesta sea válida
        if (betAmount <= 0) {
            Toast.makeText(this, "El monto de la apuesta debe ser mayor que cero.", Toast.LENGTH_SHORT).show()
            return
        }
        if (betAmount > playerFunds) {
            Toast.makeText(this, "El monto no puede ser mayor a tu fondo.", Toast.LENGTH_SHORT).show()
            return
        }
        val selectedBetId = findViewById<RadioGroup>(R.id.betRadioGroup).checkedRadioButtonId
        val selectedBet = findViewById<RadioButton>(selectedBetId).text.toString().toInt()


        // Tirar los dados y calcular el total
        val dice1 = Random.nextInt(1, 7)
        val dice2 = Random.nextInt(1, 7)
        var dice3 = 0
        if (diceCount == 6) {
            dice3 = Random.nextInt(1, 7)
            setDiceImage(diceImage6, dice3)
        }

        setDiceImage(diceImage1, dice1)
        setDiceImage(diceImage2, dice2)
        setDiceImage(diceImage3, dice3)
       /* setDiceImage(diceImage4, dice4)
        setDiceImage(diceImage5, dice5)
        setDiceImage(diceImage6, dice6)*/

        val total = dice1 + dice2 + dice3
        gameResultText.text = "Resultado: $total"

        // Si el jugador gana
        if (total == selectedBet) {
            playerFunds += betAmount * 2
            winsInARow++

            // Establece la imagen de la carita feliz
            resultEmoji.setImageResource(R.mipmap.happy_emoji)
            resultEmoji.visibility = View.VISIBLE // Mostrar carita feliz

            // Animación para la carita feliz
            val fadeIn = ObjectAnimator.ofFloat(resultEmoji, "alpha", 0f, 1f)
            fadeIn.duration = 500 // 500 milisegundos
            fadeIn.start()

            gameResultText.text = "¡Ganaste ₡${betAmount * 2}!"
        } else {
            // Si el jugador pierde
            playerFunds -= betAmount
            winsInARow = 0

            // Establece la imagen de la carita triste
            resultEmoji1.setImageResource(R.mipmap.sad_emoji)
            resultEmoji1.visibility = View.VISIBLE // Mostrar carita triste

            // Animación para la carita triste
            val fadeIn = ObjectAnimator.ofFloat(resultEmoji, "alpha", 0f, 1f)
            fadeIn.duration = 500 // 500 milisegundos
            fadeIn.start()

            gameResultText.text = "Perdiste ₡$betAmount."
        }

        playerFundsText.text = "Fondo de Apuestas: ₡$playerFunds"

        // Verificar si el juego termina
        checkEndGame()
    }

    private fun setDiceImage(imageView: ImageView, value: Int) {
        val diceDrawable = when (value) {
            1 -> R.mipmap.dadouno
            2 -> R.mipmap.dadodos
            3 -> R.mipmap.dadotres
            4 -> R.mipmap.dadocuatro
            5 -> R.mipmap.dadocinco
            else -> R.mipmap.dadoseis
        }
        imageView.setImageResource(diceDrawable)
    }

    private fun checkEndGame() {
        when {
            winsInARow >= 3 -> showEndMessage("Eres un ganador")
            playerFunds == 0 -> showEndMessage("Lo perdiste todo….No vuelvas a jugar!")
            playerFunds > intent.getIntExtra("funds", 0) -> showEndMessage("Te salvaste…")
            else -> showEndMessage("No deberías de jugar…Retírate")
        }
    }
    
    private fun showEndMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        rollDiceButton.isEnabled = false
    }
}
