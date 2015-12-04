package com.fererlab.oo.model;

import com.fererlab.oo.commons.model.BaseModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "OOP_COMP_TABLE")
public class Computer extends BaseModel<Computer, Integer> {

    @Id
    @Column(name = "CMP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMP_TABLE_SEQ")
    @SequenceGenerator(name = "COMP_TABLE_SEQ", sequenceName = "COMP_TABLE_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "CMP_BRAND", length = 100)
    private String brand;

    public Computer() {
    }

    public Computer(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", Parent='" + super.toString() + '\'' +
                '}';
    }
}
