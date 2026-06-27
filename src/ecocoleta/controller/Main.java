package ecocoleta.controller;

import ecocoleta.model.PontoColeta;
import ecocoleta.model.TipoMaterial;
import ecocoleta.model.Usuario;
import ecocoleta.service.*;
import ecocoleta.util.ConexaoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║              EcoColeta — Aplicação Principal             ║
 * ║  Engenharia de Software II — 2026                        ║
 * ║  Grupo: Julio Cezar Melo · Jean José · Gabriel Vieira   ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * Ponto de entrada do sistema. Exibe um menu de texto no console
 * que cobre todos os requisitos funcionais (RF-01 a RF-07).
 *
 * Como executar no Apache NetBeans:
 *   1. Abra o projeto (File → Open Project)
 *   2. Clique com botão direito em Main.java → Run File
 *      (ou pressione Shift+F6)
 */
public class Main {

    private static final UsuarioService        usuarioService = new UsuarioService();
    private static final PontoColetaService    pontoService   = new PontoColetaService();
    private static final AvaliacaoService      avaliacaoService = new AvaliacaoService();
    private static final GuiaInformativoService guiaService   = new GuiaInformativoService();

    private static final Scanner sc = new Scanner(System.in);

   
    private static Usuario usuarioLogado = null;


    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("  Bem-vindo ao EcoColeta v1.0 MVP  ");
        System.out.println("====================================");

        // Testa a conexão antes de tudo
        ConexaoDB.testarConexao();

        boolean rodando = true;
        while (rodando) {
            if (usuarioLogado == null) {
                rodando = menuAcesso();
            } else {
                rodando = menuPrincipal();
            }
        }

