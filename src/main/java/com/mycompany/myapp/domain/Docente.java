package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany(mappedBy = "docentes")
    @JsonIgnore
    private Set<Materia> materias = new HashSet<>();

    @ManyToOne
    private Persona persona;

    @ManyToMany(mappedBy = "docentes")
    @JsonIgnore
    private Set<Actividad_civica> actividad_civicas = new HashSet<>();

    @OneToMany(mappedBy = "docente")
    @JsonIgnore
    private Set<Contrato> contratoes = new HashSet<>();

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

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Docente materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Docente addMateria(Materia materia) {
        this.materias.add(materia);
        materia.getDocentes().add(this);
        return this;
    }

    public Docente removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.getDocentes().remove(this);
        return this;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }

    public Persona getPersona() {
        return persona;
    }

    public Docente persona(Persona persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Set<Actividad_civica> getActividad_civicas() {
        return actividad_civicas;
    }

    public Docente actividad_civicas(Set<Actividad_civica> actividad_civicas) {
        this.actividad_civicas = actividad_civicas;
        return this;
    }

    public Docente addActividad_civica(Actividad_civica actividad_civica) {
        this.actividad_civicas.add(actividad_civica);
        actividad_civica.getDocentes().add(this);
        return this;
    }

    public Docente removeActividad_civica(Actividad_civica actividad_civica) {
        this.actividad_civicas.remove(actividad_civica);
        actividad_civica.getDocentes().remove(this);
        return this;
    }

    public void setActividad_civicas(Set<Actividad_civica> actividad_civicas) {
        this.actividad_civicas = actividad_civicas;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Docente contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Docente addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setDocente(this);
        return this;
    }

    public Docente removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setDocente(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
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
            "}";
    }
}
