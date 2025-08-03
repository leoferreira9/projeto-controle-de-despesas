package app;

import dao.CategoriaDAO;
import dao.DespesaDAO;

import java.util.Scanner;

public class ProgramaPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DespesaDAO despesaDAO = new DespesaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        int opcao;

        do{
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1. Cadastrar categoria");
            System.out.println("2. Cadastrar despesa");
            System.out.println("3. Listar todas as despesas");
            System.out.println("4. Buscar despesa por ID");
            System.out.println("5. Atualizar despesa");
            System.out.println("6. Remover despesa");
            System.out.println("7. Filtrar despesas por período");
            System.out.println("8. Total mensal de despesas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }while(opcao != 0);

        sc.close();
    }
}
