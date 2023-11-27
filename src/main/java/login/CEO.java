package login;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import Conexao.Conexao;
public class CEO extends Funcionario {

    Conexao conexao = new Conexao();
    JdbcTemplate sql = conexao.getConexaoDoBanco();
    public CEO() {
    }

    public CEO(String nomeFuncionario, String rgFuncionario, String cpfFuncionario, String emailFuncionario, String senha, Integer tipoConta, Integer empresa, Integer idFuncionario) {
        super(nomeFuncionario, rgFuncionario, cpfFuncionario, emailFuncionario, senha, tipoConta, empresa, idFuncionario);
    }

    @Override
    public void cadastraFuncionarioCeo(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta,  Integer fkEmpresa ) {

        CEO ceo = new CEO();
        ceo.setNomeFuncionario(nome);
        ceo.setRgFuncionario(rg);
        ceo.setCpfFuncionario(cpf);
        ceo.setEmailFuncionario(email);
        ceo.setSenha(senha);
        ceo.setTipoConta(fkTipoConta);
        ceo.setEmpresa(fkEmpresa);


        sql.update("INSERT INTO funcionario (nomeFuncionario, rgFuncionario, cpfFuncionario, emailFuncionario, Senha, fkTipoConta, fkEmpresa) VALUES (?, ?, ?, ?, ?, ?, ?)", ceo.getNomeFuncionario(),ceo.getRgFuncionario(),ceo.getCpfFuncionario(), ceo.getEmailFuncionario(),ceo.getSenha(),ceo.getTipoConta(),ceo.getEmpresa());

    }

    @Override
    public void cadastraFuncionarioRepresentante(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta, Integer fkEmpresa) {


    }

    public void excluirFuncionario(Integer idFuncionario){

        sql.update("DELETE FROM funcionario WHERE id = ?", idFuncionario);
    }

    public void excluirFuncionarioRepresentante(Integer idFuncionario){

        sql.update("DELETE FROM funcionario WHERE id = ?", idFuncionario);
    }

}
