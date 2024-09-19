package com.example.bank_account_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false)
    private Date creationDate;

    private Date modificationDate;

    @PrePersist
    public void onPrePersist() {
        setCreationDate(new Date());
        setModificationDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        setModificationDate(new Date());
    }

}
