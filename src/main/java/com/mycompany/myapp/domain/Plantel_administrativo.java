package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Plantel_administrativo.
 */
@Entity
@Table(name = "plantel_administrativo")
public class Plantel_administrativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "plantel_administrativos")
    @JsonIgnore
    private Set<Actividad_civica> actividad_civicas = new HashSet<>();

    @ManyToOne
    private Persona persona;

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

    public Plantel_administrativo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Actividad_civica> getActividad_civicas() {
        return actividad_civicas;
    }

    public Plantel_administrativo actividad_civicas(Set<Actividad_civica> actividad_civicas) {
        this.actividad_civicas = actividad_civicas;
        return this;
    }

    public Plantel_administrativo addActividad_civica(Actividad_civica actividad_civica) {
        this.actividad_civicas.add(actividad_civica);
        actividad_civica.getPlantel_administrativos().add(this);
        return this;
    }

    public Plantel_administrativo removeActividad_civica(Actividad_civica actividad_civica) {
        this.actividad_civicas.remove(actividad_civica);
        actividad_civica.getPlantel_administrativos().remove(this);
        return this;
    }

    public void setActividad_civicas(Set<Actividad_civica> actividad_civicas) {
        this.actividad_civicas = actividad_civicas;
    }

    public Persona getPersona() {
        return persona;
    }

    public Plantel_administrativo persona(Persona persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
        Plantel_administrativo plantel_administrativo = (Plantel_administrativo) o;
        if (plantel_administrativo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantel_administrativo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plantel_administrativo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
