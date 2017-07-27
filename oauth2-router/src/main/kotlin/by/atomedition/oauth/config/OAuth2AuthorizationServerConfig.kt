package by.atomedition.oauth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@SpringBootApplication
@Configuration
@EnableAuthorizationServer
open class OAuth2AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    private val tokenStore = InMemoryTokenStore()

    @Autowired
    @Qualifier("authenticationManagerBean")
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!
                .tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService)
                .pathMapping("/oauth/token", "/public/login")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {

        clients!!
                .inMemory()
                .withClient("Lola")
                .secret("Bola")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
    }

}