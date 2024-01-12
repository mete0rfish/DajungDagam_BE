package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "areas")
@Table
@Getter
@Setter
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue
    @Column(name = "area_id")

    private int id;

    @Column(name = "GUNAME")
    private String guName;

    @Column(name = "DONGNAME")
    public String dongName;

    @Builder
    public Area(int id, String guName, String dongName)
    {
        this.id = id;
        this.guName = guName;
        this.dongName = dongName;
    }
}
