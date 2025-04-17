package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_navigateToAlbumDetailAndVerifyContent() {
        // Esperar a que carguen los álbumes en la pantalla principal
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer álbum
        composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Álbum").assertExists()
            true
        }

        // Verificar que los elementos principales del detalle están presentes
        composeTestRule.onNodeWithText("Información").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tracks").assertIsDisplayed()

        // Verificar que el botón de regresar está disponible
        composeTestRule.onNodeWithContentDescription("Regresar").assertIsDisplayed()
    }

    @Test
    fun test_returnFromAlbumDetail() {
        // Esperar a que carguen los álbumes en la pantalla principal
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer álbum
        composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Álbum").assertExists()
            true
        }

        // Presionar el botón de retorno
        composeTestRule.onNodeWithContentDescription("Regresar").performClick()

        // Verificar que hemos regresado a la pantalla principal
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithText("VinylWave").assertExists()
            true
        }

        // Confirmar que estamos en la lista de álbumes de nuevo
        composeTestRule.onNodeWithText("Álbumes").assertIsDisplayed()
    }
}