package com.springExec.board.response;

import com.springExec.board.dto.ArticleWithCommentsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        String userid,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Set<ArticleCommentsResponse> articleCommentResponses
) {

    public static ArticleWithCommentsResponse of(Long id, String title, String content, String hashtag, String userid, LocalDateTime createdAt, String email, String nickname, Set<ArticleCommentsResponse> articleCommentResponses) {
        return new ArticleWithCommentsResponse(id, title, content, hashtag, userid,  createdAt, email, nickname, articleCommentResponses);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.userAccountDto().userId(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.articleCommentDtos().stream()
                        .map(ArticleCommentsResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}