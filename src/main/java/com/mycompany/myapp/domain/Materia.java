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

    @OneToMany(mappedBy = "materia")
    @JsonIgnore
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "materia")
    @JsonIgnore
    private Set<Nota> notas = new HashSet<>();

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private Set<Aula> aulas = new HashSet<>();

    @OneToMany(mappedBy = "materia")
    @JsonIgnore
    private Set<Tema> temas = new HashSet<>();

    @ManyToMany(mappedBy = "materias")
    @JsonIgnore
    private Set<Estudiante> estudiantes = new HashSet<>();

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

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Materia horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Materia addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setMateria(this);
        return this;
    }

    public Materia removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setMateria(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public Materia notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public Materia addNota(Nota nota) {
        this.notas.add(nota);
        nota.setMateria(this);
        return this;
    }

    public Materia removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setMateria(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
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

    public Set<Tema> getTemas() {
        return temas;
    }

    public Materia temas(Set<Tema> temas) {
        this.temas = temas;
        return this;
    }

    public Materia addTema(Tema tema) {
        this.temas.add(tema);
        tema.setMateria(this);
        return this;
    }

    public Materia removeTema(Tema tema) {
        this.temas.remove(tema);
        tema.setMateria(null);
        return this;
    }

    public void setTemas(Set<Tema> temas) {
        this.temas = temas;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Materia estudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        return this;
    }

    public Materia addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.getMaterias().add(this);
        return this;
    }

    public Materia removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.getMaterias().remove(this);
        return this;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
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
            "}";
    }
}
