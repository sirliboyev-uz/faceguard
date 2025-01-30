package com.example.faceguard.model;

import com.example.faceguard.template.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private String contentType;
    private float faceEmbeddings;
    private float fileSize;
    @Column(nullable = false)
    private byte[] bytes;
    @OneToOne
    private Users users;
}
