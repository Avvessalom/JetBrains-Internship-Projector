package com.gmail.eugene.lazurin.jetBrains_Internship_Projector

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.openqa.selenium.Keys
import java.awt.event.KeyEvent

class KeyboardLinuxAltButtonsTest {
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

            Selenide.open(url)
            Selenide.element(".window").should(Condition.appear)

            Selenide.element("body").sendKeys(*keysToSend)

            val events = runBlocking { keyEvents.receive() }

            withReadableException(events, tester)
        }
    }

    //keyboards codes https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
    @Test
    fun testLinuxSmileEnter() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "1", "f", "6", "0", "0",Keys.ENTER)) {
        assertEquals(25, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 49, '1', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '1', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 49, '1', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, 'f', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, '0', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[19], KeyEvent.KEY_PRESSED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '0', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[22], KeyEvent.KEY_PRESSED, 10, '\n', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[23], KeyEvent.KEY_TYPED, 0, '\n', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[24], KeyEvent.KEY_RELEASED, 10, '☺', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }

    @Test
    fun testLinuxSmileSpace() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "1", Keys.SPACE)) {
        assertEquals(13, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 49, '1', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '1', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 49, '1', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, 'f', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, '0', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[19], KeyEvent.KEY_PRESSED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '0', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 48, '0', KeyEvent.KEY_LOCATION_STANDARD, 0)


        checkEvent(it[22], KeyEvent.KEY_PRESSED, 8, '\b', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[23], KeyEvent.KEY_TYPED, 0, '\b', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[24], KeyEvent.KEY_RELEASED, 8, '☺', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }

    @Test
    fun testLinuxMarsSpearEnter() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "2", "6", "4", "2", Keys.ENTER)) {
        assertEquals(22, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 52, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 52, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[19], KeyEvent.KEY_PRESSED, 10, '\n', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '\n', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 10, '♂', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }

    @Test
    fun testLinuxMarsSpearSpace() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "1", "1", Keys.SPACE)) {
        assertEquals(22, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 54, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 52, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, '6', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 52, '6', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[19], KeyEvent.KEY_PRESSED, 8, '\b', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '\b', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 8, '♂', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }

    @Test
    fun testLinuxSquareEnter() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "2", "5", "f", "c", Keys.ENTER)) {
        assertEquals(22, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 53, '5', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, '5', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 53, '5', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, 'f', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 67, 'c', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, 'c', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 67, 'c', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[19], KeyEvent.KEY_PRESSED, 10, '\n', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '\n', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 10, '█', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }

    @Test
    fun testLinuxSquareSpace() = test(Keys.chord(Keys.CONTROL, Keys.SHIFT, "u", "2", "1", "9", Keys.SPACE)) {
        assertEquals(22, it.size)
        checkEvent(it[0], KeyEvent.KEY_PRESSED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 128)
        checkEvent(it[1], KeyEvent.KEY_PRESSED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT, 192)
        checkEvent(it[2], KeyEvent.KEY_PRESSED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[3], KeyEvent.KEY_TYPED, 0, 'u', KeyEvent.KEY_LOCATION_UNKNOWN, 192)
        checkEvent(it[4], KeyEvent.KEY_RELEASED, 85, 'u', KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[5], KeyEvent.KEY_RELEASED, 16, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 192)
        checkEvent(it[6], KeyEvent.KEY_RELEASED, 17, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[7], KeyEvent.KEY_PRESSED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[8], KeyEvent.KEY_TYPED, 0, '2', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[9], KeyEvent.KEY_RELEASED, 50, '2', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[10], KeyEvent.KEY_PRESSED, 53, '5', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[11], KeyEvent.KEY_TYPED, 0, '5', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[12], KeyEvent.KEY_RELEASED, 53, '5', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[13], KeyEvent.KEY_PRESSED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[14], KeyEvent.KEY_TYPED, 0, 'f', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[15], KeyEvent.KEY_RELEASED, 70, 'f', KeyEvent.KEY_LOCATION_STANDARD, 0)

        checkEvent(it[16], KeyEvent.KEY_PRESSED, 67, 'c', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[17], KeyEvent.KEY_TYPED, 0, 'c', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[18], KeyEvent.KEY_RELEASED, 67, 'c', KeyEvent.KEY_LOCATION_STANDARD, 0)


        checkEvent(it[19], KeyEvent.KEY_PRESSED, 8, '\b', KeyEvent.KEY_LOCATION_STANDARD, 0)
        checkEvent(it[20], KeyEvent.KEY_TYPED, 0, '\b', KeyEvent.KEY_LOCATION_UNKNOWN, 0)
        checkEvent(it[21], KeyEvent.KEY_RELEASED, 8, '█', KeyEvent.KEY_LOCATION_STANDARD, 0)
    }
}