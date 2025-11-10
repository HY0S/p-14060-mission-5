package com.back.domain.wiseSaying.entity;


public class QueryParse {
    private String keywordType;
    private String keyword;
    private int page;

    public QueryParse() {
        this.keywordType = null;
        this.keyword = null;
        this.page = 1;
    }

    public QueryParse(String keywordType, String keyword) {
        this.keywordType = keywordType;
        this.keyword = keyword;
        this.page = 1;
    }

    public QueryParse(int page, String keywordType, String keyword) {
        this.keywordType = keywordType;
        this.keyword = keyword;
        this.page = page;

    }
    public String getKeywordType() {
        return keywordType;
    }
    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
}
