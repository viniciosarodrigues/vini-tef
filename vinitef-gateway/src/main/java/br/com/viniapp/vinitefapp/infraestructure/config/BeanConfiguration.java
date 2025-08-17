package br.com.viniapp.vinitefapp.infraestructure.config;

import br.com.viniapp.vinitefapp.application.usecases.ProcessTransactionUseCaseImpl;
import br.com.viniapp.vinitefapp.domain.ports.in.ProcessTransactionUseCase;
import br.com.viniapp.vinitefapp.domain.ports.out.TefProviderPort;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ProcessTransactionUseCase processTransactionUseCase(TefProviderPort tefProviderPort) {
        return new ProcessTransactionUseCaseImpl(tefProviderPort);
    }

    @Bean
    public Logger getLogger(InjectionPoint p) {
        return LoggerFactory.getLogger(p.getClass().getCanonicalName());
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Vini TEF Solution Service Store")
                        .description("Este é o catálogo de serviços RESTFUL da Vini TEF Solution, para saber mais sobre acesse: [Vini TEF Solution](https://github.com/viniciosarodrigues/vini-tef).")
                        .version("v1.0.0-beta")
                        .license(new License().name("MIT")
                                .url("https://github.com/viniciosarodrigues/vini-tef/blob/master/LICENSE")))
                .externalDocs(new ExternalDocumentation()
                        .description("Vini TEF Solution")
                        .url("https://github.com/viniciosarodrigues/vini-tef"));
    }
}