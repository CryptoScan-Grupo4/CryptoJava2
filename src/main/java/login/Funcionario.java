package login;

public abstract class Funcionario {

    protected Integer idFuncionario;

    protected String nomeFuncionario;
    protected String  rgFuncionario;
    protected String  cpfFuncionario;
    protected String emailFuncionario;
    protected String senha;
    protected Integer tipoConta;

    protected  Integer empresa;

    public  Funcionario(){}

    public Funcionario(String nomeFuncionario, String rgFuncionario, String cpfFuncionario, String emailFuncionario, String senha, Integer tipoConta, Integer empresa, Integer idFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
        this.rgFuncionario = rgFuncionario;
        this.cpfFuncionario = cpfFuncionario;
        this.emailFuncionario = emailFuncionario;
        this.senha = senha;
        this.tipoConta = tipoConta;
        this.empresa = empresa;
        this.idFuncionario = idFuncionario;
    }


    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getRgFuncionario() {
        return rgFuncionario;
    }

    public void setRgFuncionario(String rgFuncionario) {
        this.rgFuncionario = rgFuncionario;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public abstract void cadastraFuncionarioCeo(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta,  Integer fkEmpresa );

    public abstract void cadastraFuncionarioRepresentante(String nome, String rg, String cpf, String email, String senha, Integer fkTipoConta,  Integer fkEmpresa );

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Integer tipoConta) {
        this.tipoConta = tipoConta;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "emailFuncionario='" + emailFuncionario + '\'' +
                ", senha='" + senha + '\'' +
                ", tipoConta=" + tipoConta +
                '}';
    }
}
