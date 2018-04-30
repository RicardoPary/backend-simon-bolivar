package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Familiar.
 */
@Entity
@Table(name = "familiar")
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "parentesco")
    private String parentesco;

    @ManyToOne
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public Familiar parentesco(String parentesco) {
        this.parentesco = parentesco;
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Persona getPersona() {
        return persona;
    }

    public Familiar persona(Persona persona) {
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
        Familiar familiar = (Familiar) o;
        if (familiar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familiar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Familiar{" +
            "id=" + getId() +
            ", parentesco='" + getParentesco() + "'" +
            "}";
    }
}
