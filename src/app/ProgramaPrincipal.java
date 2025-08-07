package app;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import model.Categoria;
import model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ProgramaPrincipal {

    public static final DespesaDAO despesaDAO = new DespesaDAO();
    public static final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do{
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1. Cadastrar categoria");
            System.out.println("2. Cadastrar despesa");
            System.out.println("3. Listar todas as Despesas");
            System.out.println("4. Buscar despesa por ID");
            System.out.println("5. Atualizar despesa");
            System.out.println("6. Remover despesa");
            System.out.println("7. Atualizar categoria");
            System.out.println("8. Remover categoria");
            System.out.println("9. Listar todas as Categorias");
            System.out.println("10. Filtrar despesas por período");
            System.out.println("11. Total mensal de despesas");
            System.out.println("0. Sair");

            opcao = lerInt(sc, "\nEscolha uma opção: ");

            switch (opcao){
                case 1:
                    cadastraCategoria(sc);
                    break;
                case 2:
                    cadastrarDespesa(sc);
                    break;
                case 3:
                    List<Despesa> despesas = despesaDAO.listarTodas();
                    if(!despesas.isEmpty()){
                        despesas.forEach(System.out::println);
                    } else {
                        System.out.println("Nenhuma despesa cadastrada!");
                    }
                    break;
                case 4:
                    Despesa despesa = buscarDespesaPorId(sc);
                    if(despesa != null){
                        System.out.println(despesa);
                    } else {
                        System.out.println("Nenhuma despesa encontrada!");
                    }
                    break;
                case 5:
                    atualizarDespesa(sc);
                    break;
                case 6:
                    removerDespesa(sc);
                    break;
                case 7:
                    atualizarCategoria(sc);
                    break;
                case 8:
                    removerCategoria(sc);
                    break;
                case 9:
                    if(categoriaDAO.listarTodas().isEmpty()){
                        System.out.println("\n❌ Nenhuma categoria cadastrada!");
                    } else {
                        System.out.println();
                        listarCategorias();
                    }
                    break;
                case 10:
                    filtarDespesasPorPeriodo(sc);
                    break;
                case 11:
                    calcularTotalMensal(sc);
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("\n❌ Opção inválida!");
            }
        }while(opcao != 0);
        sc.close();
    }

    public static BigDecimal lerBigDecimal(Scanner sc, String mensagem){
        while(true){
            try {
                System.out.print(mensagem);
                return new BigDecimal(sc.nextLine().replace(",", "."));
            } catch (NumberFormatException e){
                System.out.println("\n❌ Valor inválido. Digite um número decimal (ex: 10.50).\n");
            }
        }
    }

    public static int lerInt(Scanner sc, String mensagem){
        while(true){
            try {
                System.out.print(mensagem);
                return Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("\n❌ Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public static LocalDate lerData(Scanner sc, String mensagem){
        while(true){
            try {
                System.out.println(mensagem);
                System.out.print("Ano: ");
                int ano = Integer.parseInt(sc.nextLine());

                System.out.print("Mês: ");
                int mes = Integer.parseInt(sc.nextLine());

                System.out.print("Dia: ");
                int dia = Integer.parseInt(sc.nextLine());

                return LocalDate.of(ano, mes, dia);
            }catch (Exception e){
                System.out.println("\n❌ Data inválida. Tente novamente.\n");
            }
        }

    }

    public static Categoria escolherCategoria(Scanner sc){
        System.out.println();
        listarCategorias();
        int id = lerInt(sc, "\nInforme o ID da categoria: ");
        return categoriaDAO.buscarPorId(id);
    }

    public static void listarCategorias(){
        List<Categoria> categorias = categoriaDAO.listarTodas();
        if(categorias.isEmpty()){
            System.out.println("\n❌ Nenhuma categoria cadastrada!");
        } else {
            categorias.forEach(System.out::println);
        }
    }

    public static void listarDespesas(){
        List<Despesa> despesas = despesaDAO.listarTodas();
        if(despesas.isEmpty()){
            System.out.println("\n❌ Nenhuma despesa cadastrada!");
        } else {
            despesas.forEach(System.out::println);
        }
    }

    public static void cadastraCategoria(Scanner sc){
        System.out.print("\nDigite o nome da nova categoria: ");
        String nomeCategoria = sc.nextLine();
        Categoria novaCategoria = new Categoria(nomeCategoria);
        categoriaDAO.inserir(novaCategoria);
    }

    public static void cadastrarDespesa(Scanner sc){
        System.out.print("\nDigite a descrição da despesa: ");
        String descricaoDespesa = sc.nextLine();

        BigDecimal valorDespesa = lerBigDecimal(sc, "Digite o valor: ");

        LocalDate data = lerData(sc, "Agora informe a data dessa despesa");
        Categoria categoria = escolherCategoria(sc);

        if(categoria == null){
            System.out.println("\n❌ Categoria inválida!");
        } else {
            Despesa novaDespesa = new Despesa(descricaoDespesa, valorDespesa, data, categoria);
            despesaDAO.inserir(novaDespesa);
        }

    }

    public static Despesa buscarDespesaPorId(Scanner sc){
        System.out.println();
        int id = lerInt(sc, "\nInforme o ID da despesa: ");
        return despesaDAO.buscarPorId(id);
    }

    public static void atualizarDespesa(Scanner sc){
        if(despesaDAO.listarTodas().isEmpty()){
            System.out.println("\n❌ Não há despesas cadastradas!");
        } else {
            listarDespesas();
            int id = lerInt(sc, "\nInforme o ID da despesa que deseja atualizar: ");

            Despesa despesa = despesaDAO.buscarPorId(id);

            if(despesa == null){
                System.out.println("\n❌ Nenhuma despesa encontrada!");
            } else {
                System.out.print("\nInforme a descrição da despesa: ");
                String descricao = sc.nextLine();

                BigDecimal valor = lerBigDecimal(sc, "Informe o valor: ");

                LocalDate data = lerData(sc, "Agora informe a data dessa despesa");

                Categoria categoria = escolherCategoria(sc);

                if(categoria == null){
                    System.out.println("\n❌ Categoria inválida!");
                } else {
                    despesa = new Despesa(descricao, valor, data, categoria);
                    despesaDAO.atualizar(despesa, id);
                }
            }
        }
    }

    public static void removerDespesa(Scanner sc){
        List<Despesa> despesas = despesaDAO.listarTodas();
        if(!despesas.isEmpty()){
            despesas.forEach(System.out::println);

            int id = lerInt(sc, "\nInforme o ID da despesa que deseja remover: ");

            Despesa despesa = despesaDAO.buscarPorId(id);

            if(despesa == null){
                System.out.println("\n❌ Nenhuma despesa encontrada!");
            } else {
                despesaDAO.remover(despesa.getId());
            }
        } else {
            System.out.println("\n❌ Nenhuma despesa cadastrada!");
        }
    }

    public static void atualizarCategoria(Scanner sc){
        if(categoriaDAO.listarTodas().isEmpty()){
            System.out.println("\n❌ Não há categorias cadastradas!");
        } else {
            Categoria categoria = escolherCategoria(sc);

            if(categoria == null){
                System.out.print("\n❌ Categoria não encontrada!");
            } else {
                System.out.print("\nDigite o novo nome da categoria: ");
                sc.nextLine();
                String novoNome = sc.nextLine();
                categoria.setNome(novoNome);
                categoriaDAO.atualizar(categoria);
            }
        }
    }

    private static void removerCategoria(Scanner sc){
        List<Categoria> categorias = categoriaDAO.listarTodas();

        if(categorias.isEmpty()){
            System.out.println("\n❌ Não há categorias cadastradas!");
        } else {
            System.out.println();
            categorias.forEach(System.out::println);
            int id = lerInt(sc, "\nInforme o ID da categoria: ");

            Categoria categoria = categoriaDAO.buscarPorId(id);

            if(categoria == null){
                System.out.println("\n❌ Categoria não encontrada!");
            } else {
                List<Despesa> despesas = despesaDAO.listarPorCategoria(id);
                if(!despesas.isEmpty()){
                    System.out.println("\n❌ Esta categoria possui despesas associadas e não pode ser removida.");
                } else {
                    categoriaDAO.remover(id);
                }
            }
        }
    }

    private static void filtarDespesasPorPeriodo(Scanner sc){
        if(despesaDAO.listarTodas().isEmpty()){
            System.out.println("\n❌ Não há despesas cadastradas!");
        } else {

            LocalDate dataInicio = lerData(sc, "Digite a data de início");
            LocalDate dataFinal = lerData(sc, "Digite a data Final");

            List<Despesa> despesas = despesaDAO.filtrarPorPeriodo(dataInicio, dataFinal);

            if(despesas.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.printf("\n❌ Nenhuma despesa encontrada no período (%s) até (%s)\n", dataInicio.format(formatter) , dataFinal.format(formatter));
            } else {
                despesas.forEach(System.out::println);
            }
        }
    }

    private static void calcularTotalMensal(Scanner sc){
        if(despesaDAO.listarTodas().isEmpty()){
            System.out.println("\n❌ Não há despesas cadastradas");
        } else {
            int ano = lerInt(sc, "\nInforme o ano: ");
            int mes = lerInt(sc, "Informe o mês: ");

            BigDecimal total = despesaDAO.totalMensal(mes, ano);
            System.out.println("\n✅ (" + mes + "/" + ano +") | Total: " + total);
        }
    }
}
