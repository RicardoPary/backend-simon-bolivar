package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlantelAdministrativo.
 */
@Entity
@Table(name = "plantel_administrativo")
public class PlantelAdministrativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_persona")
    private Long idPersona;

    @ManyToMany(mappedBy = "plantelAdministrativos")
    @JsonIgnore
    private Set<ActividadCivica> actividadCivicas = new HashSet<>();

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

    public PlantelAdministrativo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public PlantelAdministrativo idPersona(Long idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Set<ActividadCivica> getActividadCivicas() {
        return actividadCivicas;
    }

    public PlantelAdministrativo actividadCivicas(Set<ActividadCivica> actividadCivicas) {
        this.actividadCivicas = actividadCivicas;
        return this;
    }

    public PlantelAdministrativo addActividadCivica(ActividadCivica actividadCivica) {
        this.actividadCivicas.add(actividadCivica);
        actividadCivica.getPlantelAdministrativos().add(this);
        return this;
    }

    public PlantelAdministrativo removeActividadCivica(ActividadCivica actividadCivica) {
        this.actividadCivicas.remove(actividadCivica);
        actividadCivica.getPlantelAdministrativos().remove(this);
        return this;
    }

    public void setActividadCivicas(Set<ActividadCivica> actividadCivicas) {
        this.actividadCivicas = actividadCivicas;
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
        PlantelAdministrativo plantelAdministrativo = (PlantelAdministrativo) o;
        if (plantelAdministrativo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantelAdministrativo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlantelAdministrativo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", idPersona=" + getIdPersona() +
            "}";
    }
}
