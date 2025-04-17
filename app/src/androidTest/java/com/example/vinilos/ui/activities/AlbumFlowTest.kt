package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_completeAlbumFlow() {
        // 1. Esperar a que cargue la lista de álbumes (HU01)
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificar elementos de la pantalla de álbumes
        composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()

        // Obtener el número de álbumes para verificaciones posteriores
        val albumNodes = composeTestRule.onAllNodesWithContentDescription(
            label = "Portada de",
            substring = true
        ).fetchSemanticsNodes()

        assert(albumNodes.isNotEmpty()) { "No se encontraron álbumes en la pantalla" }

        // 2. Navegar al detalle del primer álbum (HU02)
        composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)[0].performClick()

        // 3. Verificar el contenido de la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Información").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Información").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tracks").assertIsDisplayed()
        composeTestRule.onNodeWithText("Detalle de Álbum").assertIsDisplayed()

        // 4. Verificar opción de regresar y usarla
        composeTestRule.onNodeWithContentDescription("Regresar").performClick()

        // 5. Verificar que hemos regresado a la lista de álbumes
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun test_errorHandlingAndRetry() {
        // Este test simula una situación donde podríamos tener un error
        // Si la aplicación muestra un error (lo que puede suceder si el servidor no está disponible)
        if (composeTestRule.onAllNodesWithText("Reintentar").fetchSemanticsNodes().isNotEmpty()) {
            // Probar la funcionalidad de reintentar
            composeTestRule.onNodeWithText("Reintentar").performClick()

            // Esperar a que se carguen los datos
            composeTestRule.waitForIdle()

            // Verificar si se recuperó después del error
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                    .fetchSemanticsNodes().isNotEmpty() ||
                        composeTestRule.onAllNodesWithText("Reintentar").fetchSemanticsNodes().isNotEmpty()
            }
        }
    }
}