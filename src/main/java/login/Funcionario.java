package login;

public class Funcionario {
    private String emailFuncionario;
    private String senha;
    private Integer tipoConta;

    public Funcionario(){}

    public Funcionario(String emailFuncionario, String senha, Integer tipoConta) {
        this.emailFuncionario = emailFuncionario;
        this.senha = senha;
        this.tipoConta = tipoConta;
    }


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
