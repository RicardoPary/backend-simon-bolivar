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
    private String fechaInicio;

    @Column(name = "fecha_fin")
    private String fechaFin;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "sueldo")
    private Double sueldo;

    @Column(name = "vacaciones")
    private String vacaciones;

    @Column(name = "jornada_diaria")
    private String jornadaDiaria;

    @Column(name = "id_docente")
    private Long idDocente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public Contrato fechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public Contrato fechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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

    public String getJornadaDiaria() {
        return jornadaDiaria;
    }

    public Contrato jornadaDiaria(String jornadaDiaria) {
        this.jornadaDiaria = jornadaDiaria;
        return this;
    }

    public void setJornadaDiaria(String jornadaDiaria) {
        this.jornadaDiaria = jornadaDiaria;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public Contrato idDocente(Long idDocente) {
        this.idDocente = idDocente;
        return this;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
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
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", sueldo=" + getSueldo() +
            ", vacaciones='" + getVacaciones() + "'" +
            ", jornadaDiaria='" + getJornadaDiaria() + "'" +
            ", idDocente=" + getIdDocente() +
            "}";
    }
}
