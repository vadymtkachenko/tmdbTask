package com.spintech.testtask.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Data
@Table(name = "user_actor", uniqueConstraints = { @UniqueConstraint( columnNames = { "user", "actor" })})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserActors.UserActorId.class)
public class UserActors implements Serializable {

    @Id
    @Column
    public Long user;

    @Id
    @Column
    public Long actor;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class UserActorId implements Serializable {

        private Long user;
        private Long actor;
    }
}
