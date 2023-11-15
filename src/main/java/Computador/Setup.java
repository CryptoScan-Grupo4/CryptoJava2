package Computador;

import javax.xml.crypto.Data;

public class Setup {
    private Integer idSetup;
    private Integer fkComputador;
    private Integer fkComponente;
    private String dataCriacao;

    public Setup (){}

    public Setup(Integer idSetup, Integer fkComputador, Integer fkComponente, String dataCriacao) {
        this.idSetup = idSetup;
        this.fkComputador = fkComputador;
        this.fkComponente = fkComponente;
        this.dataCriacao = dataCriacao;
    }

    public Integer getIdSetup() {
        return idSetup;
    }

    public void setIdSetup(Integer idSetup) {
        this.idSetup = idSetup;
    }

    public Integer getFkComputador() {
        return fkComputador;
    }

    public void setFkComputador(Integer fkComputador) {
        this.fkComputador = fkComputador;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return String.format("""
                -----------Setup----------
                idSetup: %d
                fkComputador: %d
                fkComponente: %d
                
                """, idSetup, fkComputador, fkComponente);

    }
}
