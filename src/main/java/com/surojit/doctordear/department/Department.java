package com.surojit.doctordear.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.surojit.doctordear.center.Center;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "department")
@ToString
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @JsonBackReference
    private Center center;
}
