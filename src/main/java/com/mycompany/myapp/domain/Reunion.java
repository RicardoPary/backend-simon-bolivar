package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reunion.
 */
@Entity
@Table(name = "reunion")
public class Reunion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "lugar")
    private String lugar;

    @ManyToMany
    @JoinTable(name = "reunion_persona",
               joinColumns = @JoinColumn(name="reunions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="personas_id", referencedColumnName="id"))
    private Set<Persona> personas = new HashSet<>();

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

    public Reunion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public Reunion fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public Reunion lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Set<Persona> getPersonas() {
        return personas;
    }

    public Reunion personas(Set<Persona> personas) {
        this.personas = personas;
        return this;
    }

    public Reunion addPersona(Persona persona) {
        this.personas.add(persona);
        persona.getReunions().add(this);
        return this;
    }

    public Reunion removePersona(Persona persona) {
        this.personas.remove(persona);
        persona.getReunions().remove(this);
        return this;
    }

    public void setPersonas(Set<Persona> personas) {
        this.personas = personas;
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
        Reunion reunion = (Reunion) o;
        if (reunion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reunion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reunion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", lugar='" + getLugar() + "'" +
            "}";
    }
}
