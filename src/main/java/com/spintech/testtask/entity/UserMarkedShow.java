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
@Table(name = "user_show", uniqueConstraints = { @UniqueConstraint( columnNames = { "user", "show" })})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserMarkedShow.UserMarkedShowId.class)
public class UserMarkedShow {

    @Id
    @Column
    public Long user;

    @Id
    @Column
    public Long show;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class UserMarkedShowId implements Serializable {

        private Long user;
        private Long show;
    }
}
