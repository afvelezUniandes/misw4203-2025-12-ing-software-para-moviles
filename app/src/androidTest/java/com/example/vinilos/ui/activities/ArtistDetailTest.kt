package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistDetailTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_navigateToArtistDetailAndVerifyContent() {
        // Navegar a la pestaña de artistas
        composeTestRule.onNodeWithText("Artistas").performClick()

        // Esperar a que cargue la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("artist_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer artista
        composeTestRule.onAllNodesWithTag("artist_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Detalle de Artista")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificar que los elementos principales del detalle están presentes
        composeTestRule.onNodeWithText("Información").assertIsDisplayed()

        // Verificar que hay una sección de álbumes (si el artista tiene álbumes)
        composeTestRule.onNodeWithText("Álbumes").assertIsDisplayed()

        // Verificar que el botón de regresar está disponible
        composeTestRule.onNodeWithContentDescription("Regresar").assertIsDisplayed()
    }

    @Test
    fun test_returnFromArtistDetail() {
        // Navegar a la pestaña de artistas
        composeTestRule.onNodeWithText("Artistas").performClick()

        // Esperar a que cargue la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("artist_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer artista
        composeTestRule.onAllNodesWithTag("artist_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Detalle de Artista")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Presionar el botón de retorno
        composeTestRule.onNodeWithContentDescription("Regresar").performClick()

        // Verificar que hemos regresado a la lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithText("Artistas").isDisplayed() &&
                    composeTestRule.onAllNodesWithTag("artist_item").fetchSemanticsNodes().isNotEmpty()
        }
    }
}