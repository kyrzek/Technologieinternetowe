package pl.lipov.technologieinternetowe.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.lipov.technologieinternetowe.domain.model.Fraction

interface FractionRepository {
    fun getAllFractions(): Flow<List<Fraction>>
}