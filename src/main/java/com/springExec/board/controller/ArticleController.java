package com.springExec.board.controller;

import com.springExec.board.domain.type.SearchType;
import com.springExec.board.response.ArticleResponse;
import com.springExec.board.response.ArticleWithCommentsResponse;
import com.springExec.board.service.ArticleService;
import com.springExec.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/articles")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC ) Pageable pageable,
            ModelMap map)
    {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        map.addAttribute("articles", articles);  //dto를 response 형태로 바꿔서 리턴
        map.addAttribute("paginationBarNumbers", paginationBarNumbers);  //dto를 response 형태로 바꿔서 리턴
        map.addAttribute("searchTypes", searchType.values());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(ModelMap map, @PathVariable Long articleId)
    {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());
        map.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchHashtag(@RequestParam(required = false) String searchValue, @PageableDefault(size=10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map){
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();
        map.addAttribute("articles", articles);  //dto를 response 형태로 바꿔서 리턴
        map.addAttribute("paginationBarNumbers", paginationBarNumbers);  //dto를 response 형태로 바꿔서 리턴
        map.addAttribute("hashtags", hashtags);  //dto를 response 형태로 바꿔서 리턴
        map.addAttribute("searchType", SearchType.HASHTAG);  //dto를 response 형태로 바꿔서 리턴

        return "articles/search-hashtag";
    }

    }
