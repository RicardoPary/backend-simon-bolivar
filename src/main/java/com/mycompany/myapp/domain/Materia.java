package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Materia.
 */
@Entity
@Table(name = "materia")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private Set<Aula> aulas = new HashSet<>();

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private Set<Docente> docentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Materia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public Materia sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Materia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public Materia aulas(Set<Aula> aulas) {
        this.aulas = aulas;
        return this;
    }

    public Materia addAula(Aula aula) {
        this.aulas.add(aula);
        aula.getMaterias().add(this);
        return this;
    }

    public Materia removeAula(Aula aula) {
        this.aulas.remove(aula);
        aula.getMaterias().remove(this);
        return this;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public Materia docentes(Set<Docente> docentes) {
        this.docentes = docentes;
        return this;
    }

    public Materia addDocente(Docente docente) {
        this.docentes.add(docente);
        docente.getMaterias().add(this);
        return this;
    }

    public Materia removeDocente(Docente docente) {
        this.docentes.remove(docente);
        docente.getMaterias().remove(this);
        return this;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
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
        Materia materia = (Materia) o;
        if (materia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Materia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
