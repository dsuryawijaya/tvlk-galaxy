package com.galaxy.currency;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyAccessor {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private Map<String, Double> cache;

    public CurrencyAccessor() {
        cache = new HashMap<>();
        DataSource dataSource = getDataSource();
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static DataSource getDataSource(){
        String username = "danielsuryawijaya";
        String password = "";
        String dbUrl = "jdbc:postgresql://localhost:5432/";

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(8);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    private synchronized String format(Long millis){
        return sdf.format(millis);
    }

    public Double getRates(String fromCurrency, String toCurrency, Long millis) {
        String query = getRatesQuery();
        MapSqlParameterSource queryParam = new MapSqlParameterSource();
        queryParam.addValue("fromCurrency", fromCurrency);
        queryParam.addValue("toCurrency", toCurrency);
        queryParam.addValue("dateTime", format(millis));

        List<Double> result = jdbcTemplate.query(query, queryParam, new RowMapper<Double>() {
            @Override
            public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getDouble("rate");
            }
        });
        return result.isEmpty() ? null : result.get(0);
    }

    private String getRatesQuery(){
        return "SELECT rate FROM currency rates1 " +
                "WHERE rates1.batch_id=(" +
                "SELECT max(rates2.batch_id) FROM currency rates2 " +
                "WHERE rates2.created_at <= :dateTime::TIMESTAMPTZ) " +
                "AND rates1.from_currency=:fromCurrency AND rates1.to_currency=:toCurrency;";
    }

    public Double getRates(String fromCurrency, String toCurrency, Date date) {
        String hash = hash(fromCurrency, toCurrency, date);
        Double rates = cache.get(hash);
        if(rates != null){
            return rates;
        } else {
            rates = getRates(fromCurrency, toCurrency, date.getTime());
            if(rates != null) {
                cache.put(hash, rates);
            }
        }
        return rates;
    }

    private String hash(String from, String to, Date date){
        return from + "-" + to + "-" + sdf.format(date);
    }

    public void cleanCache(){
        this.cache = new HashMap<>();
    }
}
