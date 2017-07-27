package by.atomedition.oauth

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Oauth2RouterApplication

fun main(args: Array<String>) {
    SpringApplication.run(Oauth2RouterApplication::class.java, *args)
}
