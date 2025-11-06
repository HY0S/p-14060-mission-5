package com.back.domain.wiseSaying.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wise_saying")
@Getter
@Setter
public class WiseSaying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 1000)
    private String content;
    
    @Column(nullable = false, length = 100)
    private String author;
    
    public WiseSaying() {}
    
    public WiseSaying(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
    
    // int id를 사용하는 생성자 (기존 코드 호환성)
    public WiseSaying(int id, String content, String author) {
        this.id = (long) id;
        this.content = content;
        this.author = author;
    }
    
    // int 타입으로 id를 반환하는 메서드 (기존 코드 호환성)
    public int getIdAsInt() {
        return id != null ? id.intValue() : 0;
    }
}
