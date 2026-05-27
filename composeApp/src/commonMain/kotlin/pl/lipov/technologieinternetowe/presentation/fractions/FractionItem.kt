package pl.lipov.technologieinternetowe.presentation.fractions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.lipov.technologieinternetowe.domain.model.Fraction

@Composable
fun FractionItem(
    fraction: Fraction,
    onClick: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF2D2D2D)) // Ciemne tło "gamingowe"
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Zastępczy Box imitujący okładkę gry
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = fraction.title.take(1),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = fraction.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "ZOBACZ NA GOG",
                color = Color(0xFF00FF00), // Zielony akcent
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}