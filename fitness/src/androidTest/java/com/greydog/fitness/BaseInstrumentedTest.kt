package com.greydog.fitness

import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.common.SingleRootFileSource
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import java.io.File

abstract class BaseInstrumentedTest {
    fun setupWireMockMappings(): WireMockConfiguration {
        val config = WireMockConfiguration.wireMockConfig().port(8080)
        val ctx = InstrumentationRegistry.getInstrumentation().context
        val baseFolder = "${ctx.filesDir}/wiremock"
        val mappingsFolder = "$baseFolder/mappings"

        File(mappingsFolder).deleteRecursively()
        File(mappingsFolder).mkdirs()

        val source = SingleRootFileSource(baseFolder).apply {createIfNecessary()}

        ctx.assets.list("wiremock/mappings")?.forEach { mapping ->
            val mappingText = ctx.assets.open("wiremock/mappings/$mapping").bufferedReader().use { it.readText() }
            source.writeTextFile("mappings/$mapping", mappingText)
        }

        config.fileSource(source)
        return config
    }
}