package pl.lipov.technologieinternetowe.presentation.fractions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import pl.lipov.technologieinternetowe.data.dataSource.FractionDataSource
import pl.lipov.technologieinternetowe.domain.model.Fraction

/**
 * ViewModel zarządzający listą gier na ekranie.
 */
class FractionListModel : ViewModel() {

    // Pobieramy dane bezpośrednio z DataSource dla uproszczenia
    val fractions: StateFlow<List<Fraction>> = FractionDataSource.fractions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}