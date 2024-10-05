package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        String sql = String.format("insert into jdbc_student(id,name,gender,age) values ('%s', '%s', '%s', %d)",
                student.getId(),
                student.getName(),
                student.getGender(),
                student.getAge());

        log.debug("save:{}",sql);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();){
            int result = statement.executeUpdate(sql);
            log.debug("save:{}",result);
            return  result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String sql = String.format("SELECT * FROM jdbc_student WHERE id = '%s'", id);
        log.debug("findById: {}",sql);

        try (Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
            if(resultSet.next())
            {
                Student student = new Student(resultSet.getString("id"),
                        resultSet.getString("name"),
                        Student.GENDER.valueOf(resultSet.getString("gender")),
                        resultSet.getInt("age"),
                        resultSet.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        String sql = String.format("UPDATE jdbc_student SET name = '%s', gender = '%s', age ='%d' WHERE id = '%s'",
         student.getName(), student.getGender().name(), student.getAge(), student.getId());

        log.debug("save:{}",sql);

        try (Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement()){
            int result = statement.executeUpdate(sql);
            log.debug("save:{}",result);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id){
       //todo#4 student 삭제
        String sql = String.format("DELETE FROM jdbc_student WHERE id='%s'",id);
        try(Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(sql);
            log.debug("save:{}",result);

            return  result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
