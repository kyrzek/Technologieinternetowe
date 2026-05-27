package pl.lipov.technologieinternetowe.data.dataSource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.lipov.technologieinternetowe.domain.model.Fraction

/**
 * Lokalne źródło danych przechowujące listę gier (jako obiekty Fraction).
 */
object FractionDataSource {
    private val _fractions = MutableStateFlow(
        listOf(
            Fraction(
                id = "astartes",
                title = "Adeptus Astartes",
            ),
            Fraction(
                id = "sororitas",
                title = "Adepta Sororitas",
            ),
            Fraction(
                id = "custodes",
                title = "Adeptus Custodes",
            ),
            Fraction(
                id = "mechanicus",
                title = "Adeptus Mechanicus",
            )
        )
    )

    val fractions: StateFlow<List<Fraction>> = _fractions.asStateFlow()
}