package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Estudiante> estudiantes = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Docente> docentes = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Familiar> familiars = new HashSet<>();

    @ManyToMany(mappedBy = "personas")
    @JsonIgnore
    private Set<Reunion> reunions = new HashSet<>();

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

    public Persona nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Persona estudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        return this;
    }

    public Persona addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.setPersona(this);
        return this;
    }

    public Persona removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.setPersona(null);
        return this;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public Persona docentes(Set<Docente> docentes) {
        this.docentes = docentes;
        return this;
    }

    public Persona addDocente(Docente docente) {
        this.docentes.add(docente);
        docente.setPersona(this);
        return this;
    }

    public Persona removeDocente(Docente docente) {
        this.docentes.remove(docente);
        docente.setPersona(null);
        return this;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
    }

    public Set<Familiar> getFamiliars() {
        return familiars;
    }

    public Persona familiars(Set<Familiar> familiars) {
        this.familiars = familiars;
        return this;
    }

    public Persona addFamiliar(Familiar familiar) {
        this.familiars.add(familiar);
        familiar.setPersona(this);
        return this;
    }

    public Persona removeFamiliar(Familiar familiar) {
        this.familiars.remove(familiar);
        familiar.setPersona(null);
        return this;
    }

    public void setFamiliars(Set<Familiar> familiars) {
        this.familiars = familiars;
    }

    public Set<Reunion> getReunions() {
        return reunions;
    }

    public Persona reunions(Set<Reunion> reunions) {
        this.reunions = reunions;
        return this;
    }

    public Persona addReunion(Reunion reunion) {
        this.reunions.add(reunion);
        reunion.getPersonas().add(this);
        return this;
    }

    public Persona removeReunion(Reunion reunion) {
        this.reunions.remove(reunion);
        reunion.getPersonas().remove(this);
        return this;
    }

    public void setReunions(Set<Reunion> reunions) {
        this.reunions = reunions;
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
        Persona persona = (Persona) o;
        if (persona.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), persona.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
