package login;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import Conexao.Conexao;

public class Representante extends Funcionario {

    Conexao conexao = new Conexao();
    JdbcTemplate sql = conexao.getConexaoDoBanco();

    public Representante() {
    }

    public Representante(String nomeFuncionario, String rgFuncionario, String cpfFuncionario, String emailFuncionario, String senha, Integer tipoConta, Integer empresa, Integer idFuncionario) {
        super(nomeFuncionario, rgFuncionario, cpfFuncionario, emailFuncionario, senha, tipoConta, empresa, idFuncionario);
    }

    @Override
    public void cadastraFuncionarioCeo(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta, Integer fkEmpresa) {

    }

    @Override
    public void cadastraFuncionarioRepresentante(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta, Integer fkEmpresa) {
        Representante rep = new Representante();
        rep.setNomeFuncionario(nome);
        rep.setRgFuncionario(rg);
        rep.setCpfFuncionario(cpf);
        rep.setEmailFuncionario(email);
        rep.setSenha(senha);
        rep.setTipoConta(fkTipoConta);
        rep.setEmpresa(fkEmpresa);


        sql.update("INSERT INTO funcionario (nomeFuncionario, rgFuncionario, cpfFuncionario, emailFuncionario, Senha, fkTipoConta, fkEmpresa) VALUES (?, ?, ?, ?, ?, ?, ?)", rep.getNomeFuncionario(),rep.getRgFuncionario(),rep.getCpfFuncionario(), rep.getEmailFuncionario(),rep.getSenha(),rep.getTipoConta(),rep.getEmpresa());
    }
}
