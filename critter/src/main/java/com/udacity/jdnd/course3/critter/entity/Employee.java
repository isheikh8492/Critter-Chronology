package com.udacity.jdnd.course3.critter.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "tblEmployee")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;
    @ElementCollection
    @CollectionTable(name = "tblSkills")
    @Enumerated(EnumType.STRING)
    @Column(name = "skills", nullable = false)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @CollectionTable(name = "tblDaysAvailable")
    @Enumerated(EnumType.STRING)
    @Column(name = "daysAvailable", nullable = false)
    private Set<DayOfWeek> daysAvailable;
}
