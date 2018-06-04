package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Trabajador.
 */
@Entity
@Table(name = "trabajador")
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nit")
    private Long nit;

    @Column(name = "id_persona")
    private Long idPersona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNit() {
        return nit;
    }

    public Trabajador nit(Long nit) {
        this.nit = nit;
        return this;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public Trabajador idPersona(Long idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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
        Trabajador trabajador = (Trabajador) o;
        if (trabajador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trabajador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trabajador{" +
            "id=" + getId() +
            ", nit=" + getNit() +
            ", idPersona=" + getIdPersona() +
            "}";
    }
}
