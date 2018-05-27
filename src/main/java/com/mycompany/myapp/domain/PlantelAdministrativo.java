package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
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

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_trabajador")
    private Long idTrabajador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public PlantelAdministrativo cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public PlantelAdministrativo idTrabajador(Long idTrabajador) {
        this.idTrabajador = idTrabajador;
        return this;
    }

    public void setIdTrabajador(Long idTrabajador) {
        this.idTrabajador = idTrabajador;
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
            ", cargo='" + getCargo() + "'" +
            ", idPersona=" + getIdPersona() +
            ", idTrabajador=" + getIdTrabajador() +
            "}";
    }
}
