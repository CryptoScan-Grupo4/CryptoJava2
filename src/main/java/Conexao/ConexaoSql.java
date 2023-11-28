package Conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSql {
    private JdbcTemplate conexaoDoBancoSql2;

    public ConexaoSql() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://34.232.200.187:1433;database=cryptoscan;encrypt=true;trustServerCertificate=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("cryptoscan");
        conexaoDoBancoSql2 = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBancoSqlServer() {
        return conexaoDoBancoSql2;
    }
}