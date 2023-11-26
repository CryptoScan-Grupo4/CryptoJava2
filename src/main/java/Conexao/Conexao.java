package Conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    private JdbcTemplate conexaoDoBanco;

    // Conexão MYSQL
    public Conexao() {
        //BasicDataSource dataSource = new BasicDataSource();
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/cryptoscan");
        //dataSource.setUsername("cryptoscan");
        //dataSource.setPassword("cryptoscan");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://34.232.200.187:1433;database=cryptoscan;encrypt=true;trustServerCertificate=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("cryptoscan");

        conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}
