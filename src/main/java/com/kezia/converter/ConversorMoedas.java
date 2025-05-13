package com.kezia.converter;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorMoedas  {

    private static final String CHAVE_API = "c2855aa9252ce7ab7c27bf1e";
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n*************** Bem-vindo ao Conversor de Moedas ****************");
            System.out.println("1. USD para BRL");
            System.out.println("2. BRL para USD");
            System.out.println("3. EUR para USD");
            System.out.println("4. USD para EUR");
            System.out.println("5. GBP para JPY");
            System.out.println("6. CAD para ARS");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção válida: ");
            option = scanner.nextInt();

            String from = "", to = "";
            switch (option) {
                case 1: from = "USD"; to = "BRL"; break;
                case 2: from = "BRL"; to = "USD"; break;
                case 3: from = "EUR"; to = "USD"; break;
                case 4: from = "USD"; to = "EUR"; break;
                case 5: from = "GBP"; to = "JPY"; break;
                case 6: from = "CAD"; to = "ARS"; break;
                case 0: System.out.println("Saindo..."); continue;
                default: System.out.println("Opção inválida!"); continue;
            }

            System.out.print("Digite o valor para converter: ");
            double valor = scanner.nextDouble();
            double taxa = obterTaxaConversao(from, to);
            if (taxa != -1) {
                double convertido = valor * taxa;
                System.out.printf(">> %.2f %s = %.2f %s%n", valor, from, convertido, to);
            } else {
                System.out.println("Erro ao obter taxa de câmbio.");
            }

        } while (option != 0);

        scanner.close();
    }

    public static double obterTaxaConversao(String from, String to) {
        try {
            URL url = new URL(URL_BASE + CHAVE_API + "/pair/" + from + "/" + to);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ExchangeResponse resposta = new Gson().fromJson(leitor, ExchangeResponse.class);
            return resposta.getConversionRate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