        System.out.println("\nObrigado por usar o EcoColeta! Até mais.");
        sc.close();
    }

  
    private static boolean menuAcesso() {
        System.out.println("\n--- MENU DE ACESSO ---");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar novo usuário");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
        String opcao = sc.nextLine().trim();

        switch (opcao) {
            case "1": fazerLogin();    break;
            case "2": cadastrarUsuario(); break;
            case "0": return false;
            default:  System.out.println("Opção inválida.");
        }
        return true;
    }


    private static boolean menuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL --- [" + usuarioLogado.getNome()
                         + " | " + usuarioLogado.getTipo() + "]");
        System.out.println("1. Listar pontos de coleta");
        System.out.println("2. Buscar ponto por ID");
        System.out.println("3. Filtrar pontos por tipo de material");
        System.out.println("4. Cadastrar ponto de coleta");
        System.out.println("5. Avaliar ponto de coleta");
        System.out.println("6. Ver avaliações de um ponto");
        System.out.println("7. Ver guias informativos");
        System.out.println("8. Cadastrar guia informativo");


        if ("ADMIN".equals(usuarioLogado.getTipo())) {
            System.out.println("9. [ADMIN] Listar usuários");
            System.out.println("D. [ADMIN] Desativar ponto");
        }

        System.out.println("L. Logout");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
        String opcao = sc.nextLine().trim().toUpperCase();

        switch (opcao) {
            case "1": listarPontos();          break;
            case "2": buscarPontoPorId();      break;
            case "3": filtrarPorMaterial();    break;
            case "4": cadastrarPonto();        break;
            case "5": avaliarPonto();          break;
            case "6": verAvaliacoes();         break;
            case "7": listarGuias();           break;
            case "8": cadastrarGuia();         break;
            case "9":
                if ("ADMIN".equals(usuarioLogado.getTipo())) listarUsuarios();
                else System.out.println("Acesso negado.");
                break;
            case "D":
                if ("ADMIN".equals(usuarioLogado.getTipo())) desativarPonto();
                else System.out.println("Acesso negado.");
                break;
            case "L": usuarioLogado = null; System.out.println("Logout realizado."); break;
            case "0": return false;
            default:  System.out.println("Opção inválida.");
        }
        return true;
    }

 
   private static void fazerLogin() {
        System.out.println("\n-- Login --");
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            // O try tenta executar o código que pode dar erro de banco de dados
            Usuario u = usuarioService.login(email, senha);
            if (u != null) {
                usuarioLogado = u;
                System.out.println("Login bem-sucedido! Bem-vindo(a), " + u.getNome() + ".");
            } else {
                System.out.println("E-mail ou senha incorretos.");
            }
        } catch (java.sql.SQLException e) {
            // Se o banco de dados falhar, o catch captura o erro e evita que o programa mude/caia de forma abrupta
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private static void cadastrarUsuario() {
        System.out.println("\n-- Cadastro de Usuário --");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Tipo (CIDADAO / EMPRESA): ");
        String tipo = sc.nextLine().toUpperCase().trim();

        String resultado = usuarioService.cadastrar(nome, email, senha, tipo);
        System.out.println(resultado);
    }

 
    private static void listarPontos() {
        System.out.println("\n-- Pontos de Coleta Disponíveis --");
        List<PontoColeta> pontos = pontoService.listarTodos();

        if (pontos.isEmpty()) {
            System.out.println("Nenhum ponto de coleta cadastrado.");
            return;
        }

        for (PontoColeta p : pontos) {
            System.out.printf("[%d] %s — %s%n", p.getId(), p.getNome(), p.getEndereco());
            if (!p.getMateriais().isEmpty()) {
                System.out.print("    Materiais: ");
                for (TipoMaterial m : p.getMateriais()) {
                    System.out.print(m.getNome() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void buscarPontoPorId() {
        System.out.print("\nDigite o ID do ponto: ");
        int id = lerInt();
        PontoColeta p = pontoService.buscarPorId(id);

        if (p == null) {
            System.out.println("Ponto não encontrado.");
        } else {
            System.out.printf("\n[%d] %s%n", p.getId(), p.getNome());
            System.out.println("Endereço : " + p.getEndereco());
            System.out.println("Descrição: " + p.getDescricao());
            if (!p.getMateriais().isEmpty()) {
                System.out.print("Materiais: ");
                for (TipoMaterial m : p.getMateriais()) System.out.print(m.getNome() + " ");
                System.out.println();
            }
        }
    }

  
    private static void filtrarPorMaterial() {
        System.out.println("\n-- Filtrar por Tipo de Material --");
        System.out.println("IDs comuns: 1=Plástico  2=Vidro  3=Papel  4=Metal  5=Eletrônico");
        System.out.println("            6=Medicamento  7=Agrotóxico  8=Óleo de Cozinha");
        System.out.print("ID do material: ");
        int id = lerInt();

        List<PontoColeta> pontos = pontoService.filtrarPorMaterial(id);
        if (pontos.isEmpty()) {
            System.out.println("Nenhum ponto encontrado para esse material.");
        } else {
            for (PontoColeta p : pontos) {
                System.out.printf("[%d] %s — %s%n", p.getId(), p.getNome(), p.getEndereco());
            }
        }
    }


    private static void cadastrarPonto() {
        // Apenas EMPRESA ou ADMIN podem cadastrar pontos
        if ("CIDADAO".equals(usuarioLogado.getTipo())) {
            System.out.println("Acesso negado. Apenas EMPRESA ou ADMIN podem cadastrar pontos.");
            return;
        }

        System.out.println("\n-- Cadastrar Ponto de Coleta --");
        System.out.print("Nome do ponto: ");
        String nome = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

       
        System.out.println("Digite os IDs dos materiais aceitos separados por vírgula");
        System.out.println("  (ex: 1,2,5  para Plástico, Vidro e Eletrônico):");
        System.out.print("Materiais: ");
        String[] ids = sc.nextLine().split(",");
        List<TipoMaterial> materiais = new ArrayList<>();
        for (String idStr : ids) {
            try {
                TipoMaterial tm = new TipoMaterial();
                tm.setId(Integer.parseInt(idStr.trim()));
                materiais.add(tm);
            } catch (NumberFormatException e) {
                // ignora entrada inválida
            }
        }

        String resultado = pontoService.cadastrar(nome, endereco, descricao,
                                                   usuarioLogado.getId(), materiais);
        System.out.println(resultado);
    }

   
    private static void avaliarPonto() {
        System.out.println("\n-- Avaliar Ponto de Coleta --");
        System.out.print("ID do ponto: ");
        int idPonto = lerInt();
        System.out.print("Nota (1 a 5): ");
        int nota = lerInt();
        System.out.print("Comentário (opcional): ");
        String comentario = sc.nextLine();

        String resultado = avaliacaoService.avaliar(nota, comentario,
                                                     usuarioLogado.getId(), idPonto);
        System.out.println(resultado);
    }


    private static void verAvaliacoes() {
        System.out.print("\nID do ponto: ");
        int idPonto = lerInt();

        System.out.println(avaliacaoService.mediaPorPonto(idPonto));
        var lista = avaliacaoService.listarPorPonto(idPonto);
        if (lista.isEmpty()) {
            System.out.println("Nenhuma avaliação para este ponto.");
        } else {
            lista.forEach(a -> System.out.printf(
                "  Nota: %d | %s%n", a.getNota(),
                a.getComentario() != null ? a.getComentario() : "(sem comentário)"));
        }
    }

    private static void listarGuias() {
        System.out.println("\n-- Guias Informativos --");
        var guias = guiaService.listarTodos();
        if (guias.isEmpty()) {
            System.out.println("Nenhum guia cadastrado.");
            return;
        }
        guias.forEach(g -> {
            System.out.println("\n[" + g.getId() + "] " + g.getTitulo());
            System.out.println(g.getConteudo());
        });
    }

  
    private static void cadastrarGuia() {
        if ("CIDADAO".equals(usuarioLogado.getTipo())) {
            System.out.println("Acesso negado. Apenas EMPRESA ou ADMIN podem criar guias.");
            return;
        }

        System.out.println("\n-- Cadastrar Guia Informativo --");
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Conteúdo: ");
        String conteudo = sc.nextLine();

        System.out.println(guiaService.cadastrar(titulo, conteudo, usuarioLogado.getId()));
    }

 
    private static void listarUsuarios() {
        System.out.println("\n-- Lista de Usuários --");
        usuarioService.listarTodos().forEach(u ->
            System.out.printf("[%d] %s | %s | %s | Ativo: %b%n",
                u.getId(), u.getNome(), u.getEmail(), u.getTipo(), u.isAtivo()));
    }

  
    private static void desativarPonto() {
        System.out.print("\nID do ponto a desativar: ");
        int id = lerInt();
        System.out.println(pontoService.desativar(id));
    }

  
    private static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, usando 0.");
            return 0;
        }
    }
}
