package com.example.composition.domain.usecases

import com.example.composition.domain.entities.Question
import com.example.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question{
        return gameRepository.generateGameQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}