package com.springExec.board.response;

import com.springExec.board.dto.ArticleCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentsResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String userid,
        String nickname
) {

    public static ArticleCommentsResponse of(Long id, String content, String userid, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleCommentsResponse(id, content,  createdAt, userid, email, nickname);
    }

    public static ArticleCommentsResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleCommentsResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().userId(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}