package com.ruslan.crudapp.repository.database;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.model.Writer;
import com.ruslan.crudapp.repository.WriterRepository;
import com.ruslan.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCWriterRepository implements WriterRepository {


    private String SQL=null;
    private String SQLForTransition=null;


    @Override
    public List<Writer> getAll() {
        SQL = "SELECT p.*, pl.*, l.* FROM writers p LEFT JOIN writer_post pl on p.id = pl.writer_id\n" +
                "                 LEFT JOIN posts l on pl.post_id = l.id\n";
        List<Post> posts = new ArrayList<>();
        Post post = null;
        Writer writer = null;
        JDBCPostRepository postRepository = new JDBCPostRepository();

        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                post = postRepository.getById(resultSet.getInt("post_id"));
                posts.add(post);
            }

            List<Writer> result = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {
                writer = new Writer(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        posts);
                result.add(writer);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Writer getById(Integer id) {
        SQL = "SELECT p.*, pl.*, l.* FROM writers p LEFT JOIN writer_post pl on p.id = pl.writer_id\n" +
                "                 LEFT JOIN posts l on pl.post_id = l.id\n" +
                "                 WHERE p.id =?";

        List<Post> posts = new ArrayList<>();
        Post post = null;
        JDBCPostRepository postRepository = new JDBCPostRepository();
        Writer result = null;
        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                post = postRepository.getById(resultSet.getInt("post_id"));
                posts.add(post);
            }
            resultSet.beforeFirst();
            while (resultSet.next()) {
                result = new Writer(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        posts);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Writer save(Writer writer) {
        SQL = "INSERT INTO writers (firstName, lastName) VALUES (?, ?)";
        SQLForTransition = "INSERT INTO writer_post (writer_id, post_id) VALUES (?, ?)";

        Post post = null;
        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatementWithKeys(SQL);
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Создание записи не удалось, ни одна строка не была изменена.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                writer.setId(generatedKeys.getInt(1));
            }

            statement = JdbcUtils.getPreparedStatement(SQLForTransition);
            List<Post> posts = writer.getPosts();
            for (int i = 0; i < posts.size(); i++) {
                statement.setInt(1, writer.getId());
                post = posts.get(i);
                statement.setInt(2, post.getId());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        String SQL = "UPDATE writers SET firstName = ?,lastName = ? WHERE id = ?";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        String formattedDate = sdf.format(date);
        float a = Float.valueOf(formattedDate);
        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {
            statement.setString(1, writer.getFirstName());
            statement.setInt(3, writer.getId());
            statement.setString(2, writer.getLastName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public void deleteById(Integer id) {
        SQL = "DELETE FROM writers WHERE id = ?";

        try(PreparedStatement statement = JdbcUtils.getPreparedStatement(SQL)) {


            statement.setInt(1,id );
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
