/*package tu.paquete

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tu.paquete.databinding.ActivityResultadoBinding // Cambia esto según tu paquete

class ResultadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa View Binding
        binding = ActivityResultadoBinding.inflate(layoutInflater)


        // Configurar la vista
        binding.estado.text = estadoTexto ?: "Estado no disponible"
        binding.textoResultado.text = textoResultadoTexto ?: "Resultado no disponible"

        // Puedes establecer un icono específico si es necesario
        // binding.iconoJuego.setImageResource(R.drawable.tu_icono)


        // Manejar el botón de salir
        binding.exitButton.setOnClickListener {
            finish() // Cierra la actividad actual
        }

        // Obtener datos del intent
        val estadoTexto = intent.getStringExtra("ESTADO")
        val textoResultadoTexto = intent.getStringExtra("TEXTO_RESULTADO")

    }

}*/
