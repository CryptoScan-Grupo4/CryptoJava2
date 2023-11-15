package Conexao;

import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.servicos.ServicoGrupo;
import com.github.britooo.looca.api.group.temperatura.Temperatura;

public class Sistema {
    private final Sistema sistema;
    private final Memoria memoria;
    private final Processador processador;
    private final Temperatura temperatura;
    private final Disco disco;
    private final DiscoGrupo grupoDeDiscos;
    private final ServicoGrupo grupoDeServicos;
    private final ProcessoGrupo grupoDeProcessos;

    public Sistema(Sistema sistema, Memoria memoria, Processador processador, Temperatura temperatura, Disco disco, DiscoGrupo grupoDeDiscos, ServicoGrupo grupoDeServicos, ProcessoGrupo grupoDeProcessos) {
        this.sistema = sistema;
        this.memoria = memoria;
        this.processador = processador;
        this.temperatura = temperatura;
        this.disco = disco;
        this.grupoDeDiscos = grupoDeDiscos;
        this.grupoDeServicos = grupoDeServicos;
        this.grupoDeProcessos = grupoDeProcessos;
    }


    public Sistema getSistema() {
        return sistema;
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public Processador getProcessador() {
        return processador;
    }

    public Temperatura getTemperatura() {
        return temperatura;
    }

    public DiscoGrupo getGrupoDeDiscos() {
        return grupoDeDiscos;
    }

    public ServicoGrupo getGrupoDeServicos() {
        return grupoDeServicos;
    }

    public ProcessoGrupo getGrupoDeProcessos() {
        return grupoDeProcessos;
    }

    public Disco getDisco() {
        return disco;
    }

    @Override
    public String toString() {
        return "Sistema{" +
                "sistema=" + sistema +
                ", memoria=" + memoria +
                ", processador=" + processador +
                ", temperatura=" + temperatura +
                ", disco=" + disco +
                ", grupoDeDiscos=" + grupoDeDiscos +
                ", grupoDeServicos=" + grupoDeServicos +
                ", grupoDeProcessos=" + grupoDeProcessos +
                '}';
    }
}
