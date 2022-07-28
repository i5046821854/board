package com.springExec.board.service;

import com.springExec.board.domain.Article;
import com.springExec.board.domain.type.SearchType;
import com.springExec.board.dto.ArticleDto;
import com.springExec.board.dto.ArticleWithCommentsDto;
import com.springExec.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()){  //서치 타입 안 넣을 때
            return articleRepository.findAll(pageable).map(ArticleDto::from);  //entity에서 dto로 변환
        }
        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(()->new EntityNotFoundException("게시글이 없다"));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());//dto를 엔티티로 변환한 뒤 세이브
    }

    public void updateArticle(ArticleDto dto) {
        try
        {
            Article article = articleRepository.getReferenceById(dto.id());
            if(dto.content() != null) article.setContent(dto.content());  //타이틀, 컨텐트는 not null이니까 dto의 해당 값이 null이 아닐때만 바꿔주는 걸로
            if(dto.title() != null) article.setTitle(dto.title());
            article.setHashtag(dto.hashtag());
        }catch (EntityNotFoundException e)
        {
            log.warn("게시글 업데이트 실패, 게시글을 찾을 수 없음 - dto : {}", dto);
        }
//        articleRepository.save(article);  필요없음. set으로 변경 감지
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}
