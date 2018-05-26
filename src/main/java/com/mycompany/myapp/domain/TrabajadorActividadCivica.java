package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TrabajadorActividadCivica.
 */
@Entity
@Table(name = "trabajador_actividad_civica")
public class TrabajadorActividadCivica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_trabajador")
    private Long idTrabajador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public TrabajadorActividadCivica idTrabajador(Long idTrabajador) {
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
        TrabajadorActividadCivica trabajadorActividadCivica = (TrabajadorActividadCivica) o;
        if (trabajadorActividadCivica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trabajadorActividadCivica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrabajadorActividadCivica{" +
            "id=" + getId() +
            ", idTrabajador=" + getIdTrabajador() +
            "}";
    }
}
