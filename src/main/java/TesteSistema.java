import Computador.Computador;
import Computador.Setup;
import Conexao.Conexao;
import Conexao.ConexaoSql;
import Medida.Medida;
import com.github.britooo.looca.api.core.Looca;

import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
        Timer timer = new Timer();
        Looca looca = new Looca();
        Conexao conexao = new Conexao();
        ConexaoSql conexao2 = new ConexaoSql();
        Sistema sistema = looca.getSistema();
        JdbcTemplate sql = conexao.getConexaoDoBanco();
        JdbcTemplate sqlServer = conexao2.getConexaoDoBancoSqlServer();
        Scanner leitor = new Scanner(System.in);
        Scanner leitorLogin = new Scanner(System.in);
        Integer opcaoEscolhida;
        String emailFuncionario;
        String senha;
        List<Computador> listaLoginFuncionario;

        Components components = JSensors.get.components();
        List<Gpu> gpus = components.gpus;

        System.out.println("""
                |-------------------------------|
                |    Bem vindo ao CryptoScan    |
                |-------------------------------|
                """);
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

                    System.out.println("""
                            |-------------------------------|
                            |Informe sua senha:             |
                            |-------------------------------|
                            """);
                    senha = leitorLogin.nextLine();

                    listaLoginFuncionario = sql.query("SELECT * FROM Computador WHERE (SELECT idFuncionario FROM Funcionario WHERE emailFuncionario = ? AND senha = ?)",
                            new BeanPropertyRowMapper<>(Computador.class), emailFuncionario, senha);


                    if (listaLoginFuncionario.size() == 0) {
                        System.out.println("""
                                |------------------------------------------------------|
                                |               Nenhuma conta encontrada               |
                                |------------------------------------------------------|
                                |Rever os dados informados ou Fazer cadastro no site   |
                                |------------------------------------------------------|
                                """);
                    } else {

                        Scanner leitorSerial = new Scanner(System.in);
                        Integer serialMaquina;
                        System.out.println("""
                                |-------------------------------------|
                                |Informe o serial da máquina:         |
                                |-------------------------------------|
                                """);
                        serialMaquina = leitorSerial.nextInt();

                        List<Setup> codigoComputadores = sql.query("SELECT idSetup, fkComputador FROM Setup WHERE(SELECT serialComputador FROM Computador WHERE serialComputador = ?)",
                                new BeanPropertyRowMapper<>(Setup.class), serialMaquina);

                        if (codigoComputadores.size() == 0) {
                            System.out.println("Computador não existe");
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

                            List<Medida> setupsDoBanco = sql.query("SELECT * FROM Setup WHERE idSetup = ? ",
                                    new BeanPropertyRowMapper<>(Medida.class), idSetup);


                            if (setupsDoBanco.size() == 0) {
                                System.out.println("Setup não existe");
                            } else {
                                Scanner leitorOpcaoDados = new Scanner(System.in);
                                Integer opcaoDados;
                                System.out.println("""
                                        |---------------------------------------------------|
                                        |                  Setup acessado                   |
                                        |---------------------------------------------------|
                                        """);

                                do {
                                    System.out.println("""
                                            |---------------------------------------------------|
                                            |1 - Iniciar leitura em tempo real                  |
                                            |2 - Visualização do histórico                      |
                                            |3 - Informações de disco                           |
                                            |4 - Informações da GPU                             |
                                            |5 - Sair                                           |
                                            |0 - Parar leitura                                  |
                                            |---------------------------------------------------|
                                            """);
                                    opcaoDados = leitorOpcaoDados.nextInt();

                                    switch (opcaoDados) {
                                        case 1:
                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Integer usoProcessador = (looca.getProcessador().getUso()).intValue();
                                                    Long usoMemoria = (looca.getMemoria().getEmUso());
                                                    Long limiteMemoria = (looca.getMemoria().getTotal());

                                                    Double porcentagemMemoria = Double.valueOf((usoMemoria * 100) / limiteMemoria);

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
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemUsoDowload, 4, idSetup);
                                                    sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemUsoUpload, 5, idSetup);


                                                    sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoProcessador, 1, idSetup);
                                                    sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemMemoria, 2, idSetup);
                                                    sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoDisco, 3, idSetup);
                                                    sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemUsoDowload, 4, idSetup);
                                                    sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemUsoUpload, 5, idSetup);


                                                }
                                            }, 5000, 2000);

                                            break;

                                        case 2:
                                            List<Medida> medidasInseridas = sql.query("SELECT tipoComponente, medida, idSetup AS fkSetup, DATE_FORMAT(dataHoraMedida, '%d %c %Y %T') AS 'dataHoraMedida' FROM Medida join Setup on idSetup = fkSetup join Componente on idComponente = fkComponente where idSetup = ?;",
                                                    new BeanPropertyRowMapper<>(Medida.class), idSetup);

                                            for (Medida medida : medidasInseridas) {
                                                System.out.println(medidasInseridas);
                                            }
                                            break;
                                        case 3:
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
                                            if (gpus != null && !gpus.isEmpty()) {
                                                for (Gpu gpu : gpus) {
                                                    List<Temperature> temps = gpu.sensors.temperatures;
                                                    for (final Temperature temp : temps) {
                                                        timer.schedule(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                Double temperaturaGPU = Double.valueOf(gpus.get(0).sensors.temperatures.toString());
                                                                sql.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", temperaturaGPU, 5, idSetup);
                                                                sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", temperaturaGPU, 5, idSetup);

                                                                System.out.printf("""
                                                                    |------------------------------------------------------------------|
                                                                    |                       INFORMAÇÕES DA GPU                         |
                                                                    |------------------------------------------------------------------|
                                                                    |Nome: %s                                                          |
                                                                    |Temperatura: %.1f ºC                                              |
                                                                    |------------------------------------------------------------------|
                                                                        """, gpu.name, temp.value);
                                                                }
                                                            }, 5000, 2000);




                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Nenhuma GPU detectada");

                                                }
                                                break;


                                            case 0: {
                                                System.out.println("Parando leitura");

                                                System.exit(0);

                                                break;
                                            }
                                        }

                                    } while (opcaoDados != 5);


                                }

                            }

                        }
                        break;
                }

            } while (opcaoEscolhida != 0);

        }

    }
