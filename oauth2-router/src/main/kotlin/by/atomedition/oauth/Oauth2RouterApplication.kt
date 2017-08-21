package by.atomedition.oauth

import com.rabbitmq.client.AMQP
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory
import sun.java2d.Disposer.getQueue



@SpringBootApplication
class Oauth2RouterApplication

fun main(args: Array<String>) {
    rabbit()
    SpringApplication.run(Oauth2RouterApplication::class.java, *args)
}

fun rabbit() {
    val factory = ConnectionFactory()
    factory.username = "guest"
    factory.password = "guest"
    factory.host = "172.20.0.2"
    factory.port = 8080
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare("myExchange", "direct", true)
    val queueName = channel.queueDeclare().queue
    channel.queueBind(queueName, "myExchange", "myRoutingKey")
}
