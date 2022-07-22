package com.springExec.board.Controller;

import com.springExec.board.controller.ArticleController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)  //빈으로 지정할 컨트롤러를 선택
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 페이지 - 정상호출")
    public void givenNothing_whenRequestingArticleView_thenReturnsArticlesView() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))  //뷰의 이름을 체크
                .andExpect(MockMvcResultMatchers.model().attributeExists("articles"));  //모델 어트리뷰트를 검사
    }

    @Disabled("개발 중")
    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    public void givenNothing_whenRequestingArticleView_thenReturnsSingleArticleView() throws Exception {
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))  //뷰의 이름을 체크
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("articleComments"));
    }

    @Disabled("개발 중")
    @Test
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출")
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsSearchView() throws Exception {
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))  //뷰의 이름을 체크
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Disabled("개발 중")
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 - 정상호출")
    public void givenNothing_whenRequestingHashtagSearch_thenReturnsSearchView() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))  //뷰의 이름을 체크
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }


}