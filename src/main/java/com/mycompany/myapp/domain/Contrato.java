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

    @Column(name = "sueldo")
    private Double sueldo;

    @ManyToOne
    private Docente docente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", sueldo=" + getSueldo() +
            "}";
    }
}
