package com.dajungdagam.dg.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.dajungdagam.dg.domain.entity.Recommendation;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecommendOutputDto {

    private int user_id;

    private List<Recommendation> recommendations;

    public RecommendOutputDto(int user_id, List<Recommendation> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }
}