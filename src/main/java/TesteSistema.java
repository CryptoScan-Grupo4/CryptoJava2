import Computador.Computador;
import Computador.Setup;
import Conexao.Conexao;
import Log.Log;
import Medida.Medida;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import integracao.Slack;
import login.Funcionario;
import org.json.JSONObject;
import login.Funcionario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TesteSistema {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {

        Slack slack = new Slack();
        Log log = new Log();
        Timer timer = new Timer();
        Looca looca = new Looca();
        Conexao conexao = new Conexao();
        Sistema sistema = looca.getSistema();
        JdbcTemplate sql = conexao.getConexaoDoBanco();
        Scanner leitor = new Scanner(System.in);
        Scanner leitorLogin = new Scanner(System.in);
        Integer opcaoEscolhida;
        String emailFuncionario;
        String senha;
        List<Computador> listaLoginFuncionario;

        Components components = JSensors.get.components();
        List<Gpu> gpus = components.gpus;
        Gpu gpuUnique = components.gpus.get(0);

        System.out.println("""
                |-------------------------------|
                |    Bem vindo ao CryptoScan    |
                |-------------------------------|
                """);
        log.info("Entrada no sistema CryptoScan");


        do {
            System.out.println("""
                    |-------------------------------|
                    |1 - Realizar login no sistema  |
                    |0 - Sair                       |
                    |-------------------------------|
                    """);
            opcaoEscolhida = leitor.nextInt();

            switch (opcaoEscolhida) {
                case 1:
                    System.out.println("""
                            |-------------------------------|
                            |Informe seu email:             |
                            |-------------------------------|
                            """);
                    emailFuncionario = leitorLogin.nextLine();
                    log.info("Email informado");

                    System.out.println("""
                            |-------------------------------|
                            |Informe sua senha:             |
                            |-------------------------------|
                            """);
                    senha = leitorLogin.nextLine();
                    log.info("Senha informada");

                    listaLoginFuncionario = sql.query("SELECT idFuncionario FROM Funcionario WHERE emailFuncionario = ? AND senha = ?",
                            new BeanPropertyRowMapper<>(Computador.class), emailFuncionario, senha);


                    if (listaLoginFuncionario.size() == 0) {
                        System.out.println("""
                                |------------------------------------------------------|
                                |               Nenhuma conta encontrada               |
                                |------------------------------------------------------|
                                |Rever os dados informados ou Fazer cadastro no site   |
                                |------------------------------------------------------|
                                """);
                        log.error("Conta não encontrada");

                    } else {

                        Scanner leitorSerial = new Scanner(System.in);
                        Integer serialMaquina;
                        System.out.println("""
                                |-------------------------------------|
                                |Informe o serial da máquina:         |
                                |-------------------------------------|
                                """);
                        serialMaquina = leitorSerial.nextInt();
                        log.info("Serial da máquina informado!");

                        List<Setup> codigoComputadores = sql.query("SELECT serialComputador FROM Computador WHERE serialComputador = ?",
                                new BeanPropertyRowMapper<>(Setup.class), serialMaquina);

                        if (codigoComputadores.size() == 0) {
                            System.out.println("Computador não existe");
                            log.error("Serial não existente");

                        } else {
                            Scanner leitorOpcaoSetup = new Scanner(System.in);
                            Integer idSetup;
                            System.out.println(""" 
                                    |-------------------------------------|
                                    |          Máquina acessada           |
                                    |-------------------------------------|
                                    |Informe o id do setup:               |
                                    |-------------------------------------|
                                    """);
                            idSetup = leitorOpcaoSetup.nextInt();
                            log.info("Id do setup informado!");

                            List<Medida> setupsDoBanco = sql.query("SELECT * FROM Setup WHERE idSetup = ? ",
                                    new BeanPropertyRowMapper<>(Medida.class), idSetup);


                            if (setupsDoBanco.size() == 0) {
                                System.out.println("Setup não existe");
                                log.error("Id do setup não localizado!");

                            } else {
                                Scanner leitorOpcaoDados = new Scanner(System.in);
                                Integer opcaoDados;
                                System.out.println("""
                                        |---------------------------------------------------|
                                        |                  Setup acessado                   |
                                        |---------------------------------------------------|
                                        """);
                                log.success("Setup acessado!");

                                do {
                                    System.out.println("""
                                            |---------------------------------------------------|
                                            |1 - Iniciar leitura em tempo real                  |
                                            |2 - Visualização do histórico                      |
                                            |3 - Informações de disco                           |
                                            |4 - Informações da GPU                             |
                                            |5 - Sair                                           |
                                            |---------------------------------------------------|
                                            """);
                                    opcaoDados = leitorOpcaoDados.nextInt();

                                    switch (opcaoDados) {
                                        case 1:
                                            log.info("Inicialização da leitura em tempo real");

                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Integer usoProcessador = (looca.getProcessador().getUso()).intValue();
                                                    Long usoMemoria = (looca.getMemoria().getEmUso());
                                                    Long limiteMemoria = (looca.getMemoria().getTotal());

                                                    Double porcentagemMemoria = Double.valueOf((usoMemoria * 100) / limiteMemoria);

                                                    Double temperaturaGPU = Double.valueOf(gpuUnique.sensors.temperatures.get(0).value);

                                                    Double valorDisponivelDisco = Double.valueOf(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 8e+9);
                                                    Double valorTotalDisco = Double.valueOf(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 8e+9);

                                                    BigDecimal valorDisponivelBigDecimal = new BigDecimal(valorDisponivelDisco);
                                                    valorDisponivelBigDecimal = valorDisponivelBigDecimal.setScale(2, RoundingMode.HALF_UP);
                                                    Double valorDisponivelArrendondado = valorDisponivelBigDecimal.doubleValue();

                                                    BigDecimal valorTotalBigDecimal = new BigDecimal(valorTotalDisco);
                                                    valorTotalBigDecimal = valorTotalBigDecimal.setScale(2, RoundingMode.HALF_UP);
                                                    Double valorTotalArrendondado = valorTotalBigDecimal.doubleValue();

                                                    Double discoOcupado = (valorTotalArrendondado - valorDisponivelArrendondado);
                                                    Double usoDisco = ((discoOcupado * 100) / valorTotalArrendondado);

                                                    Double velocidadeDownload = 0.0;
                                                    List<RedeInterface> lista = looca.getRede().getGrupoDeInterfaces().getInterfaces();
                                                    for (int i = 0; lista.size() > i; i++) {
                                                        if (!lista.get(i).getEnderecoIpv4().isEmpty()) {
                                                            velocidadeDownload = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(i).getBytesRecebidos().doubleValue();
                                                            break;
                                                        }
                                                    }

                                                    Double velocidadeUpload = 0.0;
                                                    for (int i = 0; lista.size() > i; i++) {
                                                        if (!lista.get(i).getEnderecoIpv4().isEmpty()) {
                                                            velocidadeUpload = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(i).getBytesEnviados().doubleValue();
                                                            break;
                                                        }
                                                    }
                                                    Conversor.formatarBytes(velocidadeDownload.longValue());
                                                    Conversor.formatarBytes(velocidadeUpload.longValue());

                                                    Double porcentagemUsoDowload = (velocidadeDownload * 100) / 150.0;
                                                    Double porcentagemUsoUpload = (velocidadeUpload * 100) / 150.0;


                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoProcessador, 1, idSetup);
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemMemoria, 2, idSetup);
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoDisco, 3, idSetup);
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", velocidadeDownload, 4, idSetup);
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", temperaturaGPU, 5, idSetup);

                                                    JSONObject json = new JSONObject();

                                                    if (!gpus.isEmpty()) {
                                                        Double temperaturaGpu = gpuUnique.sensors.temperatures.get(0).value;
                                                        String nomeGpu = gpuUnique.name;
                                                        if (temperaturaGpu > 40.0) {
                                                            String msg = "*ALERTA DE ALTA TEMPERATURA*\n*GPU:* %s | *Temperatura:* %.1fºC | *Serial:* %d".formatted(nomeGpu, temperaturaGpu, serialMaquina);
                                                            json.put("text", msg);
                                                            log.info("Alerta de alta temperatura: %.1fºC".formatted(temperaturaGpu));
                                                        }
                                                    }

                                                    try {
                                                        Slack.sendMessage(json);
                                                    } catch (IOException | InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                            }, 5000, 2000);

                                            break;

                                        case 2:
                                            log.info("Vizualizando histórico");

                                            List<Medida> medidasInseridas = sql.query("SELECT tipoComponente, medida, idSetup AS fkSetup, FORMAT(dataHoraMedida, 'dd MMM yyyy HH:mm:ss') AS dataHoraMedida FROM Medida JOIN Setup ON idSetup = fkSetup JOIN Componente ON idComponente = fkComponente WHERE idSetup = ?;",
                                                    new BeanPropertyRowMapper<>(Medida.class), idSetup);

                                            for (Medida medida : medidasInseridas) {
                                                System.out.println(medidasInseridas);
                                            }
                                            break;
                                        case 3:
                                            log.info("Acesso a informação de disco");
                                            String tamanhoTotal = Conversor.formatarBytes(looca.getGrupoDeDiscos().getTamanhoTotal());
                                            String espacoDisponivel = GREEN + Conversor.formatarBytes(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel()) + RESET;
                                            String espacoEmUso = RED + Conversor.formatarBytes(looca.getGrupoDeDiscos().getTamanhoTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel()) + RESET;

                                            System.out.printf("""
                                                    \n
                                                    |------------------------------------------------------------------|
                                                    |                       INFORMAÇÕES DE DISCO                       |
                                                    |------------------------------------------------------------------|
                                                    |Tamanho Total: %s                                          |
                                                    |Espaço Disponivel: %s                                      |
                                                    |Espaço em uso: %s                                          |
                                                    |------------------------------------------------------------------|
                                                    \n
                                                    """, tamanhoTotal, espacoDisponivel, espacoEmUso);
                                            break;
                                        case 4:
                                            log.info("Acesso a GPU");

                                            if (gpus != null) {
                                                for (Gpu gpu : gpus) {
                                                    List<Temperature> temps = gpu.sensors.temperatures;
                                                    for (final Temperature temp : temps) {
                                                        System.out.printf("""
                                                                \n
                                                                |------------------------------------------------------------------|
                                                                |                       INFORMAÇÕES DA GPU                         |
                                                                |------------------------------------------------------------------|
                                                                |Nome: %s                                                          |
                                                                |Temperatura: %.1f ºC                                              |
                                                                |------------------------------------------------------------------|
                                                                \n
                                                                """, gpu.name, temp.value);
                                                    }
                                                }
                                            } else {
                                                System.out.println("Nenhuma GPU detectada");
                                            }
                                            break;
                                    }

                                } while (opcaoDados != 5);

                                System.out.println("Parando o sistema");

                                System.exit(0);

                                break;
                            }

                        }

                    }
                    break;

            }

        } while (opcaoEscolhida != 0);

    }

}
