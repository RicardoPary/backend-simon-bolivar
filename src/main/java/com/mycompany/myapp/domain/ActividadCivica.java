package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ActividadCivica.
 */
@Entity
@Table(name = "actividad_civica")
public class ActividadCivica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cronograma")
    private String cronograma;

    @Column(name = "fecha")
    private String fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public ActividadCivica nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ActividadCivica descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCronograma() {
        return cronograma;
    }

    public ActividadCivica cronograma(String cronograma) {
        this.cronograma = cronograma;
        return this;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public String getFecha() {
        return fecha;
    }

    public ActividadCivica fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
        ActividadCivica actividadCivica = (ActividadCivica) o;
        if (actividadCivica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividadCivica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActividadCivica{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cronograma='" + getCronograma() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
