package com.example.composition.domain.usecases

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings{
        return gameRepository.getGameSettings(level)
    }
}