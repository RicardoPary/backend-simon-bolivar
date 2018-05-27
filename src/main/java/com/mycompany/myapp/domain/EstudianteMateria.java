package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EstudianteMateria.
 */
@Entity
@Table(name = "estudiante_materia")
public class EstudianteMateria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(name = "id_materia")
    private Long idMateria;

    @Column(name = "gestion")
    private Integer gestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public EstudianteMateria idEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
        return this;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public EstudianteMateria idMateria(Long idMateria) {
        this.idMateria = idMateria;
        return this;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Integer getGestion() {
        return gestion;
    }

    public EstudianteMateria gestion(Integer gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
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
        EstudianteMateria estudianteMateria = (EstudianteMateria) o;
        if (estudianteMateria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estudianteMateria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstudianteMateria{" +
            "id=" + getId() +
            ", idEstudiante=" + getIdEstudiante() +
            ", idMateria=" + getIdMateria() +
            ", gestion=" + getGestion() +
            "}";
    }
}
