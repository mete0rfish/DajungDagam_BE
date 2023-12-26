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
    @Column
    private String gu_name;
    @Column
    private String dong_name;
}
