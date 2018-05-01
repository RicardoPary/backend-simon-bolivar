package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Nota.
 */
@Entity
@Table(name = "nota")
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "bimestre")
    private Integer bimestre;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "area")
    private String area;

    @ManyToOne
    @JsonIgnore
    private Libreta libreta;

    @ManyToOne
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public Nota valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public Nota bimestre(Integer bimestre) {
        this.bimestre = bimestre;
        return this;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public String getObservacion() {
        return observacion;
    }

    public Nota observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getArea() {
        return area;
    }

    public Nota area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Libreta getLibreta() {
        return libreta;
    }

    public Nota libreta(Libreta libreta) {
        this.libreta = libreta;
        return this;
    }

    public void setLibreta(Libreta libreta) {
        this.libreta = libreta;
    }

    public Materia getMateria() {
        return materia;
    }

    public Nota materia(Materia materia) {
        this.materia = materia;
        return this;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nota nota = (Nota) o;
        if (nota.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nota.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Nota{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", bimestre=" + getBimestre() +
            ", observacion='" + getObservacion() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }
}
