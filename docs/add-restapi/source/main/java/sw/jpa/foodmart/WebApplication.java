package sw.jpa.foodmart;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class WebApplication
{
    private static final Logger LOGGER = LoggerFactory.getLogger( WebApplication.class );
    
    public static void main( String[] args )
    {
        SpringApplication.run( WebApplication.class, args );
    }
    
    @Bean
    public CommandLineRunner commandLineRunner( ApplicationContext ctx )
    {
        return args -> LOGGER.info( "Starting Foodmart Webservices" );
    }
    
    @Bean
    public OpenAPI customOpenAPI(
            @Value( "${openapi.title}" ) String appTitle,
            @Value( "${openapi.description}" ) String appDescription,
            @Value( "${openapi.version}" ) String appVersion )

    {
        return new OpenAPI()
                .components( new Components()
                    .addSecuritySchemes( "bearer-key",
                        new SecurityScheme()
                                .type( SecurityScheme.Type.HTTP )
                                .description( "OAuth2 accessToken" )
                                .scheme( "bearer" )
                                .bearerFormat( "JWT" )
                    ) )
                .addSecurityItem( new SecurityRequirement()
                    .addList( "bearer-jwt", Arrays.asList( "read", "write" ) )
                    .addList( "bearer-key", Collections.emptyList() ) )
                .info( new Info()
                    .title( appTitle )
                    .version( appVersion )
                    .description( appDescription )
                    .termsOfService( "http://swagger.io/terms/" )
                    .license( new License().name( "Apache 2.0" ).url( "http://springdoc.org" ) ) );
    }
}
