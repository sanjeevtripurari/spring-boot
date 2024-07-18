package com.sanj.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanj.model.Employee;

@Repository
public class EmployeeRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Employee createEmployee(Employee employee)
    {
        String sql = "INSERT INTO EMPLOYEE " + "(NAME, AGE,SALARY) VALUES (?, ?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator()
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException
            {
                PreparedStatement ps = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getName());
                ps.setInt(2, employee.getAge());
                ps.setInt(3, employee.getSalary());
                return ps;
            }
        }, holder);

        int generatedEmployeeId = holder.getKey().intValue();
        employee.setId(generatedEmployeeId);
        return employee;
    }
}