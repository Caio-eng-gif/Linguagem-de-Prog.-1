package Janelas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Janela {
    private int id;
    private int largura;
    private int altura;
    private boolean aberta;

    public Janela(int id, int largura, int altura, boolean aberta) {
        this.id = id;
        this.largura = largura;
        this.altura = altura;
        this.aberta = aberta;
    }

    public int getId() {
        return id;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public void salvarNoBancoDeDados(BancoDeDados bancoDeDados) {
        String query = "INSERT INTO janelas (id, largura, altura, aberta) VALUES (?, ?, ?, ?)";

        try (Connection connection = bancoDeDados.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, largura);
            statement.setInt(3, altura);
            statement.setBoolean(4, aberta);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BancoDeDados bancoDeDados = new BancoDeDados("jdbc:mysql://localhost:3306/nome_do_banco", "usuario", "senha");

        // Criação da tabela no banco de dados
        bancoDeDados.criarTabela();

        Janela janela1 = new Janela(1, 800, 600, true);
        janela1.salvarNoBancoDeDados(bancoDeDados);

        Janela janela2 = new Janela(2, 1024, 768, false);
        janela2.salvarNoBancoDeDados(bancoDeDados);
    }
}

public class BancoDeDados {
    private Connection connection;

    public BancoDeDados(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void criarTabela() {
        String query = "CREATE TABLE IF NOT EXISTS janelas (" +
                "id INT PRIMARY KEY, " +
                "largura INT, " +
                "altura INT, " +
                "aberta BOOLEAN" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

