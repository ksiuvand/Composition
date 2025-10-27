package com.example.composition.domain.repository

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question

interface GameRepository {

    fun getGameSettings(level: Level): GameSettings

    fun generateGameQuestion(
        maxSumValue: Int,
        countOfOptions: Int,
    ): Question
}