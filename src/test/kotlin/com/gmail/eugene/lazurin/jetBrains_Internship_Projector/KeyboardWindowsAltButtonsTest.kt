package com.gmail.eugene.lazurin.jetBrains_Internship_Projector

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import com.codeborne.selenide.Condition.appear
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.Keys
import java.awt.event.KeyEvent

class KeyboardWindowsAltButtonsTest {
    private companion object {

        private fun withReadableException(events: List<KeyEvent?>, checks: (events: List<KeyEvent?>) -> Unit) {
            try {
                checks(events)
            } catch (e: AssertionError) {
                val eventsString = events.joinToString(separator = "\n", prefix = "[\n", postfix = "\n]")
                throw AssertionError("exception when checking the following events (see cause): $eventsString", e)
            }
        }

        private fun checkEvent(
            actual: KeyEvent?,
            id: Int,
            keyCode: Int,
            keyChar: Char,
            keyLocation: Int?,
            modifiersEx: Int
        ) {
            if (actual != null) {
                assertEquals(id, actual.id)
            }
            if (actual != null) {
                assertEquals(keyCode, actual.keyCode)
            }
            // keyText is generated from keyCode so no need to compare it
            if (actual != null) {
                assertEquals(
                    keyChar,
                    actual.keyChar,
                    "expected int: ${keyChar.toInt()} but was int: ${actual.keyChar.toInt()}"
                )
            }
            keyLocation?.let {
                if (actual != null) {
                    assertEquals(it, actual.keyLocation)
                }
            }
            if (modifiersEx >= 0) {
                if (actual != null) {
                    assertEquals(modifiersEx, actual.modifiersEx)
                }
            }
        }

        private fun test(vararg keysToSend: CharSequence, tester: (events: List<KeyEvent?>) -> Unit) {
            val port = 8887
            val url = "http://localhost/$port"

            val keyEvents = Channel<List<KeyEvent?>>()

            open(url)
            element(".window").should(appear)

            element("body").sendKeys(*keysToSend)

            val events = runBlocking { keyEvents.receive() }

            withReadableException(events, tester)
        }
    }

    //keyboards codes https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
    @Test
    fun testWindowsSmile() = test(Keys.chord(Keys.ALT, Keys.NUMPAD1)) {
        assertEquals(3, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 18, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[1], KeyEvent.KEY_TYPED, 97, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[2], KeyEvent.KEY_RELEASED, 18, '☺',KeyEvent.KEY_LOCATION_LEFT, 0)
    }

    @Test
    fun testWindowsMarsSpear() = test(Keys.chord(Keys.ALT, Keys.NUMPAD1, Keys.NUMPAD1)) {
        assertEquals(4, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 18, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[1], KeyEvent.KEY_TYPED, 97, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[2], KeyEvent.KEY_TYPED, 97, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[3], KeyEvent.KEY_RELEASED, 18, '♂', KeyEvent.KEY_LOCATION_LEFT, 0)
    }

    @Test
    fun testWindowsSquare() = test(Keys.chord(Keys.ALT, Keys.NUMPAD2, Keys.NUMPAD1, Keys.NUMPAD9)) {
        assertEquals(5, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 18, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[1], KeyEvent.KEY_TYPED, 98, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[2], KeyEvent.KEY_TYPED, 97, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 105, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 18, '█', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }
}
