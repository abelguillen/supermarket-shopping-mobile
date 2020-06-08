package com.aguillen.supermarketshoppingmobile.model;

public class Shopping {
    private Article article;
    private Integer cantidad;

    public Shopping(Article article, Integer cantidad) {
        this.article = article;
        this.cantidad = cantidad;
    }

    public Shopping() {}

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
