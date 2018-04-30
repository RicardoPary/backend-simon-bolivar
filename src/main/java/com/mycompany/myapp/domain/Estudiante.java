package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "matricula")
    private String matricula;

    @ManyToMany
    @JoinTable(name = "estudiante_materia",
               joinColumns = @JoinColumn(name="estudiantes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="materias_id", referencedColumnName="id"))
    private Set<Materia> materias = new HashSet<>();

    @ManyToOne
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public Estudiante matricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Estudiante materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Estudiante addMateria(Materia materia) {
        this.materias.add(materia);
        materia.getEstudiantes().add(this);
        return this;
    }

    public Estudiante removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.getEstudiantes().remove(this);
        return this;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }

    public Persona getPersona() {
        return persona;
    }

    public Estudiante persona(Persona persona) {
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
        Estudiante estudiante = (Estudiante) o;
        if (estudiante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estudiante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estudiante{" +
            "id=" + getId() +
            ", matricula='" + getMatricula() + "'" +
            "}";
    }
}
