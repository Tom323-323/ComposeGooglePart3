package com.tomaslab.composegooglepart3

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class TipUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip(){
        composeTestRule.setContent {
            TipTimeScreen()
        }

        composeTestRule.onNodeWithText("Bill Amount").performTextInput("10")

        composeTestRule.onNodeWithText("Tip (%)").performTextInput("20")

        composeTestRule.onNodeWithText("Tip amount: $2.00").assertExists()
    }

}