package com.aguillen.supermarketshoppingmobile.validate;

import com.aguillen.supermarketshoppingmobile.model.Article;

public class ValidateArticle {

    public static boolean validateArticle(Article article) {
        if(!article.getName().isEmpty() && article.getName() != null) {
            return true;
        } else {
            return false;
        }
    }

}
