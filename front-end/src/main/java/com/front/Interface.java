package com.front;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.dto.AccountDTO;

public class Interface {

    private static final String MENU = "****************************\n"
            + "Bem vindo ao Banco do Brasil\n"
            + "****************************\n"
            + "\n"
            + "- Digite o número ao lado da opção para escolhe-la:\n"
            + "\n"
            + "1 - Cadastrar nova conta\n"
            + "2 - Consultar saldo\n"
            + "3 - Adicionar crédito\n"
            + "4 - Realizar débito\n"
            + "5 - Realizar transferência\n"
            + "6 - Render Juros\n"
            + "7 - Consultar Dados\n"
            + "8 - Sair do programa\n"
            + "\n";

    private static final String PROMPT_CONTINUAR = "Pressione Enter para continuar...";
    private static final String PROMPT_NUMERO = "Digite um número: ";
    private static final String PROMPT_CARACTERE = "Digite um caractere: ";
    private static final String PROMPT_SALDO = "Diga o saldo inicial:\n";
    private static final String PROMPT_TAXA = "Digite a taxa de juros que você pretende adicionar\n"
            + "\n"
            + "Digite uma taxa: ";

    private Scanner sc;
    private AccountClient accountClient;

    public Interface(Scanner sc, AccountClient accountClient) {
        this.sc = sc;
        this.accountClient = accountClient;
    }

    public void init() {
        showMenu();
    }

    private void pauseBeforeClearingScreen() {
        System.out.println(PROMPT_CONTINUAR);
        sc.nextLine();
        sc.nextLine();
        clearScreen();
    }

    private void showMenu() {
        int response = 0;

        while (true) {
            System.out.print(MENU);
            response = sc.nextInt();
            clearScreen();

            try {
                switch (response) {
                    case 1:
                        showRegisterAccount();
                        break;
                    case 2:
                        showCheckBalance();
                        break;
                    case 3:
                        showMakeDeposit();
                        break;
                    case 4:
                        showMakeDebit();
                        break;
                    case 5:
                        showMakeTransfer();
                        break;
                    case 6:
                        showMakeYieldInterest();
                        break;
                    case 7:
                        showCheckData();
                        break;
                    case 8:
                        System.out.println("Obrigado por usar o Banco do Brasil!");
                        return;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Ocorreu um erro ao realizar a operação: " + e.getMessage());
            }

            pauseBeforeClearingScreen();
        }
    }

    private void showRegisterAccount() {
        System.out.print("Para registrar sua conta precisaremos de um número identificador escolhido por você\n"
                + "\n"
                + PROMPT_NUMERO);

        int response = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + "Para registrar sua conta precisaremos de tipo de conta (n: normal | b: bonus | p: poupança)\n"
                + "\n"
                + PROMPT_CARACTERE);

        Character type = sc.next().charAt(0);

        float initialBalance = 0;
        if (type.equals('p') || type.equals('n')) {
            System.out.print(PROMPT_SALDO);
            initialBalance = sc.nextFloat();
        }

        AccountDTO accountDTO = new AccountDTO(response, type, initialBalance);

        try {
            HttpResponse<String> serverResponse = accountClient.registerAccount(accountDTO);
            String responseBody = serverResponse.body();

            if (responseBody.contains("Erro")) {
                System.out.println("Erro: Já existe uma conta com esse identificador.");
            } else {
                System.out.println("Parabéns! Sua conta foi criada com o identificador: " + response + ".");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocorreu um erro ao tentar registrar a conta: " + e.getMessage());
        }
    }

    private void showCheckBalance() {
        System.out.print("Digite o número identificador da conta que você quer verificar o saldo\n"
                + "\n"
                + PROMPT_NUMERO);

        int response = sc.nextInt();

        try {
            HttpResponse<String> serverResponse = accountClient.getAccountById(response);
            String responseBody = serverResponse.body();

            if (responseBody.contains("Erro")) {
                System.out.println("Erro: Não existe uma conta com esse identificador.");
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                double balance = jsonNode.get("balance").asDouble();

                System.out.println("O saldo da conta " + response + " é de: " + balance);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocorreu um erro ao tentar verificar o saldo: " + e.getMessage());
        }
    }

    private void showMakeDeposit() throws IOException, InterruptedException {
        System.out.print("Digite o número identificador da conta que você quer adicionar crédito\n"
                + "\n"
                + PROMPT_NUMERO);

        int ident = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + "Digite a quantidade de crédito que você pretende adicionar\n"
                + "\n"
                + PROMPT_NUMERO);

        float quant = sc.nextFloat();

        HttpResponse<String> serverResponse = accountClient.addCredit(ident, quant);
        System.out.println(serverResponse.body());
    }

    private void showMakeDebit() throws IOException, InterruptedException {
        System.out.print("Digite o número identificador da conta que você quer realizar um débito\n"
                + "\n"
                + PROMPT_NUMERO);

        int ident = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + "Digite a quantidade de dinheiro que você pretende remover\n"
                + "\n"
                + PROMPT_NUMERO);

        float quant = sc.nextFloat();

        HttpResponse<String> serverResponse = accountClient.addDebit(ident, quant);
        System.out.println(serverResponse.body());
    }

    private void showMakeTransfer() throws IOException, InterruptedException {
        System.out.print("Digite o número identificador da conta de origem da transferência\n"
                + "\n"
                + PROMPT_NUMERO);

        int identOrig = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + "Digite o número identificador da conta de destino da transferência\n"
                + "\n"
                + PROMPT_NUMERO);

        int identDest = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + "Digite a quantidade de dinheiro que você pretende transferir\n"
                + "\n"
                + PROMPT_NUMERO);

        float quant = sc.nextFloat();

        HttpResponse<String> serverResponse = accountClient.transfer(identOrig, identDest, quant);
        System.out.println(serverResponse.body());
    }

    private void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    private void showMakeYieldInterest() throws IOException, InterruptedException {
        System.out.print("Digite o número identificador da conta que você quer adicionar juros\n"
                + "\n"
                + PROMPT_NUMERO);

        int ident = sc.nextInt();

        System.out.print("---------------------------------------------------------\n"
                + PROMPT_TAXA);

        float rate = sc.nextFloat();

        HttpResponse<String> serverResponse = accountClient.yieldInterest(ident, rate);
        System.out.println(serverResponse.body());
    }

    private void showCheckData() throws IOException, InterruptedException {
        System.out.print("Digite o número identificador da conta que você quer verificar os dados\n"
                + "\n"
                + PROMPT_NUMERO);

        int response = sc.nextInt();

        HttpResponse<String> serverResponse = accountClient.getAccountById(response);

        System.out.println(serverResponse.body());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountClient accountClient = new AccountClient();
        Interface app = new Interface(sc, accountClient);
        app.init();
    }
}
