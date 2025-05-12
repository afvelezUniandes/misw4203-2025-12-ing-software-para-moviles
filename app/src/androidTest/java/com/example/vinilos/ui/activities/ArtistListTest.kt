package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_artistsTabIsAccessible() {
        // Verificar que estamos en la pantalla principal
        composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()

        // Hacer clic en la pestaña de artistas
        composeTestRule.onNodeWithText("Artistas").performClick()

        // Esperar a que cargue la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("artist_item")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun test_artistsAreDisplayed() {
        // Navegar a la pestaña de artistas
        composeTestRule.onNodeWithText("Artistas").performClick()

        // Esperar a que cargue la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("artist_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificar que se muestra al menos un artista
        val nodes = composeTestRule.onAllNodesWithTag("artist_item").fetchSemanticsNodes()
        assert(nodes.isNotEmpty()) { "Se esperaba al menos 1 artista, pero se encontraron ${nodes.size}" }

        // Verificar que no hay error en la pantalla
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()
        composeTestRule.onNodeWithText("Reintentar").assertDoesNotExist()
    }

    @Test
    fun test_navigateToArtistDetail() {
        // Navegar a la pestaña de artistas
        composeTestRule.onNodeWithText("Artistas").performClick()

        // Esperar a que cargue la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("artist_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer artista
        composeTestRule.onAllNodesWithTag("artist_item")[0].performClick()

        // Verificar que estamos en la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Detalle de Artista")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Regresar a la lista
        composeTestRule.onNodeWithContentDescription("Regresar").performClick()
    }
}