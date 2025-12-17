package es.fpsumma.dam2.api.ui.screen.tareas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListadoTareasContent(
    ui: TareasUIState,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onOpenDetalle: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de tareas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onAdd) {
                        Icon(Icons.AutoMirrored.Filled.NoteAdd, contentDescription = "Añadir")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            if (ui.loading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
            }

            ui.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            if (ui.tareas.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay tareas aún")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items = ui.tareas, key = { it.id }) { tarea ->
                        Card(
                            onClick = { onOpenDetalle(tarea.id) },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            ListItem(
                                headlineContent = { Text(tarea.titulo) },
                                supportingContent = { Text(tarea.descripcion) },
                                trailingContent = {
                                    IconButton(onClick = { onDelete(tarea.id) }) {
                                        Icon(Icons.Outlined.Delete, contentDescription = "Borrar tarea")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}