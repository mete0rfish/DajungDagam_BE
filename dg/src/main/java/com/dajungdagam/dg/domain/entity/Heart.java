//package com.dajungdagam.dg.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//import static jakarta.persistence.FetchType.LAZY;
//
//@NoArgsConstructor
//@Getter
//@Entity
//public class Heart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "heart_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "post_id")
//    private TradePost tradePost;
//
//    @Builder
//    public Heart(Long id, User user, TradePost tradePost) {
//        this.id = id;
//        this.user = user;
//        this.tradePost = tradePost;
//    }
//
//}
