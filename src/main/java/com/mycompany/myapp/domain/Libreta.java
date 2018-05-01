package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Libreta.
 */
@Entity
@Table(name = "libreta")
public class Libreta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "observacion")
    private String observacion;

    @OneToMany(mappedBy = "libreta")
    private Set<Nota> notas = new HashSet<>();

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

    public Libreta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public Libreta tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public Libreta observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public Libreta notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public Libreta addNota(Nota nota) {
        this.notas.add(nota);
        nota.setLibreta(this);
        return this;
    }

    public Libreta removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setLibreta(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
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
        Libreta libreta = (Libreta) o;
        if (libreta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), libreta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Libreta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
