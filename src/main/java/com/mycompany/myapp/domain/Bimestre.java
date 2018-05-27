package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bimestre.
 */
@Entity
@Table(name = "bimestre")
public class Bimestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(name = "id_docente")
    private Long idDocente;

    @Column(name = "bimestre")
    private String bimestre;

    @Column(name = "id_materia")
    private Long idMateria;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "indicador_cualitativo")
    private String indicadorCualitativo;

    @Column(name = "nota_bimestral_final")
    private Double notaBimestralFinal;

    @Column(name = "promedio_autoevaluacion")
    private Double promedioAutoevaluacion;

    @Column(name = "autoevaluacion_decir")
    private Double autoevaluacionDecir;

    @Column(name = "autoevaluacion_ser")
    private Double autoevaluacionSer;

    @Column(name = "decir_promedio")
    private Double decirPromedio;

    @Column(name = "hacer_promedio")
    private Double hacerPromedio;

    @Column(name = "saber_promedio")
    private Double saberPromedio;

    @Column(name = "ser_promedio")
    private Double serPromedio;

    @Column(name = "notas_ser")
    private String notasSer;

    @Column(name = "notas_saber")
    private String notasSaber;

    @Column(name = "notas_hacer")
    private String notasHacer;

    @Column(name = "notas_decir")
    private String notasDecir;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public Bimestre idEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
        return this;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public Bimestre idDocente(Long idDocente) {
        this.idDocente = idDocente;
        return this;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getBimestre() {
        return bimestre;
    }

    public Bimestre bimestre(String bimestre) {
        this.bimestre = bimestre;
        return this;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public Bimestre idMateria(Long idMateria) {
        this.idMateria = idMateria;
        return this;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getObservacion() {
        return observacion;
    }

    public Bimestre observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIndicadorCualitativo() {
        return indicadorCualitativo;
    }

    public Bimestre indicadorCualitativo(String indicadorCualitativo) {
        this.indicadorCualitativo = indicadorCualitativo;
        return this;
    }

    public void setIndicadorCualitativo(String indicadorCualitativo) {
        this.indicadorCualitativo = indicadorCualitativo;
    }

    public Double getNotaBimestralFinal() {
        return notaBimestralFinal;
    }

    public Bimestre notaBimestralFinal(Double notaBimestralFinal) {
        this.notaBimestralFinal = notaBimestralFinal;
        return this;
    }

    public void setNotaBimestralFinal(Double notaBimestralFinal) {
        this.notaBimestralFinal = notaBimestralFinal;
    }

    public Double getPromedioAutoevaluacion() {
        return promedioAutoevaluacion;
    }

    public Bimestre promedioAutoevaluacion(Double promedioAutoevaluacion) {
        this.promedioAutoevaluacion = promedioAutoevaluacion;
        return this;
    }

    public void setPromedioAutoevaluacion(Double promedioAutoevaluacion) {
        this.promedioAutoevaluacion = promedioAutoevaluacion;
    }

    public Double getAutoevaluacionDecir() {
        return autoevaluacionDecir;
    }

    public Bimestre autoevaluacionDecir(Double autoevaluacionDecir) {
        this.autoevaluacionDecir = autoevaluacionDecir;
        return this;
    }

    public void setAutoevaluacionDecir(Double autoevaluacionDecir) {
        this.autoevaluacionDecir = autoevaluacionDecir;
    }

    public Double getAutoevaluacionSer() {
        return autoevaluacionSer;
    }

    public Bimestre autoevaluacionSer(Double autoevaluacionSer) {
        this.autoevaluacionSer = autoevaluacionSer;
        return this;
    }

    public void setAutoevaluacionSer(Double autoevaluacionSer) {
        this.autoevaluacionSer = autoevaluacionSer;
    }

    public Double getDecirPromedio() {
        return decirPromedio;
    }

    public Bimestre decirPromedio(Double decirPromedio) {
        this.decirPromedio = decirPromedio;
        return this;
    }

    public void setDecirPromedio(Double decirPromedio) {
        this.decirPromedio = decirPromedio;
    }

    public Double getHacerPromedio() {
        return hacerPromedio;
    }

    public Bimestre hacerPromedio(Double hacerPromedio) {
        this.hacerPromedio = hacerPromedio;
        return this;
    }

    public void setHacerPromedio(Double hacerPromedio) {
        this.hacerPromedio = hacerPromedio;
    }

    public Double getSaberPromedio() {
        return saberPromedio;
    }

    public Bimestre saberPromedio(Double saberPromedio) {
        this.saberPromedio = saberPromedio;
        return this;
    }

    public void setSaberPromedio(Double saberPromedio) {
        this.saberPromedio = saberPromedio;
    }

    public Double getSerPromedio() {
        return serPromedio;
    }

    public Bimestre serPromedio(Double serPromedio) {
        this.serPromedio = serPromedio;
        return this;
    }

    public void setSerPromedio(Double serPromedio) {
        this.serPromedio = serPromedio;
    }

    public String getNotasSer() {
        return notasSer;
    }

    public Bimestre notasSer(String notasSer) {
        this.notasSer = notasSer;
        return this;
    }

    public void setNotasSer(String notasSer) {
        this.notasSer = notasSer;
    }

    public String getNotasSaber() {
        return notasSaber;
    }

    public Bimestre notasSaber(String notasSaber) {
        this.notasSaber = notasSaber;
        return this;
    }

    public void setNotasSaber(String notasSaber) {
        this.notasSaber = notasSaber;
    }

    public String getNotasHacer() {
        return notasHacer;
    }

    public Bimestre notasHacer(String notasHacer) {
        this.notasHacer = notasHacer;
        return this;
    }

    public void setNotasHacer(String notasHacer) {
        this.notasHacer = notasHacer;
    }

    public String getNotasDecir() {
        return notasDecir;
    }

    public Bimestre notasDecir(String notasDecir) {
        this.notasDecir = notasDecir;
        return this;
    }

    public void setNotasDecir(String notasDecir) {
        this.notasDecir = notasDecir;
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
        Bimestre bimestre = (Bimestre) o;
        if (bimestre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bimestre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bimestre{" +
            "id=" + getId() +
            ", idEstudiante=" + getIdEstudiante() +
            ", idDocente=" + getIdDocente() +
            ", bimestre='" + getBimestre() + "'" +
            ", idMateria=" + getIdMateria() +
            ", observacion='" + getObservacion() + "'" +
            ", indicadorCualitativo='" + getIndicadorCualitativo() + "'" +
            ", notaBimestralFinal=" + getNotaBimestralFinal() +
            ", promedioAutoevaluacion=" + getPromedioAutoevaluacion() +
            ", autoevaluacionDecir=" + getAutoevaluacionDecir() +
            ", autoevaluacionSer=" + getAutoevaluacionSer() +
            ", decirPromedio=" + getDecirPromedio() +
            ", hacerPromedio=" + getHacerPromedio() +
            ", saberPromedio=" + getSaberPromedio() +
            ", serPromedio=" + getSerPromedio() +
            ", notasSer='" + getNotasSer() + "'" +
            ", notasSaber='" + getNotasSaber() + "'" +
            ", notasHacer='" + getNotasHacer() + "'" +
            ", notasDecir='" + getNotasDecir() + "'" +
            "}";
    }
}
