package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Horario.
 */
@Entity
@Table(name = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hora_inicio")
    private String hora_inicio;

    @Column(name = "hora_fin")
    private String hora_fin;

    @Column(name = "dia")
    private String dia;

    @ManyToOne
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public Horario hora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
        return this;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public Horario hora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
        return this;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getDia() {
        return dia;
    }

    public Horario dia(String dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Materia getMateria() {
        return materia;
    }

    public Horario materia(Materia materia) {
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
        Horario horario = (Horario) o;
        if (horario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + getId() +
            ", hora_inicio='" + getHora_inicio() + "'" +
            ", hora_fin='" + getHora_fin() + "'" +
            ", dia='" + getDia() + "'" +
            "}";
    }
}
