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

    @Column(name = "ci")
    private Long ci;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "paterno")
    private String paterno;

    @Column(name = "materno")
    private String materno;

    @Column(name = "genero")
    private String genero;

    @Column(name = "fecha_nacimiento")
    private String fecha_nacimiento;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private Long telefono;

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Estudiante> estudiantes = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Docente> docentes = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Familiar> familiars = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @JsonIgnore
    private Set<Plantel_administrativo> plantel_administrativos = new HashSet<>();

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

    public Long getCi() {
        return ci;
    }

    public Persona ci(Long ci) {
        this.ci = ci;
        return this;
    }

    public void setCi(Long ci) {
        this.ci = ci;
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

    public String getPaterno() {
        return paterno;
    }

    public Persona paterno(String paterno) {
        this.paterno = paterno;
        return this;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public Persona materno(String materno) {
        this.materno = materno;
        return this;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getGenero() {
        return genero;
    }

    public Persona genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public Persona fecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
        return this;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public Persona nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public Persona direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public Persona telefono(Long telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
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

    public Set<Plantel_administrativo> getPlantel_administrativos() {
        return plantel_administrativos;
    }

    public Persona plantel_administrativos(Set<Plantel_administrativo> plantel_administrativos) {
        this.plantel_administrativos = plantel_administrativos;
        return this;
    }

    public Persona addPlantel_administrativo(Plantel_administrativo plantel_administrativo) {
        this.plantel_administrativos.add(plantel_administrativo);
        plantel_administrativo.setPersona(this);
        return this;
    }

    public Persona removePlantel_administrativo(Plantel_administrativo plantel_administrativo) {
        this.plantel_administrativos.remove(plantel_administrativo);
        plantel_administrativo.setPersona(null);
        return this;
    }

    public void setPlantel_administrativos(Set<Plantel_administrativo> plantel_administrativos) {
        this.plantel_administrativos = plantel_administrativos;
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
            ", ci=" + getCi() +
            ", nombre='" + getNombre() + "'" +
            ", paterno='" + getPaterno() + "'" +
            ", materno='" + getMaterno() + "'" +
            ", genero='" + getGenero() + "'" +
            ", fecha_nacimiento='" + getFecha_nacimiento() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono=" + getTelefono() +
            "}";
    }
}
