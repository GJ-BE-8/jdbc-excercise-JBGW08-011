package com.nhnacademy.jdbc.bank.repository.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

    public Optional<Account> findByAccountNumber(Connection connection, long accountNumber){
        //todo#1 계좌-조회
        String sql = "SELECT * FROM jdbc_account WHERE account_number=?";
        log.debug("sql:{}",sql);

        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,accountNumber);
            resultSet = statement.executeQuery();

            if(resultSet.next())
            {
             return Optional.of(new Account(resultSet.getLong("account_number"),
                     resultSet.getString("name"),
                     resultSet.getLong("balance")));
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
    public int save(Connection connection, Account account) {
        //todo#2 계좌-등록, executeUpdate() 결과를 반환 합니다.
        String sql = "INSERT INTO jdbc_account (account_number, name, balance) VALUES (?,?,?)";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,account.getAccountNumber());
            statement.setString(2,account.getName());
            statement.setLong(3,account.getBalance());

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByAccountNumber(Connection connection, long accountNumber){
        int count=0;
        String sql =" SELECT COUNT(*) FROM jdbc_account WHERE account_number=?";
        log.debug("sql: {}",sql);
        ResultSet resultSet = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,accountNumber);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                count = resultSet.getInt(1);
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
        //todo#3 select count(*)를 이용해서 계좌의 개수를 count해서 반환
        return count;
    }

    @Override
    public int deposit(Connection connection, long accountNumber, long amount){
        //todo#4 입금, executeUpdate() 결과를 반환 합니다.
        String sql = "UPDATE jdbc_account SET balance = balance+? WHERE account_number=?";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,amount);
            statement.setLong(2,accountNumber);

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int withdraw(Connection connection, long accountNumber, long amount){
        //todo#5 출금, executeUpdate() 결과를 반환 합니다.
        String sql = "UPDATE jdbc_account SET balance = balance-? WHERE account_number=?";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,amount);
            statement.setLong(2,accountNumber);

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return  result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByAccountNumber(Connection connection, long accountNumber) {
        //todo#6 계좌 삭제, executeUpdate() 결과를 반환 합니다.
        String sql = "DELETE FROM jdbc_account WHERE account_number=?";
        log.debug("sql: {}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,accountNumber);

            int result = statement.executeUpdate();
            log.debug("result: {}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
