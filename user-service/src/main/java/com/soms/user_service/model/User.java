package com.soms.user_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    private String email;
    private String phone;
    private String address;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin = false;
}
