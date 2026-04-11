package com.community.guides.repository;

import com.community.guides.model.CreateGuideRequest;
import com.community.guides.model.Guide;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GuideRepository {

    private final DataSource dataSource;

    public GuideRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Guide save(CreateGuideRequest request) {
        String sql = "INSERT INTO guides(name, community, boat_name) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, request.getName());
            statement.setString(2, request.getCommunity());
            statement.setString(3, request.getBoatName());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new Guide(id, request.getName(), request.getCommunity(), request.getBoatName());
                }
            }

            throw new IllegalStateException("No se pudo recuperar el ID del guía creado");
        } catch (SQLException exception) {
            throw new IllegalStateException("Error al guardar el guía", exception);
        }
    }

    public List<Guide> findAll() {
        String sql = "SELECT id, name, community, boat_name FROM guides ORDER BY id DESC";
        List<Guide> guides = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                guides.add(new Guide(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("community"),
                        resultSet.getString("boat_name")
                ));
            }

            return guides;
        } catch (SQLException exception) {
            throw new IllegalStateException("Error al listar guías", exception);
        }
    }
}
