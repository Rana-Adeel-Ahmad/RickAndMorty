package com.example.datasource.cache

import com.example.datasource.cache.model.toCharacter
import com.example.domain.Character

class CharactersCacheImpl(
    charactersDb: CharactersDatabase
) : CharactersCache {

    private val queries = charactersDb.charactersDbQueries

    override suspend fun getAllCharacters(): List<Character> {
        return queries.getAllCharacters().executeAsList().map { it.toCharacter() }
    }

    override suspend fun insertCharacter(character: Character) {
        return character.run {
            queries.insertCharacter(
                id = id.toLong(),
                name = name,
                status = status.status,
                species = species,
                type = type,
                gender = gender.gender,
                origin = origin.name,
                location = location.name,
                imageUrl = imageUrl
            )
        }
    }

    override suspend fun insertCharacters(characters: List<Character>) {
        characters.forEach {
            try {
                insertCharacter(it)
            } catch (e: Exception) {
                // Ignore this character
                e.printStackTrace()
            }
        }
    }
}