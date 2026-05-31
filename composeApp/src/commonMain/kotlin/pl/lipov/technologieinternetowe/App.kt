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
// PIERWOTNA BAZA (DANE STARTOWE)
// ==========================================

val pierwotnaBazaFrakcji = listOf(
    Frakcja(
        tytul = "Astartes",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/astartes.png",
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
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/chaos.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Zdeprawowane siły czerpiące moc z wymiaru zwanego Osnową. Bogowie Chaosu kuszą śmiertelników potęgą, zmieniając ich w demony i heretyków, których jedynym celem jest zniszczenie galaktyki.")
        )
    ),
    Frakcja(
        tytul = "Orkowie",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/orcs.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Wielcy, zieloni i brutalni. Orkowie żyją tylko dla walki. Nie znają strachu, a ich siła bierze się z dziwnej mocy psychicznej, która sprawia, że jeśli wierzą, że coś działa (np. broń zrobiona ze złomu) - to to działa.")
        )
    ),
    Frakcja(
        tytul = "Aeldari",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aeldari.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Kiedyś władcy galaktyki, dziś wymierająca rasa uchodźców o szpiczastych uszach (kosmiczne elfy). Posiadają niezwykle zaawansowaną technologię i polegają na precyzyjnych, błyskawicznych uderzeniach."),
            ElementOpisu.Obrazek(
                url = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/aeld.png",
                podpis = "Wojownik Aeldari"
            ),
            ElementOpisu.Akapit("Ich armie składają się z niezliczonych gwardzistów oraz legendarnych Kosmicznych Marines. Każdego dnia na tysiącach planet toczą się krwawe zmagania w obronie ludzkości przed obcymi i demonami."),
            ElementOpisu.Akapit("Technologia Imperium jest w stagnacji - opiera się na reliktach z Mrocznych Wieków Technologii. Konstrukcją i naprawą maszyn zajmują się hermetyczni Kapłani Maszyny (Adeptus Mechanicus).")
        )
    ),
    Frakcja(
        tytul = "Nekroni",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/necron.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Miliony lat temu zamienili swoje ciała na niezniszczalne, metalowe szkielety. Przebudzili się ze snu stazy, by odzyskać dawną chwałę. Mają broń, która rozpada materię na atomy.")
        )
    ),
    Frakcja(
        tytul = "Tyranidzi",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/tyranid.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Pozagalaktyczny rój potworów kierowany przez jeden Umysł Roju. Nie budują statków z metalu - wszystko u nich jest biologiczne. Pożerają całe planety do gołej skały, wchłaniając biomasę.")
        )
    ),
    Frakcja(
        tytul = "T'au",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/tau.png",
        elementyOpisu = listOf(
            ElementOpisu.Akapit("Młoda, bardzo dynamicznie rozwijająca się rasa kosmitów. Ich filozofią jest 'Większe Dobro'. Nie walczą wręcz, polegają na gigantycznych mechach bojowych i potężnych działach plazmowych.")
        )
    ),
    Frakcja(
        tytul = "Votann",
        imageUrl = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/votann.png",
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
    // Zamiast całego obiektu przekazujemy tylko tytuł, aby dynamicznie pobierać zaktualizowaną wersję ze stanu
    data class Szczegoly(val tytulFrakcji: String) : Ekran()
}

