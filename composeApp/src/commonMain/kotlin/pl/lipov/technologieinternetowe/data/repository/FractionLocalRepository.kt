package pl.lipov.technologieinternetowe.data.repository

import kotlinx.coroutines.flow.Flow
import pl.lipov.technologieinternetowe.data.dataSource.FractionDataSource
import pl.lipov.technologieinternetowe.domain.model.Fraction
import pl.lipov.technologieinternetowe.domain.repository.FractionRepository

class FractionLocalRepository : FractionRepository {
    override fun getAllFractions(): Flow<List<Fraction>> = FractionDataSource.fractions
}