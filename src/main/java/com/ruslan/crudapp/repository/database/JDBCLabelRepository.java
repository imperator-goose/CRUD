package com.ruslan.crudapp.repository.database;


import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.repository.LabelRepository;
import com.ruslan.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCLabelRepository implements LabelRepository {


    private String SQL=null;

    @Override
    public List<Label> getAll() {
        SQL = "SELECT * FROM labels";
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Label> result = new ArrayList<>();
            Label addToList;
            while (resultSet.next()) {
                addToList = new Label(resultSet.getInt("id"),
                        resultSet.getString("name"));
                result.add(addToList);

            }
            return result;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Label getById(Integer id) {
        SQL = "SELECT * FROM labels WHERE id = ?";
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Label result = null;
            while (resultSet.next()) {
                result = new Label(resultSet.getInt("id"),
                        resultSet.getString("name"));
            }
            return result;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Label save(Label label) {
        SQL = "INSERT INTO labels (id, name) VALUES (?, ?)";
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {

            statement.setInt(1, label.getId());
            statement.setString(2, label.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        SQL = "UPDATE labels SET name = ? WHERE id = ?";

        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {


            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        SQL = "DELETE FROM labels WHERE id = ?";

        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {


            statement.setInt(1,id );
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
