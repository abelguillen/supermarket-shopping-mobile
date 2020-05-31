package com.aguillen.supermarketshoppingmobile.util;

import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static ArticleDTO buildDTO(Article article) {
        return modelMapper.map(article, ArticleDTO.class);
    }

    public static Article buildBO(ArticleDTO articleDTO) {
        return modelMapper.map(articleDTO, Article.class);
    }

    public static List<ArticleDTO> convertBoToDto(List<Article> articles) {
        List<ArticleDTO> articlesDTO = new ArrayList<ArticleDTO>();
        for(Article article : articles) {
            articlesDTO.add(buildDTO(article));
        }
        return articlesDTO;
    }

}
