package com.springExec.board.repository;

import com.springExec.board.domain.Article;
import com.springExec.board.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest   //자동  autowired해줌
class JpaRepositoryTest {

    public final ArticleCommentRepository articleCommentRepository;
    public final ArticleRepository articleRepository;

    public JpaRepositoryTest(@Autowired ArticleCommentRepository articleCommentRepository, @Autowired ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelect(){
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).isNotNull().hasSize(0);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInsert(){
        long prevCount = articleRepository.count();
        articleRepository.save(Article.of("title", "content", "@hashtag"));
        assertThat(articleRepository.count()).isEqualTo(prevCount+1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdate(){
        articleRepository.save(Article.of("title", "content", "@hashtag"));
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashTag = "#Spring";
        article.setHashtag(updatedHashTag);

        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashTag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDelete(){
        articleRepository.save(Article.of("title", "content", "@hashtag"));
        Article article = articleRepository.findById(1L).orElseThrow();
        long prevArticleCount = articleRepository.count();
        long prevArticleComCount = articleCommentRepository.count();
        int deleteComSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleComCount - deleteComSize);

    }


}