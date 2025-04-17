package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_albumsAreDisplayed() {
        // Esperamos a que desaparezca la pantalla de carga y se muestren los álbumes
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificamos que se muestra al menos un álbum
        val nodes = composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
            .fetchSemanticsNodes()
        assert(nodes.size >= 1) { "Se esperaba al menos 1 álbum, pero se encontraron ${nodes.size}" }

        // Verificamos que no hay error en la pantalla
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()
        composeTestRule.onNodeWithText("Reintentar").assertDoesNotExist()
    }

    @Test
    fun test_navigationTabsAreDisplayed() {
        // Verificamos que los tabs de navegación están presentes
        composeTestRule.onNodeWithText("Álbumes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Artistas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Coleccionistas").assertIsDisplayed()
    }

    @Test
    fun test_appTitleIsDisplayed() {
        // Verificamos que el título de la app está visible
        composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()
    }

    @Test
    fun test_navigateThroughAllAlbums() {
        // Esperar a que carguen los álbumes
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Obtener todos los álbumes disponibles
        val albumNodes = composeTestRule.onAllNodesWithContentDescription(
            label = "Portada de",
            substring = true
        ).fetchSemanticsNodes()

        val totalAlbums = albumNodes.size

        // Navegar por cada uno de los álbumes
        for (i in 0 until totalAlbums) {
            // Verificar que estamos en la pantalla principal
            composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()

            // Esperar a que los álbumes estén cargados (por si acaso)
            composeTestRule.waitUntil(timeoutMillis = 5000) {
                composeTestRule.onAllNodesWithContentDescription(label = "Portada de", substring = true)
                    .fetchSemanticsNodes().isNotEmpty()
            }

            // Verificar que podemos acceder al álbum actual
            composeTestRule.onAllNodesWithContentDescription(
                label = "Portada de",
                substring = true
            )[i].assertExists()

            // Hacer clic en el álbum actual
            composeTestRule.onAllNodesWithContentDescription(
                label = "Portada de",
                substring = true
            )[i].performClick()

            // Verificar que estamos en la pantalla de detalle
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onNodeWithText("Detalle de Álbum").assertExists()
                true
            }

            // Verificar elementos característicos de la pantalla de detalle
            composeTestRule.onNodeWithText("Información").assertIsDisplayed()
            composeTestRule.onNodeWithText("Tracks").assertIsDisplayed()

            // Regresar a la lista de álbumes
            composeTestRule.onNodeWithContentDescription("Regresar").performClick()
        }

        // Verificar que hemos terminado en la pantalla principal
        composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()
    }
}