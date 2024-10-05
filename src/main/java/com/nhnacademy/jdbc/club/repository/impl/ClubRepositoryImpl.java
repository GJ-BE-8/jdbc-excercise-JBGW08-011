package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.repository.ClubRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class ClubRepositoryImpl implements ClubRepository {

    @Override
    public Optional<Club> findByClubId(Connection connection, String clubId) {
        //todo#3 club 조회
        String sql = "SELECT * FROM jdbc_club WHERE club_id=?";
        log.debug("sql: {}",sql);
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,clubId);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                return Optional.of(new Club(resultSet.getString("club_id"),
                        resultSet.getString("club_name"),
                        resultSet.getTimestamp("club_created_at").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.empty();
    }

    @Override
    public int save(Connection connection, Club club) {
        //todo#4 club 생성, executeUpdate() 결과를 반환
        String sql = "INSERT INTO jdbc_club (club_id, club_name) VALUES (?,?)";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,club.getClubId());
            statement.setString(2,club.getClubName());

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Connection connection, Club club) {
        //todo#5 club 수정, clubName을 수정합니다. executeUpdate()결과를 반환
        String sql = "UPDATE jdbc_club SET club_name=? WHERE club_id=?";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,club.getClubName());
            statement.setString(2,club.getClubId());

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByClubId(Connection connection, String clubId) {
        //todo#6 club 삭제, executeUpdate()결과 반환
        String sql = "DELETE FROM jdbc_club WHERE club_id=?";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,clubId);

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int countByClubId(Connection connection, String clubId) {
        //todo#7 clubId에 해당하는 club의 count를 반환
        String sql = "SELECT COUNT(*) FROM jdbc_club WHERE club_id =?";
        log.debug("sql: {}",sql);
        ResultSet resultSet = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,clubId);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
