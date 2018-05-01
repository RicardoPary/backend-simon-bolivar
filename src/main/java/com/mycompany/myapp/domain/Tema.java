package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tema.
 */
@Entity
@Table(name = "tema")
public class Tema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Tema titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tema descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materia getMateria() {
        return materia;
    }

    public Tema materia(Materia materia) {
        this.materia = materia;
        return this;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
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
        Tema tema = (Tema) o;
        if (tema.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tema.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tema{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
