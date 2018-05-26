package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Docente.
 */
@Entity
@Table(name = "docente")
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

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

    public String getTipo() {
        return tipo;
    }

    public Docente tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public Docente idPersona(Long idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public Docente idTrabajador(Long idTrabajador) {
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
        Docente docente = (Docente) o;
        if (docente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Docente{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", idPersona=" + getIdPersona() +
            ", idTrabajador=" + getIdTrabajador() +
            "}";
    }
}
