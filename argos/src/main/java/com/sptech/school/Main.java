package com.sptech.school;

import java.util.Random;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        // repetição pra mostrar quantos monitoramentos quiser
        for (int i = 0; i < 4; i++) {
            gerarMonitoramento();
            System.out.println("------------------------------------------------------------------");
        }
    }

    public static void gerarMonitoramento() {
        Random r = new Random();
        // pegar a data atual
        String data = LocalDateTime.now().toString();

        // sortear um numero de 0 a 100 pra porcentagem de cpu
        int cpuPercent = r.nextInt(101);

        // memoria total com valor mocado
        int memTotal = 16;

        // vai sortear um numero de 0 a 16 (que foi o valor mocado) pra ver o quanto de memoria esta sendo usada
        int memUsed = r.nextInt(17);

        // calcular o quanto de memoria esta livre, subtraindo o numero de memoria total e quanto que esta sendo usada
        int memFree = memTotal - memUsed;

        // calcular o percentual de memoria usada
        double memPercent = ((double) memUsed / memTotal) * 100;

        // valor mocado do total de disco (em gb)
        int discoTotal = 500;

        // sorteio de 0 a 500 do tanto de disco que esta sendo usado
        int discoUsed = r.nextInt(501);

        // calcular o tanto de disco livre, subtraindo o disco total com o disco que ta sendo usado
        int discoFree = discoTotal - discoUsed;

        // chamada dos metodos, o que vai levar os dados mocados para os metodos
        verificarCPU(data, cpuPercent);
        verificarMemoria(data, memTotal, memFree, memUsed, memPercent);
        verificarDisco(data, discoTotal, discoUsed, discoFree);
    }


    // para ficar claro de onde saiu os parametros dos metodos,
    // eles sao os dados que foram mocados na chamada dos metodos no gerarMonitoramento()

    public static void verificarCPU(String data, double cpu) {
        String level;
        String mensagem;

        if (cpu > 90) {
            level = "FATAL";
            mensagem = " - Superaquecimento iminente!";
        } else if (cpu > 75) {
            level = "WARN";
            mensagem = " - Carga elevada detectada.";
        } else {
            level = "INFO";
            mensagem = " - Operação normal.";
        }

        System.out.println("[" + level + "] " + data + " | CPU(%): " + String.format("%.2f", cpu) + mensagem);
    }

    public static void verificarMemoria(String data, double total, double free, double used, double percent) {
        String level;
        String mensagem;

        if (percent > 90) {
            level = "ERROR";
            mensagem = " - Memória insuficiente para novos processos!";
        } else if (percent > 70) {
            level = "WARN";
            mensagem = " - Consumo acima do esperado.";
        } else {
            level = "INFO";
            mensagem = " - Uso estável.";
        }

        System.out.print("[" + level + "] " + data);
        System.out.print(" | Mémoria-total: " + total + "GB | Mémoria-livre: " + String.format("%.2f", free) + "GB");
        System.out.print(" | Mémoria-used: " + String.format("%.2f", used) + "GB | Mémoria(%): " + String.format("%.2f", percent));
        System.out.println(mensagem);
    }

    public static void verificarDisco(String data, double total, double used, double free) {
        double percentUso = ((double) used / total) * 100;
        String level;
        String mensagem;

        if (percentUso > 95) {
            level = "FATAL";
            mensagem = " - Unidade de armazenamento cheia!";
        } else if (percentUso > 80) {
            level = "WARN";
            mensagem = " - Pouco espaço disponível.";
        } else {
            level = "INFO";
            mensagem = " - Espaço suficiente.";
        }

        System.out.print("[" + level + "] " + data);
        System.out.print(" | Disco-total: " + total + "GB | Disco-used: " + String.format("%.2f", used) + "GB");
        System.out.print(" | Disco-free: " + String.format("%.2f", free) + "GB");
        System.out.println(mensagem);
    }
}