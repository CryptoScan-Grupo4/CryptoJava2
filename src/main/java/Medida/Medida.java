package Medida;

import java.time.format.DateTimeFormatter;

public class Medida {
    private Integer idMedida;
    private Float medida;
    private String  dataHoraMedida;
    private Integer fkComponente;
    private String tipoComponente;
    private Integer fkSetup;
    public Medida (){}


    public Medida(Integer idMedida, Float medida, String dataHoraMedida, Integer fkComponente, String tipoComponente, Integer fkSetup) {
        this.idMedida = idMedida;
        this.medida = medida;
        this.dataHoraMedida = dataHoraMedida;
        this.fkComponente = fkComponente;
        this.tipoComponente = tipoComponente;
        this.fkSetup = fkSetup;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public Float getMedida() {
        return medida;
    }

    public void setMedida(Float medida) {
        this.medida = medida;
    }

    public String getDataHoraMedida() {
        return dataHoraMedida;
    }

    public void setDataHoraMedida(String dataHoraMedida) {
        this.dataHoraMedida = dataHoraMedida;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Integer getFkSetup() {
        return fkSetup;
    }

    public void setFkSetup(Integer fkSetup) {
        this.fkSetup = fkSetup;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                
                |                                        MEDIDAS                                        |
                -----------------------------------------------------------------------------------------
                Medida: %f - Componente: %s - Setup: %d -  DataHoraMedida: %s                                      
                -----------------------------------------------------------------------------------------
                """,medida, tipoComponente, fkSetup, dataHoraMedida);

    }
}
