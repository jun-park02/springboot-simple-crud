package com.junhyeok.crud.post;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    // IDENTITY는 AUTO_INCREMENT방식
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성일시
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    // 제목
    @Column(nullable = false, length = 200)
    private String title;

    // Lob는 Large Object. 즉 큰 데이터를 저장할 때 사용하는 표시
    // 필드 타입에 따라 의미가 달라짐
    // String이면 CLOB 형태의 큰 문자열로 매핑됨
    @Lob 
    @Column(nullable = false)
    private String content;

    // 기본 생성자가 항상 필요한가?
    // JPA 엔티티에는 기본 생성자가 항상 필요
    // JPA가 데이터베이스 조회 결과로 객체를 만들 때, 내부적으로 매개변수가 없는 생성자를 사용
    // JPA 기본 생성자는 public 또는 protected여야 함. 보통 외부에서 의미 없이 호추하는 것을 막기 위해
    // protected로 사용
    public Post() {
    }

    // 이 생성자도 항상 필요한가?
    // JPA 때문에 필요한 것은 아님. 개발자가 Post객체를 편하게 만들기 위한 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // createdAt을 실제로 자동 설정하는 부분
    // 최초 저장 직전 호출되는 콜백
    // 메서드 이름은 자유롭게 정할 수 있음
    @PrePersist 
    public void prePersist() { 
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
