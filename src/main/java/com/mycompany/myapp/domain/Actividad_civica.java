package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Actividad_civica.
 */
@Entity
@Table(name = "actividad_civica")
public class Actividad_civica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "lugar")
    private String lugar;

    @ManyToMany
    @JoinTable(name = "actividad_civica_docente",
               joinColumns = @JoinColumn(name="actividad_civicas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="docentes_id", referencedColumnName="id"))
    private Set<Docente> docentes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "actividad_civica_plantel_administrativo",
               joinColumns = @JoinColumn(name="actividad_civicas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="plantel_administrativos_id", referencedColumnName="id"))
    private Set<Plantel_administrativo> plantel_administrativos = new HashSet<>();

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

    public Actividad_civica nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public Actividad_civica tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public Actividad_civica fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public Actividad_civica hora(String hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public Actividad_civica lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public Actividad_civica docentes(Set<Docente> docentes) {
        this.docentes = docentes;
        return this;
    }

    public Actividad_civica addDocente(Docente docente) {
        this.docentes.add(docente);
        docente.getActividad_civicas().add(this);
        return this;
    }

    public Actividad_civica removeDocente(Docente docente) {
        this.docentes.remove(docente);
        docente.getActividad_civicas().remove(this);
        return this;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
    }

    public Set<Plantel_administrativo> getPlantel_administrativos() {
        return plantel_administrativos;
    }

    public Actividad_civica plantel_administrativos(Set<Plantel_administrativo> plantel_administrativos) {
        this.plantel_administrativos = plantel_administrativos;
        return this;
    }

    public Actividad_civica addPlantel_administrativo(Plantel_administrativo plantel_administrativo) {
        this.plantel_administrativos.add(plantel_administrativo);
        plantel_administrativo.getActividad_civicas().add(this);
        return this;
    }

    public Actividad_civica removePlantel_administrativo(Plantel_administrativo plantel_administrativo) {
        this.plantel_administrativos.remove(plantel_administrativo);
        plantel_administrativo.getActividad_civicas().remove(this);
        return this;
    }

    public void setPlantel_administrativos(Set<Plantel_administrativo> plantel_administrativos) {
        this.plantel_administrativos = plantel_administrativos;
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
        Actividad_civica actividad_civica = (Actividad_civica) o;
        if (actividad_civica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividad_civica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Actividad_civica{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", lugar='" + getLugar() + "'" +
            "}";
    }
}
