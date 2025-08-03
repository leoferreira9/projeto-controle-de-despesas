package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Despesa {

    private int id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Categoria categoria;

    public Despesa(){}

    public Despesa(int id, String descricao, BigDecimal valor, LocalDate data, Categoria categoria){
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return String.format(
                "\nDescrição: %s\nValor: %.2f\nData: %s\nCategoria: %s",
                descricao, valor, data.format(formatter), categoria.getNome()
        );
    }
}
