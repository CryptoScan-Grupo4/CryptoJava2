package Computador;

public class LimiteComponente {
    private Integer idLimite;
    private Float min;
    private Float max;
    private Integer fkComponente;

    public LimiteComponente(){}

    public LimiteComponente(Integer idLimite, Float min, Float max, Integer fkComponente) {
        this.idLimite = idLimite;
        this.min = min;
        this.max = max;
        this.fkComponente = fkComponente;
    }

    public Integer getIdLimite() {
        return idLimite;
    }

    public void setIdLimite(Integer idLimite) {
        this.idLimite = idLimite;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                ---------Limite do Componente------
                Mínimo do componente: %f
                Máximo do componente: %f
                """);
    }
}
