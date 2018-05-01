package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Aula.
 */
@Entity
@Table(name = "aula")
public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @JoinTable(name = "aula_materia",
               joinColumns = @JoinColumn(name="aulas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="materias_id", referencedColumnName="id"))
    private Set<Materia> materias = new HashSet<>();

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

    public Aula nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public Aula sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public Aula tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Aula descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Aula materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Aula addMateria(Materia materia) {
        this.materias.add(materia);
        materia.getAulas().add(this);
        return this;
    }

    public Aula removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.getAulas().remove(this);
        return this;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
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
        Aula aula = (Aula) o;
        if (aula.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aula.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aula{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
