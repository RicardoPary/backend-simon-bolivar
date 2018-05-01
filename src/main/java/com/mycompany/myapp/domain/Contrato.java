package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Contrato.
 */
@Entity
@Table(name = "contrato")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha_inicio")
    private String fecha_inicio;

    @Column(name = "fecha_fin")
    private String fecha_fin;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "sueldo")
    private Double sueldo;

    @Column(name = "vacaciones")
    private String vacaciones;

    @Column(name = "jornada_diaria")
    private String jornada_diaria;

    @ManyToOne
    private Docente docente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public Contrato fecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
        return this;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public Contrato fecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
        return this;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTipo() {
        return tipo;
    }

    public Contrato tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public Contrato sueldo(Double sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public String getVacaciones() {
        return vacaciones;
    }

    public Contrato vacaciones(String vacaciones) {
        this.vacaciones = vacaciones;
        return this;
    }

    public void setVacaciones(String vacaciones) {
        this.vacaciones = vacaciones;
    }

    public String getJornada_diaria() {
        return jornada_diaria;
    }

    public Contrato jornada_diaria(String jornada_diaria) {
        this.jornada_diaria = jornada_diaria;
        return this;
    }

    public void setJornada_diaria(String jornada_diaria) {
        this.jornada_diaria = jornada_diaria;
    }

    public Docente getDocente() {
        return docente;
    }

    public Contrato docente(Docente docente) {
        this.docente = docente;
        return this;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
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
        Contrato contrato = (Contrato) o;
        if (contrato.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contrato.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contrato{" +
            "id=" + getId() +
            ", fecha_inicio='" + getFecha_inicio() + "'" +
            ", fecha_fin='" + getFecha_fin() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", sueldo=" + getSueldo() +
            ", vacaciones='" + getVacaciones() + "'" +
            ", jornada_diaria='" + getJornada_diaria() + "'" +
            "}";
    }
}
