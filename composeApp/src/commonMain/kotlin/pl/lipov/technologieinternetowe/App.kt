package pl.lipov.technologieinternetowe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory

// ==========================================
// MODELE DANYCH
// ==========================================

sealed class ElementOpisu {
    data class Akapit(val tekst: String) : ElementOpisu()
    data class Obrazek(val url: String, val podpis: String = "") : ElementOpisu()
}

data class Frakcja(
    val tytul: String,
    val elementyOpisu: List<ElementOpisu>,
    val imageUrl: String
)

// ==========================================
// BAZA FRAKCJI
// ==========================================

val bazaFrakcji = listOf(
    Frakcja(
        tytul = "Astartes",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Największe, galaktyczne mocarstwo w Drodze Mlecznej. Przetrwanie miliardów ludzi opiera się na nieustannej wojnie i wierze w Boga-Imperatora."),
            ElementOpisu.Obrazek(
                url = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
                podpis = "Symbol Imperium (Aquila)"
            ),
            ElementOpisu.Akapit("Ich armie składają się z niezliczonych gwardzistów oraz legendarnych Kosmicznych Marines. Każdego dnia na tysiącach planet toczą się krwawe zmagania w obronie ludzkości przed obcymi i demonami."),
            ElementOpisu.Akapit("Technologia Imperium jest w stagnacji - opiera się na reliktach z Mrocznych Wieków Technologii. Konstrukcją i naprawą maszyn zajmują się hermetyczni Kapłani Maszyny (Adeptus Mechanicus).")
        )
    ),
    Frakcja(
        tytul = "Imperium",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Największe, galaktyczne mocarstwo w Drodze Mlecznej. Przetrwanie miliardów ludzi opiera się na nieustannej wojnie i wierze w Boga-Imperatora."),
            ElementOpisu.Obrazek(
                url = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
                podpis = "Symbol Imperium (Aquila)"
            ),
            ElementOpisu.Akapit("Ich armie składają się z niezliczonych gwardzistów oraz legendarnych Kosmicznych Marines. Każdego dnia na tysiącach planet toczą się krwawe zmagania w obronie ludzkości przed obcymi i demonami."),
            ElementOpisu.Akapit("Technologia Imperium jest w stagnacji - opiera się na reliktach z Mrocznych Wieków Technologii. Konstrukcją i naprawą maszyn zajmują się hermetyczni Kapłani Maszyny (Adeptus Mechanicus).")
        )
    ),
    Frakcja(
        tytul = "Chaos",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Zdeprawowane siły czerpiące moc z wymiaru zwanego Osnową. Bogowie Chaosu kuszą śmiertelników potęgą, zmieniając ich w demony i heretyków, których jedynym celem jest zniszczenie galaktyki.")
        )
    ),
    Frakcja(
        tytul = "Orkowie",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Wielcy, zieloni i brutalni. Orkowie żyją tylko dla walki. Nie znają strachu, a ich siła bierze się z dziwnej mocy psychicznej, która sprawia, że jeśli wierzą, że coś działa (np. broń zrobiona ze złomu) - to to działa.")
        )
    ),
    Frakcja(
        tytul = "Aeldari",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Kiedyś władcy galaktyki, dziś wymierająca rasa uchodźców o szpiczastych uszach (kosmiczne elfy). Posiadają niezwykle zaawansowaną technologię i polegają na precyzyjnych, błyskawicznych uderzeniach.")
        )
    ),
    Frakcja(
        tytul = "Nekroni",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Miliony lat temu zamienili swoje ciała na niezniszczalne, metalowe szkielety. Przebudzili się ze snu stazy, by odzyskać dawną chwałę. Mają broń, która rozpada materię na atomy.")
        )
    ),
    Frakcja(
        tytul = "Tyranidzi",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Pozagalaktyczny rój potworów kierowany przez jeden Umysł Roju. Nie budują statków z metalu - wszystko u nich jest biologiczne. Pożerają całe planety do gołej skały, wchłaniając biomasę.")
        )
    ),
    Frakcja(
        tytul = "T'au",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Młoda, bardzo dynamicznie rozwijająca się rasa kosmitów. Ich filozofią jest 'Większe Dobro'. Nie walczą wręcz, polegają na gigantycznych mechach bojowych i potężnych działach plazmowych.")
        )
    ),
    Frakcja(
        tytul = "Votann",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Największe, galaktyczne mocarstwo w Drodze Mlecznej. Przetrwanie miliardów ludzi opiera się na nieustannej wojnie i wierze w Boga-Imperatora."),
            ElementOpisu.Obrazek(
                url = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
                podpis = "Symbol Imperium (Aquila)"
            ),
            ElementOpisu.Akapit("Ich armie składają się z niezliczonych gwardzistów oraz legendarnych Kosmicznych Marines. Każdego dnia na tysiącach planet toczą się krwawe zmagania w obronie ludzkości przed obcymi i demonami."),
            ElementOpisu.Akapit("Technologia Imperium jest w stagnacji - opiera się na reliktach z Mrocznych Wieków Technologii. Konstrukcją i naprawą maszyn zajmują się hermetyczni Kapłani Maszyny (Adeptus Mechanicus).")
        )
    )

)

