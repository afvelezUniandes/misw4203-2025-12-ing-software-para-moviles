package com.example.vinilos.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinilos.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_collectorsTabIsAccessible() {
        // Verificar que estamos en la pantalla principal
        composeTestRule.onNodeWithText("VinylWave").assertIsDisplayed()

        // Hacer clic en la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun test_collectorsAreDisplayed() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificar que se muestra al menos un coleccionista
        val nodes = composeTestRule.onAllNodesWithTag("collector_item").fetchSemanticsNodes()
        assert(nodes.size >= 1) { "Se esperaba al menos 1 coleccionista, pero se encontraron ${nodes.size}" }

        // Verificar que no hay error en la pantalla
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()
        composeTestRule.onNodeWithText("Reintentar").assertDoesNotExist()
    }

    @Test
    fun test_collectorItemClick() {
        // Navegar a la pestaña de coleccionistas
        composeTestRule.onNodeWithText("Coleccionistas").performClick()

        // Esperar a que cargue la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("collector_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verificar que podemos hacer clic en un coleccionista (pero sin esperar navegación)
        // Esto solo verifica que el componente es clicable
        composeTestRule.onAllNodesWithTag("collector_item")[0].assertHasClickAction()

        // Opcionalmente, podemos hacer clic para ver que no hay crash
        composeTestRule.onAllNodesWithTag("collector_item")[0].performClick()

        // Verificar que seguimos en la lista de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodesWithTag("collector_item").fetchSemanticsNodes().isNotEmpty()
        }
    }
}