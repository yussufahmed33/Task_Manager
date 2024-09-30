package com.example.Task.Maneger.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name ="roles")
public class roles implements GrantedAuthority {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
@Column(nullable = false, unique = true)
private String roleName;


    @Override
    public String getAuthority() {
        return roleName;
    }
}
