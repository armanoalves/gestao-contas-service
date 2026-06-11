package com.totvs.gestao_contas_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestao de Contas Service")
                        .description(
                                "Microsserviço para gestão de contas a pagar. " +
                                "Gerencia o ciclo de vida completo de contas: " +
                                "criação, edição, pagamento, cancelamento, " +
                                "importação em lote via CSV e geração de relatórios."
                        )
                        .version("1.0.0")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT obtido no endpoint /api/auth/login")
                        )
                        .addSchemas("ErrorResponse", criarSchemaErrorResponse())
                        .addSchemas("FieldError", criarSchemaFieldError())
                );
    }

    private Schema<?> criarSchemaErrorResponse() {
        return new ObjectSchema()
                .name("ErrorResponse")
                .description("Resposta padronizada de erro da API")
                .addProperty("status", new IntegerSchema()
                        .description("Código HTTP do erro")
                        .example(400))
                .addProperty("error", new StringSchema()
                        .description("Descrição do status HTTP")
                        .example("Bad Request"))
                .addProperty("message", new StringSchema()
                        .description("Mensagem descritiva do erro"))
                .addProperty("timestamp", new StringSchema()
                        .description("Timestamp do erro no formato ISO"))
                .addProperty("path", new StringSchema()
                        .description("Caminho da requisição que gerou o erro"))
                .addProperty("errors", new ArraySchema()
                        .description("Erros de validação por campo (quando aplicável)")
                        .items(new Schema<>().$ref("#/components/schemas/FieldError")));
    }

    private Schema<?> criarSchemaFieldError() {
        return new ObjectSchema()
                .name("FieldError")
                .description("Erro de validação de um campo específico")
                .addProperty("field", new StringSchema()
                        .description("Nome do campo com erro"))
                .addProperty("message", new StringSchema()
                        .description("Mensagem de erro do campo"));
    }
}
