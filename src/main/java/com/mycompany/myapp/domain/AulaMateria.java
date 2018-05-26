package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AulaMateria.
 */
@Entity
@Table(name = "aula_materia")
public class AulaMateria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_aula")
    private Long idAula;

    @Column(name = "id_materia")
    private Long idMateria;

    @Column(name = "horario")
    private String horario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAula() {
        return idAula;
    }

    public AulaMateria idAula(Long idAula) {
        this.idAula = idAula;
        return this;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public AulaMateria idMateria(Long idMateria) {
        this.idMateria = idMateria;
        return this;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getHorario() {
        return horario;
    }

    public AulaMateria horario(String horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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
        AulaMateria aulaMateria = (AulaMateria) o;
        if (aulaMateria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aulaMateria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AulaMateria{" +
            "id=" + getId() +
            ", idAula=" + getIdAula() +
            ", idMateria=" + getIdMateria() +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
