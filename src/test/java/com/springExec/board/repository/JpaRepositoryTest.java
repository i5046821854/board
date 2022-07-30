package com.springExec.board.repository;

import com.springExec.board.domain.Article;
import com.springExec.board.config.JpaConfig;
import com.springExec.board.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import({JpaRepositoryTest.TestJpaConfig.class})
@DataJpaTest   //자동  autowired해줌
class JpaRepositoryTest {

    public final ArticleCommentRepository articleCommentRepository;
    public final ArticleRepository articleRepository;
    public final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleCommentRepository articleCommentRepository, @Autowired ArticleRepository articleRepository, @Autowired UserAccountRepository userAccountRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelect(){
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).isNotNull().hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInsert(){
        long prevCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("lee2g", "pw", null, null, null));
        Article article = Article.of(userAccount, "new Article", "new content", "#hashtag");
        articleRepository.save(article);
        assertThat(articleRepository.count()).isEqualTo(prevCount+1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdate(){
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashTag = "#Spring";
        article.setHashtag(updatedHashTag);

        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashTag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDelete(){
        Article article = articleRepository.findById(1L).orElseThrow();
        long prevArticleCount = articleRepository.count();
        long prevArticleComCount = articleCommentRepository.count();
        int deleteComSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleComCount - deleteComSize);

    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig{
        @Bean
        public AuditorAware<String> auditorAware(){
            return () -> Optional.of("uno");
        }
    }

}