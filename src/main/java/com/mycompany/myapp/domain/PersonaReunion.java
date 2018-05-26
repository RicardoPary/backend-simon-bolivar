package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonaReunion.
 */
@Entity
@Table(name = "persona_reunion")
public class PersonaReunion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_reunion")
    private Long idReunion;

    @Column(name = "fecha")
    private String fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public PersonaReunion idPersona(Long idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdReunion() {
        return idReunion;
    }

    public PersonaReunion idReunion(Long idReunion) {
        this.idReunion = idReunion;
        return this;
    }

    public void setIdReunion(Long idReunion) {
        this.idReunion = idReunion;
    }

    public String getFecha() {
        return fecha;
    }

    public PersonaReunion fecha(String fecha) {
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
        PersonaReunion personaReunion = (PersonaReunion) o;
        if (personaReunion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personaReunion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonaReunion{" +
            "id=" + getId() +
            ", idPersona=" + getIdPersona() +
            ", idReunion=" + getIdReunion() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
