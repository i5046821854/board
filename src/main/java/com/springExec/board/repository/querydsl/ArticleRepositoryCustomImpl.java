package com.springExec.board.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import com.springExec.board.domain.Article;
import com.springExec.board.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;
        JPQLQuery<String> query = from(article).distinct().select(article.hashtag).where(article.hashtag.isNotNull());
        return query.fetch();
    }

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }
}
