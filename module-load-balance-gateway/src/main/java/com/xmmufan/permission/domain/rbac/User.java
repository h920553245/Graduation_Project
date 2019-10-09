package com.xmmufan.permission.domain.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 黄源钦
 * @Description
 * @Date 11:03
 */
@Entity
@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "info_id")
    private UserInfo info;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private UserAccount account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission_mapper", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> customizePermission;

    public User() {
    }
}