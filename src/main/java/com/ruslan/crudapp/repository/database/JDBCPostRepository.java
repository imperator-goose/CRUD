package com.ruslan.crudapp.repository.database;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.repository.PostRepository;
import com.ruslan.utils.JdbcUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Post> getAll() {
        SQL = "SELECT * FROM posts";
        SQLForTransition = "SELECT * FROM postlabel";
        List<Label> labels = new ArrayList<>();
        Label label = null;
        Post post = null;
        JDBCLabelRepository labelRepository = new JDBCLabelRepository();

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(SQLForTransition);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                label = labelRepository.getById(resultSet.getInt("label_id"));
                labels.add(label);
            }

            statement = JdbcUtils.getPreparedStatement(SQL);
            resultSet = statement.executeQuery();
            List<Post> result = new ArrayList<>();

            while (resultSet.next()) {
                String enumValue = resultSet.getString("status");
                Status status = fromString(enumValue);
                post = new Post(resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getFloat("created"),
                        resultSet.getFloat("updated"),
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
        SQL = "SELECT * FROM posts WHERE id = ?";
        SQLForTransition = "SELECT * FROM postlabel WHERE post_id = ?";
        List<Label> labels = new ArrayList<>();
        Label label = null;
        JDBCLabelRepository labelRepository = new JDBCLabelRepository();
        Post result = null;
        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(SQLForTransition);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                label = labelRepository.getById(resultSet.getInt("label_id"));
                labels.add(label);
            }
            statement = JdbcUtils.getPreparedStatement(SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();





            while (resultSet.next()) {
                String enumValue = resultSet.getString("status");
                Status status = fromString(enumValue);
                result = new Post(resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getFloat("created"),
                        resultSet.getFloat("updated"),
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
        SQL = "INSERT INTO posts (id, content,created,updated,status) VALUES (?, ?,?,?,?)";
        SQLForTransition = "INSERT INTO postlabel (post_id, label_id) VALUES (?, ?)";
        List<Label> postLabels = post.getLabels();
        Label label = null;
        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL);
            statement.setInt(1, post.getId());
            statement.setString(2, post.getContent());
            statement.setFloat(3, post.getCreated());
            statement.setFloat(4, post.getUpdated());
            statement.setString(5, post.getStatus().name());
            statement.executeUpdate();
            statement = JdbcUtils.getPreparedStatement(SQLForTransition);
            for(int i = 0;i<postLabels.size();i++){
                statement.setInt(1,post.getId());
                label = postLabels.get(i);
                statement.setInt(2,label.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String SQL = "UPDATE posts SET content = ?, updated = ? WHERE id = ?";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        String formattedDate = sdf.format(date);
        float a = Float.valueOf(formattedDate);
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {
            statement.setString(1, post.getContent());
            statement.setInt(3, post.getId());
            statement.setFloat(2, a);

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

