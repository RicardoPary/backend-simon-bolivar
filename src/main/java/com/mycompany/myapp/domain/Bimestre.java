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
    private Integer bimestre;

    @Column(name = "paralelo")
    private String paralelo;

    @Column(name = "gestion")
    private Integer gestion;

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

    @Column(name = "nota_ser_1")
    private Integer notaSer1;

    @Column(name = "nota_ser_2")
    private Integer notaSer2;

    @Column(name = "nota_ser_3")
    private Integer notaSer3;

    @Column(name = "nota_ser_4")
    private Integer notaSer4;

    @Column(name = "nota_ser_5")
    private Integer notaSer5;

    @Column(name = "nota_ser_6")
    private Integer notaSer6;

    @Column(name = "nota_saber_1")
    private Integer notaSaber1;

    @Column(name = "nota_saber_2")
    private Integer notaSaber2;

    @Column(name = "nota_saber_3")
    private Integer notaSaber3;

    @Column(name = "nota_saber_4")
    private Integer notaSaber4;

    @Column(name = "nota_saber_5")
    private Integer notaSaber5;

    @Column(name = "nota_saber_6")
    private Integer notaSaber6;

    @Column(name = "nota_hacer_1")
    private Integer notaHacer1;

    @Column(name = "nota_hacer_2")
    private Integer notaHacer2;

    @Column(name = "nota_hacer_3")
    private Integer notaHacer3;

    @Column(name = "nota_hacer_4")
    private Integer notaHacer4;

    @Column(name = "nota_hacer_5")
    private Integer notaHacer5;

    @Column(name = "nota_hacer_6")
    private Integer notaHacer6;

    @Column(name = "nota_decir_1")
    private Integer notaDecir1;

    @Column(name = "nota_decir_2")
    private Integer notaDecir2;

    @Column(name = "nota_decir_3")
    private Integer notaDecir3;

    @Column(name = "nota_decir_4")
    private Integer notaDecir4;

    @Column(name = "nota_decir_5")
    private Integer notaDecir5;

    @Column(name = "nota_decir_6")
    private Integer notaDecir6;

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

    public Integer getBimestre() {
        return bimestre;
    }

    public Bimestre bimestre(Integer bimestre) {
        this.bimestre = bimestre;
        return this;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public String getParalelo() {
        return paralelo;
    }

    public Bimestre paralelo(String paralelo) {
        this.paralelo = paralelo;
        return this;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public Bimestre gestion(Integer gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
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

    public Integer getNotaSer1() {
        return notaSer1;
    }

    public Bimestre notaSer1(Integer notaSer1) {
        this.notaSer1 = notaSer1;
        return this;
    }

    public void setNotaSer1(Integer notaSer1) {
        this.notaSer1 = notaSer1;
    }

    public Integer getNotaSer2() {
        return notaSer2;
    }

    public Bimestre notaSer2(Integer notaSer2) {
        this.notaSer2 = notaSer2;
        return this;
    }

    public void setNotaSer2(Integer notaSer2) {
        this.notaSer2 = notaSer2;
    }

    public Integer getNotaSer3() {
        return notaSer3;
    }

    public Bimestre notaSer3(Integer notaSer3) {
        this.notaSer3 = notaSer3;
        return this;
    }

    public void setNotaSer3(Integer notaSer3) {
        this.notaSer3 = notaSer3;
    }

    public Integer getNotaSer4() {
        return notaSer4;
    }

    public Bimestre notaSer4(Integer notaSer4) {
        this.notaSer4 = notaSer4;
        return this;
    }

    public void setNotaSer4(Integer notaSer4) {
        this.notaSer4 = notaSer4;
    }

    public Integer getNotaSer5() {
        return notaSer5;
    }

    public Bimestre notaSer5(Integer notaSer5) {
        this.notaSer5 = notaSer5;
        return this;
    }

    public void setNotaSer5(Integer notaSer5) {
        this.notaSer5 = notaSer5;
    }

    public Integer getNotaSer6() {
        return notaSer6;
    }

    public Bimestre notaSer6(Integer notaSer6) {
        this.notaSer6 = notaSer6;
        return this;
    }

    public void setNotaSer6(Integer notaSer6) {
        this.notaSer6 = notaSer6;
    }

    public Integer getNotaSaber1() {
        return notaSaber1;
    }

    public Bimestre notaSaber1(Integer notaSaber1) {
        this.notaSaber1 = notaSaber1;
        return this;
    }

    public void setNotaSaber1(Integer notaSaber1) {
        this.notaSaber1 = notaSaber1;
    }

    public Integer getNotaSaber2() {
        return notaSaber2;
    }

    public Bimestre notaSaber2(Integer notaSaber2) {
        this.notaSaber2 = notaSaber2;
        return this;
    }

    public void setNotaSaber2(Integer notaSaber2) {
        this.notaSaber2 = notaSaber2;
    }

    public Integer getNotaSaber3() {
        return notaSaber3;
    }

    public Bimestre notaSaber3(Integer notaSaber3) {
        this.notaSaber3 = notaSaber3;
        return this;
    }

    public void setNotaSaber3(Integer notaSaber3) {
        this.notaSaber3 = notaSaber3;
    }

    public Integer getNotaSaber4() {
        return notaSaber4;
    }

    public Bimestre notaSaber4(Integer notaSaber4) {
        this.notaSaber4 = notaSaber4;
        return this;
    }

    public void setNotaSaber4(Integer notaSaber4) {
        this.notaSaber4 = notaSaber4;
    }

    public Integer getNotaSaber5() {
        return notaSaber5;
    }

    public Bimestre notaSaber5(Integer notaSaber5) {
        this.notaSaber5 = notaSaber5;
        return this;
    }

    public void setNotaSaber5(Integer notaSaber5) {
        this.notaSaber5 = notaSaber5;
    }

    public Integer getNotaSaber6() {
        return notaSaber6;
    }

    public Bimestre notaSaber6(Integer notaSaber6) {
        this.notaSaber6 = notaSaber6;
        return this;
    }

    public void setNotaSaber6(Integer notaSaber6) {
        this.notaSaber6 = notaSaber6;
    }

    public Integer getNotaHacer1() {
        return notaHacer1;
    }

    public Bimestre notaHacer1(Integer notaHacer1) {
        this.notaHacer1 = notaHacer1;
        return this;
    }

    public void setNotaHacer1(Integer notaHacer1) {
        this.notaHacer1 = notaHacer1;
    }

    public Integer getNotaHacer2() {
        return notaHacer2;
    }

    public Bimestre notaHacer2(Integer notaHacer2) {
        this.notaHacer2 = notaHacer2;
        return this;
    }

    public void setNotaHacer2(Integer notaHacer2) {
        this.notaHacer2 = notaHacer2;
    }

    public Integer getNotaHacer3() {
        return notaHacer3;
    }

    public Bimestre notaHacer3(Integer notaHacer3) {
        this.notaHacer3 = notaHacer3;
        return this;
    }

    public void setNotaHacer3(Integer notaHacer3) {
        this.notaHacer3 = notaHacer3;
    }

    public Integer getNotaHacer4() {
        return notaHacer4;
    }

    public Bimestre notaHacer4(Integer notaHacer4) {
        this.notaHacer4 = notaHacer4;
        return this;
    }

    public void setNotaHacer4(Integer notaHacer4) {
        this.notaHacer4 = notaHacer4;
    }

    public Integer getNotaHacer5() {
        return notaHacer5;
    }

    public Bimestre notaHacer5(Integer notaHacer5) {
        this.notaHacer5 = notaHacer5;
        return this;
    }

    public void setNotaHacer5(Integer notaHacer5) {
        this.notaHacer5 = notaHacer5;
    }

    public Integer getNotaHacer6() {
        return notaHacer6;
    }

    public Bimestre notaHacer6(Integer notaHacer6) {
        this.notaHacer6 = notaHacer6;
        return this;
    }

    public void setNotaHacer6(Integer notaHacer6) {
        this.notaHacer6 = notaHacer6;
    }

    public Integer getNotaDecir1() {
        return notaDecir1;
    }

    public Bimestre notaDecir1(Integer notaDecir1) {
        this.notaDecir1 = notaDecir1;
        return this;
    }

    public void setNotaDecir1(Integer notaDecir1) {
        this.notaDecir1 = notaDecir1;
    }

    public Integer getNotaDecir2() {
        return notaDecir2;
    }

    public Bimestre notaDecir2(Integer notaDecir2) {
        this.notaDecir2 = notaDecir2;
        return this;
    }

    public void setNotaDecir2(Integer notaDecir2) {
        this.notaDecir2 = notaDecir2;
    }

    public Integer getNotaDecir3() {
        return notaDecir3;
    }

    public Bimestre notaDecir3(Integer notaDecir3) {
        this.notaDecir3 = notaDecir3;
        return this;
    }

    public void setNotaDecir3(Integer notaDecir3) {
        this.notaDecir3 = notaDecir3;
    }

    public Integer getNotaDecir4() {
        return notaDecir4;
    }

    public Bimestre notaDecir4(Integer notaDecir4) {
        this.notaDecir4 = notaDecir4;
        return this;
    }

    public void setNotaDecir4(Integer notaDecir4) {
        this.notaDecir4 = notaDecir4;
    }

    public Integer getNotaDecir5() {
        return notaDecir5;
    }

    public Bimestre notaDecir5(Integer notaDecir5) {
        this.notaDecir5 = notaDecir5;
        return this;
    }

    public void setNotaDecir5(Integer notaDecir5) {
        this.notaDecir5 = notaDecir5;
    }

    public Integer getNotaDecir6() {
        return notaDecir6;
    }

    public Bimestre notaDecir6(Integer notaDecir6) {
        this.notaDecir6 = notaDecir6;
        return this;
    }

    public void setNotaDecir6(Integer notaDecir6) {
        this.notaDecir6 = notaDecir6;
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
            ", bimestre=" + getBimestre() +
            ", paralelo='" + getParalelo() + "'" +
            ", gestion=" + getGestion() +
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
            ", notaSer1=" + getNotaSer1() +
            ", notaSer2=" + getNotaSer2() +
            ", notaSer3=" + getNotaSer3() +
            ", notaSer4=" + getNotaSer4() +
            ", notaSer5=" + getNotaSer5() +
            ", notaSer6=" + getNotaSer6() +
            ", notaSaber1=" + getNotaSaber1() +
            ", notaSaber2=" + getNotaSaber2() +
            ", notaSaber3=" + getNotaSaber3() +
            ", notaSaber4=" + getNotaSaber4() +
            ", notaSaber5=" + getNotaSaber5() +
            ", notaSaber6=" + getNotaSaber6() +
            ", notaHacer1=" + getNotaHacer1() +
            ", notaHacer2=" + getNotaHacer2() +
            ", notaHacer3=" + getNotaHacer3() +
            ", notaHacer4=" + getNotaHacer4() +
            ", notaHacer5=" + getNotaHacer5() +
            ", notaHacer6=" + getNotaHacer6() +
            ", notaDecir1=" + getNotaDecir1() +
            ", notaDecir2=" + getNotaDecir2() +
            ", notaDecir3=" + getNotaDecir3() +
            ", notaDecir4=" + getNotaDecir4() +
            ", notaDecir5=" + getNotaDecir5() +
            ", notaDecir6=" + getNotaDecir6() +
            "}";
    }
}
