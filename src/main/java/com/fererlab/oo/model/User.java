package com.fererlab.oo.model;


import com.fererlab.oo.commons.model.AuditModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@Table(name = "OOP_USER_TABLE")
public class User extends AuditModel<User, Integer> {

    private static final long serialVersionUID = -2573763017488910282L;

    @Id
    @Column(name = "USR_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_TABLE_SEQ")
    @SequenceGenerator(name = "USER_TABLE_SEQ", sequenceName = "USER_TABLE_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "USR_PASSWORD", length = 100)
    private String password;

    @Column(name = "USR_USERNAME", length = 100)
    private String username;

    public User() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", Parent='" + super.toString() + '\'' +
                '}';
    }
}
