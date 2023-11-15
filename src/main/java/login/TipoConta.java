package login;

public class TipoConta {
   private Integer idTipoConta;
   private String tipoConta;

   public TipoConta(){

   }

    public TipoConta(Integer idTipoConta, String tipoConta) {
        this.idTipoConta = idTipoConta;
        this.tipoConta = tipoConta;
    }

    public Integer getIdTipoConta() {
        return idTipoConta;
    }

    public void setIdTipoConta(Integer idTipoConta) {
        this.idTipoConta = idTipoConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    @Override
    public String toString() {
        return "TipoConta{" +
                "idTipoConta=" + idTipoConta +
                ", tipoConta='" + tipoConta + '\'' +
                '}';
    }
}