// ==========================================
// GŁÓWNA APLIKACJA
// ==========================================

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .build()
    }

    var aktualnyEkran by remember { mutableStateOf<Ekran>(Ekran.Lista) }

    // SYSTEM EDYCJI: Zamieniamy statyczną listę w stan, który pozwala na modyfikację danych w locie
    var listaFrakcji by remember { mutableStateOf(pierwotnaBazaFrakcji) }

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

                if (czyPokazacBoki) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/background.png",
                            contentDescription = "Lewy bok",
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/background.png",
                            contentDescription = "Prawy bok",
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                        .widthIn(max = 960.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF1E2124))
                ) {
                    when (val ekran = aktualnyEkran) {
                        is Ekran.Lista -> {
                            WidokGlownegoMenu(
                                dynamicznaLista = listaFrakcji,
                                naKlikniecieWKarte = { kliknietaFrakcja ->
                                    aktualnyEkran = Ekran.Szczegoly(kliknietaFrakcja.tytul)
                                }
                            )
                        }
                        is Ekran.Szczegoly -> {
                            // Szukamy frakcji w naszym edytowalnym stanie za pomocą tytułu
                            val wybranaFrakcja = listaFrakcji.find { it.tytul == ekran.tytulFrakcji } ?: listaFrakcji.first()

                            WidokSzczegolowFrakcji(
                                frakcja = wybranaFrakcja,
                                naPowrot = { aktualnyEkran = Ekran.Lista },
                                naZapiszZmiany = { zaktualizowanaFrakcja ->
                                    // Podmieniamy starą frakcję na nową (z edytowanym tekstem) na liście stanu
                                    listaFrakcji = listaFrakcji.map {
                                        if (it.tytul == zaktualizowanaFrakcja.tytul) zaktualizowanaFrakcja else it
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

// ==========================================
// WIDOK 1: MENU GŁÓWNE (SIATKA KART)
// ==========================================

@Composable
fun WidokGlownegoMenu(dynamicznaLista: List<Frakcja>, naKlikniecieWKarte: (Frakcja) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {}

        item(span = { GridItemSpan(maxLineSpan) }) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF1E2124))
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/kyrzek/Technologieinternetowe/master/images/wh_logo.png",
                    contentDescription = "Banner główny",
                    modifier = Modifier.fillMaxWidth().height(250.dp).clip(RectangleShape),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Czytamy elementy z przekazanej, modyfikowalnej listy
        items(dynamicznaLista) { pojedynczaFrakcja ->
            GameCard(
                title = pojedynczaFrakcja.tytul,
                imageUrl = pojedynczaFrakcja.imageUrl,
                onClick = { naKlikniecieWKarte(pojedynczaFrakcja) }
            )
        }
    }
}

// ==========================================
// WIDOK 2: ARTYKUŁ + PANEL EDYCJI
// ==========================================

@Composable
fun WidokSzczegolowFrakcji(
    frakcja: Frakcja,
    naPowrot: () -> Unit,
    naZapiszZmiany: (Frakcja) -> Unit
) {
    // Stan określający, czy aktualnie jesteśmy w trybie edycji tekstu
    var czyTrybEdycji by remember { mutableStateOf(false) }

    // Kopia robocza elementów opisu – na niej użytkownik wprowadza zmiany przed kliknięciem "Zapisz"
    var roboczeElementy by remember(frakcja) { mutableStateOf(frakcja.elementyOpisu) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // PANEL PRZYCISKÓW NA GÓRZE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = naPowrot,
                modifier = Modifier.padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("← Wróć do menu", color = Color.LightGray)
            }

            // Przycisk zmieniający się w zależności od trybu
            if (!czyTrybEdycji) {
                Button(
                    onClick = { czyTrybEdycji = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A3F44))
                ) {
                    Text("✏️ Edytuj opis", color = Color.White)
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            // Wywołujemy zapis do głównego stanu aplikacji i wyłączamy edycję
                            naZapiszZmiany(frakcja.copy(elementyOpisu = roboczeElementy))
                            czyTrybEdycji = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)) // Zielony przycisk zapisu
                    ) {
                        Text("💾 Zapisz", color = Color.White)
                    }

                    Button(
                        onClick = {
                            // Resetujemy zmiany i wychodzimy
                            roboczeElementy = frakcja.elementyOpisu
                            czyTrybEdycji = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)) // Czerwony przycisk anulowania
                    ) {
                        Text("Anuluj", color = Color.White)
                    }
                }
            }
        }

        AsyncImage(
            model = frakcja.imageUrl,
            contentDescription = frakcja.tytul,
            modifier = Modifier.fillMaxWidth().height(300.dp).clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = frakcja.tytul,
            style = MaterialTheme.typography.displayMedium,
            color = Color.Red,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // DYNAMICZNE RENDEROWANIE (ZWYKŁY TEKST LUB POLA EDYCYJNE)
        roboczeElementy.forEachIndexed { indeks, element ->
            when (element) {
                is ElementOpisu.Akapit -> {
                    if (czyTrybEdycji) {
                        // Jeśli kliknięto edycję, renderujemy pole tekstowe zamiast czystego tekstu
                        OutlinedTextField(
                            value = element.tekst,
                            onValueChange = { nowyTekst ->
                                // Aktualizujemy tylko ten konkretny akapit w kopii roboczej
                                roboczeElementy = roboczeElementy.toMutableList().apply {
                                    this[indeks] = ElementOpisu.Akapit(nowyTekst)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Red,
                                unfocusedBorderColor = Color.Gray,
                                focusedContainerColor = Color(0xFF2C2F33),
                                unfocusedContainerColor = Color(0xFF23272A)
                            )
                        )
                    } else {
                        // Standardowy widok nieedycyjny
                        Text(
                            text = element.tekst,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                is ElementOpisu.Obrazek -> {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = element.url,
                            contentDescription = element.podpis,
                            modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.FillWidth
                        )
                        if (element.podpis.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = element.podpis,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray,
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
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RectangleShape,
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF1E2124))
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RectangleShape),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}