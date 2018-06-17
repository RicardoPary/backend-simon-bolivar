package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EstudianteCurso.
 */
@Entity
@Table(name = "estudiante_curso")
public class EstudianteCurso extends AbstractAuditingEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(name = "id_curso")
    private Long idCurso;

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

    public EstudianteCurso idEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
        return this;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public EstudianteCurso idCurso(Long idCurso) {
        this.idCurso = idCurso;
        return this;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
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
        EstudianteCurso estudianteCurso = (EstudianteCurso) o;
        if (estudianteCurso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estudianteCurso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstudianteCurso{" +
            "id=" + getId() +
            ", idEstudiante=" + getIdEstudiante() +
            ", idCurso=" + getIdCurso() +
            "}";
    }
}
