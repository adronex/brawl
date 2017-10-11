package by.brawl

import by.brawl.ws.huihui.SpellsPool
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
open class BrawlServerApplication {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpellsPool.spellsMap.size // initialise companion object
            SpringApplication.run(BrawlServerApplication::class.java, *args)
        }
    }

}