package pl.lipov.technologieinternetowe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// --- IMPORTY COIL 3 ---
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
// ----------------------

@Composable
fun App() {
    // KONFIGURACJA COIL: Mówimy Coilowi, żeby używał Ktora do pobierania zdjęć z sieci
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = MaterialTheme.colorScheme.background
            color = Color(0xFF1E2124)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Astronomicon",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                GameCard(
                    title = "Frakcje świata Warhammera",
                    description = "10ed.",
                    imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png"
                )

                GameCard(
                    title = "Astartes",
                    description = "10ed.",
                    imageUrl = ""
                )
            }
        }
    }
}

@Composable
fun GameCard(
    title: String,
    description: String,
    imageUrl: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            // ASYNCIMAGE - Komponent od Coila
            AsyncImage(
                model = imageUrl, // W Coil podajemy po prostu link jako "model"
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}