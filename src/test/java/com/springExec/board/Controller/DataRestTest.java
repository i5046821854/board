package com.springExec.board.Controller;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@Disabled("Spring Data Rest에 대한 테스트는 제외")
@DisplayName("Data Rest API Test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("게시글 리스트 조회 - api")
    @Test
    void test1() throws Exception {
        mvc.perform(get("/api/articles")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("게시글 댓글 리스트 조회 - api")
    @Test
    void test2() throws Exception {
        mvc.perform(get("/api/articleComments")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

}
