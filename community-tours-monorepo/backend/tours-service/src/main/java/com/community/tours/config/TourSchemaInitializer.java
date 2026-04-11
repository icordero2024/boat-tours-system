package com.community.tours.config;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Context
@Singleton
public class TourSchemaInitializer {

    private final DataSource dataSource;

    public TourSchemaInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tours (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(120) NOT NULL,
                    location VARCHAR(120) NOT NULL,
                    price DECIMAL(10, 2) NOT NULL
                )
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new IllegalStateException("No se pudo inicializar la tabla tours", exception);
        }
    }
}
