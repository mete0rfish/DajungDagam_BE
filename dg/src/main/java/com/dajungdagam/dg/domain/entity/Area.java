package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "areas")
@Table
@Getter
@Setter
public class Area {
    @Id
    @GeneratedValue
    @Column(name = "area_id")
    public int id;
    @Column(name = "GUNAME")
    public String guName;
    @Column(name = "DONGNAME")
    public String dongName;
}
