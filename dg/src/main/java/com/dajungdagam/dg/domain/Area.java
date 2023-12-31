package com.dajungdagam.dg.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "areas")
@Table
@Getter
@Setter
public class Area {
    @Id
    @Column(name = "area_id")
    private int id;
    @Column(name = "GUNAME")
    private String guName;
    @Column(name = "DONGNAME")
    private String dongName;
}
