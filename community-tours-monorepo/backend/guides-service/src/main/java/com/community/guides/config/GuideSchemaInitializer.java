package com.community.guides.config;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Context
@Singleton
public class GuideSchemaInitializer {

    private final DataSource dataSource;

    public GuideSchemaInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS guides (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(120) NOT NULL,
                    community VARCHAR(120) NOT NULL,
                    boat_name VARCHAR(120) NOT NULL
                )
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new IllegalStateException("No se pudo inicializar la tabla guides", exception);
        }
    }
}