// ==========================================
// SYSTEM NAWIGACJI
// ==========================================

sealed class Ekran {
    object Lista : Ekran()
    data class Szczegoly(val frakcja: Frakcja) : Ekran()
}

// ==========================================
// GŁÓWNA APLIKACJA
// ==========================================

@Composable
fun App() {
    // Konfiguracja Coil 3 dla pobierania zdjęć przez Ktor
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .build()
    }

    var aktualnyEkran by remember { mutableStateOf<Ekran>(Ekran.Lista) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF1E2124)
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val czyPokazacBoki = maxWidth > 1000.dp

                // WARSTWA 1: OBRAZY W TLE (Pokazywane tylko na szerokich oknach)
                if (czyPokazacBoki) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
                            contentDescription = "Lewy bok",
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png",
                            contentDescription = "Prawy bok",
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // WARSTWA 2: ŚRODEK Z KARTAMI LUB ARTYKUŁEM
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                        .widthIn(max = 960.dp) // Maksymalna szerokość środkowej kolumny
                        .fillMaxWidth()
                        .background(Color(0xFF1E2124)) // Ciemnoszare tło dla kontrastu
                ) {
                    when (val ekran = aktualnyEkran) {
                        is Ekran.Lista -> {
                            WidokGlownegoMenu(
                                naKlikniecieWKarte = { kliknietaFrakcja ->
                                    aktualnyEkran = Ekran.Szczegoly(kliknietaFrakcja)
                                }
                            )
                        }
                        is Ekran.Szczegoly -> {
                            WidokSzczegolowFrakcji(
                                frakcja = ekran.frakcja,
                                naPowrot = {
                                    aktualnyEkran = Ekran.Lista
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// WIDOK 1: MENU GŁÓWNE (SIATKA KART)
// ==========================================

@Composable
fun WidokGlownegoMenu(naKlikniecieWKarte: (Frakcja) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Główny nagłówek
        item(span = { GridItemSpan(maxLineSpan) }) {
//            Text(
//                //text = "",
////                style = MaterialTheme.typography.headlineLarge,
////                color = Color.White,
////                modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
////                textAlign = TextAlign.Center
//            )
        }

        // ==========================================
        // KARTA TYTUŁOWA (W PEŁNI WYPEŁNIONA OBRAZEM)
        // ==========================================
        item(span = { GridItemSpan(maxLineSpan) }) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aquilla.png", // Twój link do bannera
                    contentDescription = "Banner główny",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Ustawiamy wysokość bannera (możesz dać np. 300.dp, jeśli ma być większy)
                        .clip(RectangleShape),
                    // Używamy "Crop", aby obraz całkowicie i bez pustych pasów wypełnił kartę
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Kafelki generowane z listy (Klikalne)
        items(bazaFrakcji) { pojedynczaFrakcja ->
            GameCard(
                title = pojedynczaFrakcja.tytul,
                imageUrl = pojedynczaFrakcja.imageUrl,
                onClick = { naKlikniecieWKarte(pojedynczaFrakcja) }
            )
        }
    }
}

// ==========================================
// WIDOK 2: ARTYKUŁ (SZCZEGÓŁY FRAKCJI)
// ==========================================

@Composable
fun WidokSzczegolowFrakcji(frakcja: Frakcja, naPowrot: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Przycisk powrotu
        Button(
            onClick = naPowrot,
            modifier = Modifier.padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("← Wróć do menu", color = Color.Gray)
        }

        // Duży, dopasowany obrazek na górze
        AsyncImage(
            model = frakcja.imageUrl,
            contentDescription = frakcja.tytul,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tytuł artykułu
        Text(
            text = frakcja.tytul,
            style = MaterialTheme.typography.displayMedium,
            color = Color.Red,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Generowanie artykułu z dynamicznych klocków
        frakcja.elementyOpisu.forEach { element ->
            when (element) {
                is ElementOpisu.Akapit -> {
                    Text(
                        text = element.tekst,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                is ElementOpisu.Obrazek -> {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = element.url,
                            contentDescription = element.podpis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.FillWidth
                        )
                        if (element.podpis.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = element.podpis,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ==========================================
// KARTA (POJEDYNCZY KAFELEK)
// ==========================================

@Composable
fun GameCard(
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RectangleShape
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RectangleShape),
                contentScale = ContentScale.Fit // Nie ucina zdjęć
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}