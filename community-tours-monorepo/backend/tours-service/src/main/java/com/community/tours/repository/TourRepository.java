package com.community.tours.repository;

import com.community.tours.model.CreateTourRequest;
import com.community.tours.model.Tour;
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
public class TourRepository {

    private final DataSource dataSource;

    public TourRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Tour save(CreateTourRequest request) {
        String sql = "INSERT INTO tours(name, location, price) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, request.getName());
            statement.setString(2, request.getLocation());
            statement.setBigDecimal(3, request.getPrice());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new Tour(id, request.getName(), request.getLocation(), request.getPrice());
                }
            }

            throw new IllegalStateException("No se pudo recuperar el ID del tour creado");
        } catch (SQLException exception) {
            throw new IllegalStateException("Error al guardar el tour", exception);
        }
    }

    public List<Tour> findAll() {
        String sql = "SELECT id, name, location, price FROM tours ORDER BY id DESC";
        List<Tour> tours = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tours.add(new Tour(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getBigDecimal("price")
                ));
            }

            return tours;
        } catch (SQLException exception) {
            throw new IllegalStateException("Error al listar tours", exception);
        }
    }
}
