package Computador;

public class Componente {
    private Integer idComponente;
    private String nomeModelo;
    private String modeloComponente;
    private String tipoComponente;
    private String unidadeMedida;
    private Integer fkSetup;

    public Componente (){}

    public Componente(Integer idComponente, String nomeModelo, String modeloComponente, String tipoComponente, String unidadeMedida, Integer fkSetup) {
        this.idComponente = idComponente;
        this.nomeModelo = nomeModelo;
        this.modeloComponente = modeloComponente;
        this.tipoComponente = tipoComponente;
        this.unidadeMedida = unidadeMedida;
        this.fkSetup = fkSetup;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getModeloComponente() {
        return modeloComponente;
    }

    public void setModeloComponente(String modeloComponente) {
        this.modeloComponente = modeloComponente;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getFkSetup() {
        return fkSetup;
    }

    public void setFkSetup(Integer fkSetup) {
        this.fkSetup = fkSetup;
    }

    @Override
    public String toString() {
        return String.format("""
                ------------ Componente-----------
                idComponente: %d
                Nome do modelo: %s
                Modelo do componente: %s
                Tipo do componente: %s
                Unidade da medida: %s
                id do Setup: %d
                """, idComponente, nomeModelo, modeloComponente, tipoComponente, unidadeMedida, fkSetup);

    }
}
