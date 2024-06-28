package com.ruslan.crudapp.repository.database;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.repository.PostRepository;
import com.ruslan.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JDBCPostRepository implements PostRepository {



    public static Status fromString(String value) {
        if (value != null) {
            for (Status enumValue : Status.values()) {
                if (value.equalsIgnoreCase(enumValue.name())) {
                    return enumValue;
                }
            }
        }
        return null;
    }
    private String SQL=null;
    private String SQLForTransition=null;

    @Override
    public List getAll() {
        SQL = "SELECT p.*, pl.*, l.* FROM posts p LEFT JOIN postlabel pl on p.id = pl.post_id\n" +
                "                 LEFT JOIN labels l on pl.label_id = l.id\n";
        List<Label> labels = new ArrayList<>();
        Label label = null;
        Post post = null;
        JDBCLabelRepository labelRepository = new JDBCLabelRepository();

        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                label = labelRepository.getById(resultSet.getInt("label_id"));
                labels.add(label);
            }

            List<Post> result = new ArrayList<>();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String enumValue = resultSet.getString("status");
                Status status = fromString(enumValue);
                post = new Post(resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getDate("created"),
                        resultSet.getDate("updated"),
                        status,
                        labels);
                result.add(post);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public Post getById(Integer id) {
        SQL = "SELECT p.*, pl.*, l.* FROM posts p LEFT JOIN postlabel pl on p.id = pl.post_id\n" +
                "                 LEFT JOIN labels l on pl.label_id = l.id\n" +
                "                 WHERE p.id =?";

        List<Label> labels = new ArrayList<>();
        Label label = null;
        JDBCLabelRepository labelRepository = new JDBCLabelRepository();
        Post result = null;
        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                label = labelRepository.getById(resultSet.getInt("label_id"));
                labels.add(label);
            }
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String enumValue = resultSet.getString("status");
                Status status = fromString(enumValue);
                result = new Post(resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getDate("created"),
                        resultSet.getDate("updated"),
                        status,
                        labels);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post save(Post post) {
        String enumValue = String.valueOf(Status.ACTIVE);
        Date date = new Date();
        SQL = "INSERT INTO posts (content, created, updated, status) VALUES (?, ?, ?, ?)";
        SQLForTransition = "INSERT INTO postlabel (post_id, label_id) VALUES (?, ?)";


        Label label = null;
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement statement = JdbcUtils.getPreparedStatementWithKeys(SQL);



            statement.setString(1, post.getContent());
            statement.setString(2, String.valueOf(sqlDate));
            statement.setString(3, String.valueOf(sqlDate));
            statement.setString(4, enumValue);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Создание записи не удалось, ни одна строка не была изменена.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Не удалось получить сгенерированный id.");
            }


            List<Label> postLabels = post.getLabels();
            statement = JdbcUtils.getPreparedStatement(SQLForTransition);
            for (int i = 0; i < postLabels.size(); i++) {
                statement.setInt(1, post.getId());
                label = postLabels.get(i);
                statement.setInt(2, label.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String SQL = "UPDATE posts SET content = ?, updated = ? WHERE id = ?";
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {
            statement.setString(1, post.getContent());
            statement.setInt(3, post.getId());
            statement.setString(2, String.valueOf(sqlDate));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void deleteById(Integer id) {
        SQL = "UPDATE posts SET status = 'DELETED' WHERE id = ?";

        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {


            statement.setInt(1,id );
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

