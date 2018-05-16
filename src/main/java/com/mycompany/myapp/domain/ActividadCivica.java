package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ActividadCivica.
 */
@Entity
@Table(name = "actividad_civica")
public class ActividadCivica implements Serializable {

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
    private Set<PlantelAdministrativo> plantelAdministrativos = new HashSet<>();

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

    public ActividadCivica nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public ActividadCivica tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public ActividadCivica fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public ActividadCivica hora(String hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public ActividadCivica lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public ActividadCivica docentes(Set<Docente> docentes) {
        this.docentes = docentes;
        return this;
    }

    public ActividadCivica addDocente(Docente docente) {
        this.docentes.add(docente);
        docente.getActividadCivicas().add(this);
        return this;
    }

    public ActividadCivica removeDocente(Docente docente) {
        this.docentes.remove(docente);
        docente.getActividadCivicas().remove(this);
        return this;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
    }

    public Set<PlantelAdministrativo> getPlantelAdministrativos() {
        return plantelAdministrativos;
    }

    public ActividadCivica plantelAdministrativos(Set<PlantelAdministrativo> plantelAdministrativos) {
        this.plantelAdministrativos = plantelAdministrativos;
        return this;
    }

    public ActividadCivica addPlantelAdministrativo(PlantelAdministrativo plantelAdministrativo) {
        this.plantelAdministrativos.add(plantelAdministrativo);
        plantelAdministrativo.getActividadCivicas().add(this);
        return this;
    }

    public ActividadCivica removePlantelAdministrativo(PlantelAdministrativo plantelAdministrativo) {
        this.plantelAdministrativos.remove(plantelAdministrativo);
        plantelAdministrativo.getActividadCivicas().remove(this);
        return this;
    }

    public void setPlantelAdministrativos(Set<PlantelAdministrativo> plantelAdministrativos) {
        this.plantelAdministrativos = plantelAdministrativos;
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
        ActividadCivica actividadCivica = (ActividadCivica) o;
        if (actividadCivica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actividadCivica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActividadCivica{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", lugar='" + getLugar() + "'" +
            "}";
    }
}
