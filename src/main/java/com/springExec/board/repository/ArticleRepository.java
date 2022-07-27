package com.springExec.board.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.springExec.board.domain.Article;
import com.springExec.board.domain.QArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>
{

    Page<Article> findByTitle(String title, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);  //선택적인 필드만 검색되도록
        bindings.including(root.title, root.hashtag, root.createdAt, root.createdBy, root.content); //검색될 필드 선택
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  //하나만 검색하고, like 검색, 대소문자 구분 x
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }
}