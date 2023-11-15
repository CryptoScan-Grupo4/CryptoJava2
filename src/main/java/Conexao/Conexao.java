package Conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    private JdbcTemplate conexaoDoBanco;

    //Conexão MYSQL

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/CryptoScan");
        dataSource.setUsername("root");
        dataSource.setPassword("aluno");

        conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }




    // Conexão SQL SERVER
//    public Conexao.Conexao() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        dataSource.setUrl("jdbc:sqlserver://localhost:1433;database=mydb");
//        dataSource.setUsername("");
//        dataSource.setPassword("");
//
//        conexaoDoBanco = new JdbcTemplate(dataSource);
//    }

}
