package com.example.lab13animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import com.example.lab13animaciones.ui.theme.Lab13AnimacionesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab13AnimacionesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Ejercicio1AnimatedVisibility()
                        Divider(thickness = 2.dp)
                        Ejercicio2AnimateColor()
                        Divider(thickness = 2.dp)
                        Ejercicio3TamañoYPosición()
                        Divider(thickness = 2.dp)
                        Ejercicio4AnimatedContent()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Ejercicio1AnimatedVisibility() {
    var visible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ejercicio 1: AnimatedVisibility", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Ocultar cuadro" else "Mostrar cuadro")
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color(0xFF81C784))
            )
        }
    }
}

@Composable
fun Ejercicio2AnimateColor() {
    var isBlue by remember { mutableStateOf(true) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) Color(0xFF64B5F6) else Color(0xFF4CAF50),
        animationSpec = tween(durationMillis = 1000), label = "colorAnim"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ejercicio 2: animateColorAsState", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isBlue = !isBlue }) {
            Text("Cambiar color")
        }

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(backgroundColor)
        )
    }
}

@Composable
fun Ejercicio3TamañoYPosición() {
    var moved by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (moved) 100.dp else 150.dp,
        animationSpec = tween(durationMillis = 600), label = "sizeAnim"
    )

    val offsetX by animateDpAsState(
        targetValue = if (moved) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 600), label = "offsetXAnim"
    )

    val offsetY by animateDpAsState(
        targetValue = if (moved) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 600), label = "offsetYAnim"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ejercicio 3: Tamaño y Posición", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { moved = !moved }) {
            Text("Mover y cambiar tamaño")
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToPx(), offsetY.roundToPx()) }
                .size(size)
                .background(Color(0xFFBA68C8))
        )
    }
}

enum class Estado { Cargando, Contenido, Error }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Ejercicio4AnimatedContent() {


    var estadoActual by remember { mutableStateOf(Estado.Cargando) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ejercicio 4: AnimatedContent", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { estadoActual = Estado.Cargando }) { Text("Cargando") }
            Button(onClick = { estadoActual = Estado.Contenido }) { Text("Contenido") }
            Button(onClick = { estadoActual = Estado.Error }) { Text("Error") }
        }

        AnimatedContent(
            targetState = estadoActual,
            transitionSpec = {
                fadeIn(tween(500)) with fadeOut(tween(500))
            },
            label = "estadoAnimado"
        ) { estado ->
            when (estado) {
                Estado.Cargando -> Text("Cargando...", color = Color.Gray)
                Estado.Contenido -> Text("¡Contenido cargado correctamente!", color = Color.Green)
                Estado.Error -> Text("Error al cargar el contenido.", color = Color.Red)
            }
        }
    }
}
