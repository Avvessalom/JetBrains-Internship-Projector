package com.gmail.eugene.lazurin.jetBrains_Internship_Projector

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import java.util.concurrent.TimeUnit

class MainPageTest {
    private lateinit var driver: WebDriver
    private val port = 8887
    private val url = "http://localhost/$port"

    @BeforeEach
    fun setUp() {
        System.setProperty("webdriver.chrome.driver",
            "src/main/kotlin/com/gmail/eugene/lazurin/jetBrains_Internship_Projector/drivers/chromedriver")
        driver = ChromeDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.get(url)

    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun test() {
        print("test")
    }

}
