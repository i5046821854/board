package com.springExec.board.request;

import com.springExec.board.dto.ArticleCommentDto;
import com.springExec.board.dto.UserAccountDto;

public record ArticleCommentRequest (
        Long articleId,
        String content
)
{
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}
