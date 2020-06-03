package com.aguillen.supermarketshoppingmobile.util;

import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.dto.CategoryDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.model.Category;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private static ModelMapper modelMapper = new ModelMapper();

    //Article

    public static ArticleDTO buildArticleDTO(Article article) {
        return modelMapper.map(article, ArticleDTO.class);
    }

    public static Article buildArticleBO(ArticleDTO articleDTO) {
        return modelMapper.map(articleDTO, Article.class);
    }

    public static List<ArticleDTO> convertArticleBoToDto(List<Article> articles) {
        List<ArticleDTO> articlesDTO = new ArrayList<ArticleDTO>();
        for(Article article : articles) {
            articlesDTO.add(buildArticleDTO(article));
        }
        return articlesDTO;
    }


    // Category

    public static CategoryDTO buildCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public static Category buildCategoryBO(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public static List<CategoryDTO> convertCategoryBoToDto(List<Category> categorys) {
        List<CategoryDTO> categorysDTO = new ArrayList<CategoryDTO>();
        for(Category category : categorys) {
            categorysDTO.add(buildCategoryDTO(category));
        }
        return categorysDTO;
    }

}
