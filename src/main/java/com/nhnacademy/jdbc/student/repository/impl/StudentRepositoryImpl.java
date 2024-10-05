package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        String sql = "INSERT INTO jdbc_student (id, name ,gender, age) VALUES (?,?,?,?)";
        log.debug("sql: {}",sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, student.getId());
            statement.setString(2,student.getName());
            statement.setString(3,student.getGender().toString());
            statement.setInt(4,student.getAge());

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        String sql ="SELECT * FROM jdbc_student WHERE id =?";
        log.debug("sql: {}",sql);
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1,id);
            resultSet = statement.executeQuery();

            if(resultSet.next())
            {
             return Optional.of(new Student(resultSet.getString("id"),
                     resultSet.getString("name"),
                     resultSet.getString("gender").equals("M")? Student.GENDER.M : Student.GENDER.F,
                     resultSet.getInt("age"),
                     resultSet.getTimestamp("created_at").toLocalDateTime()
                     ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        String sql = "UPDATE jdbc_student SET name=?, gender=?, age=? WHERE id=? ";
        log.debug("sql:{}",sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            int index = 0;
            statement.setString(++index,student.getName());
            statement.setString(++index,student.getGender().toString());
            statement.setInt(++index,student.getAge());
            statement.setString(++index,student.getId());

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        String sql ="DELETE FROM jdbc_student WHERE id=?";
        log.debug("sql: {}",sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,id);
            int result = statement.executeUpdate();
            log.debug("result: {}",result);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}