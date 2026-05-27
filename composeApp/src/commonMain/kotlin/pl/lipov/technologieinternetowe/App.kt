package pl.lipov.technologieinternetowe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp



@Composable
fun App() {
    // 1. Tworzymy domyślną konfigurację (zawiera dekodery dla PNG, JPG itd.)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Strona typu Wiki",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                GameCard(
                    title = "Frakcje świata Warhammera",
                    description = "10ed.",
                    // Używamy bezpiecznego linku do loga Kotlin, aby sprawdzić czy system działa
                    imageUrl = "https://static.wikia.nocookie.net/logopedia/images/6/62/W40K_logo_03.png/revision/latest?cb=20160514073639"
                )



            }
        }
    }
}

@Composable
fun GameCard(
    title: String,
    description: String,
    imageUrl: String)
{
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {

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

@Composable
fun GreetingImage(
    message: String,
    from: String,
    modifier: Modifier = Modifier)
{
    val image = painterResource(R.drawable.androidparty)
}

