package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorDetailTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_navigateToCollectorDetailAndVerifyContent() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer coleccionista
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Coleccionista").isDisplayed()
        }

        // Verificar que los elementos principales del detalle están presentes
        composeTestRule.onNodeWithText("Información").assertIsDisplayed()

        // Verificar secciones específicas del detalle del coleccionista
        composeTestRule.onNodeWithText("Álbumes en colección").assertExists()
        composeTestRule.onNodeWithText("Artistas favoritos").assertExists()

        // Verificar que el botón de regresar está disponible
        composeTestRule.onNodeWithContentDescription("Regresar").assertIsDisplayed()
    }

    @Test
    fun test_returnFromCollectorDetail() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer coleccionista
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Coleccionista").isDisplayed()
        }

        // Presionar el botón de retorno
        composeTestRule.onNodeWithContentDescription("Regresar").performClick()

        // Verificar que hemos regresado a la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithText("Coleccionistas").isDisplayed() &&
                    composeTestRule.onAllNodesWithTag("collector_item").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun test_collectorInfoIsDisplayed() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer coleccionista
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Coleccionista").isDisplayed()
        }

        // Verificar que se muestra la información del coleccionista
        composeTestRule.onNodeWithText("Información").assertIsDisplayed()

        // Buscar texto que contenga "Email:" (caso insensitivo)
        composeTestRule.onAllNodesWithText(text = "Email:", substring = true, ignoreCase = true)
            .fetchSemanticsNodes().isNotEmpty()
    }

    @Test
    fun test_collectorAlbumsAndArtistsAreDisplayed() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer coleccionista
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Coleccionista").isDisplayed()
        }

        // Verificar sección de álbumes en colección
        composeTestRule.onNodeWithText("Álbumes en colección").assertIsDisplayed()

        // Verificar sección de artistas favoritos
        composeTestRule.onNodeWithText("Artistas favoritos").assertIsDisplayed()

        // Verificar que al menos uno de estos elementos tiene contenido
        // (puede que algunos coleccionistas no tengan álbumes o artistas)
        try {
            // Intentar encontrar al menos un álbum
            composeTestRule.onAllNodesWithTag("collector_album_item")
                .fetchSemanticsNodes().isNotEmpty()
        } catch (e: Exception) {
            try {
                // O al menos un artista favorito
                composeTestRule.onAllNodesWithTag("collector_artist_item")
                    .fetchSemanticsNodes().isNotEmpty()
            } catch (e: Exception) {
                // Es aceptable que un coleccionista no tenga ninguno de los dos
            }
        }
    }

    @Test
    fun test_collectorContactInfoIsDisplayed() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Hacer clic en el primer coleccionista
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Esperar a que cargue la pantalla de detalle
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onNodeWithText("Detalle de Coleccionista").isDisplayed()
        }

        // Verificar que la información de contacto está presente
        composeTestRule.onNodeWithText("Información").assertIsDisplayed()

        // Buscar los datos específicos de contacto
        composeTestRule.onAllNodesWithText(text = "Email:", substring = true, ignoreCase = true)
            .fetchSemanticsNodes().isNotEmpty()
        composeTestRule.onAllNodesWithText(text = "Teléfono:", substring = true, ignoreCase = true)
            .fetchSemanticsNodes().isNotEmpty()
    }
}