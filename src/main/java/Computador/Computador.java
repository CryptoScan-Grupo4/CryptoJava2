package Computador;

public class Computador {
    private Integer idComputador;
    private String serialComputador;
    private String statusAtividade;
    private Integer fkEmpresa;

    public Computador (){}

    public Computador(Integer idComputador, String serialComputador, String statusAtividade, Integer fkEmpresa) {
        this.idComputador = idComputador;
        this.serialComputador = serialComputador;
        this.statusAtividade = statusAtividade;
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getIdComputador() {
        return idComputador;
    }

    public void setIdComputador(Integer idComputador) {
        this.idComputador = idComputador;
    }

    public String getSerialComputador() {
        return serialComputador;
    }

    public void setSerialComputador(String serialComputador) {
        this.serialComputador = serialComputador;
    }

    public String getStatusAtividade() {
        return statusAtividade;
    }

    public void setStatusAtividade(String statusAtividade) {
        this.statusAtividade = statusAtividade;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    @Override
    public String toString() {
        return String.format("""
                ----------Computador---------
                idComputador: %d
                Serial do computador: %s
                Status da atividade: %s
                fkEmpresa: fkEmpresa
                """, idComputador, serialComputador, statusAtividade, fkEmpresa);


    }
}
