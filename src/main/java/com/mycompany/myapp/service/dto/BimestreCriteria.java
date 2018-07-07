package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Bimestre entity. This class is used in BimestreResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bimestres?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BimestreCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter idCurso;

    private LongFilter idDocente;

    private IntegerFilter bimestre;

    private StringFilter paralelo;

    private IntegerFilter gestion;

    private LongFilter idMateria;

    private StringFilter observacion;

    private StringFilter indicadorCualitativo;

    private DoubleFilter notaBimestralFinal;

    private DoubleFilter promedioAutoevaluacion;

    private DoubleFilter autoevaluacionDecir;

    private DoubleFilter autoevaluacionSer;

    private DoubleFilter decirPromedio;

    private DoubleFilter hacerPromedio;

    private DoubleFilter saberPromedio;

    private DoubleFilter serPromedio;

    private IntegerFilter notaSer1;

    private IntegerFilter notaSer2;

    private IntegerFilter notaSer3;

    private IntegerFilter notaSer4;

    private IntegerFilter notaSer5;

    private IntegerFilter notaSer6;

    private IntegerFilter notaSaber1;

    private IntegerFilter notaSaber2;

    private IntegerFilter notaSaber3;

    private IntegerFilter notaSaber4;

    private IntegerFilter notaSaber5;

    private IntegerFilter notaSaber6;

    private IntegerFilter notaHacer1;

    private IntegerFilter notaHacer2;

    private IntegerFilter notaHacer3;

    private IntegerFilter notaHacer4;

    private IntegerFilter notaHacer5;

    private IntegerFilter notaHacer6;

    private IntegerFilter notaDecir1;

    private IntegerFilter notaDecir2;

    private IntegerFilter notaDecir3;

    private IntegerFilter notaDecir4;

    private IntegerFilter notaDecir5;

    private IntegerFilter notaDecir6;

    private LongFilter estudianteId;

    public BimestreCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(LongFilter idCurso) {
        this.idCurso = idCurso;
    }

    public LongFilter getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(LongFilter idDocente) {
        this.idDocente = idDocente;
    }

    public IntegerFilter getBimestre() {
        return bimestre;
    }

    public void setBimestre(IntegerFilter bimestre) {
        this.bimestre = bimestre;
    }

    public StringFilter getParalelo() {
        return paralelo;
    }

    public void setParalelo(StringFilter paralelo) {
        this.paralelo = paralelo;
    }

    public IntegerFilter getGestion() {
        return gestion;
    }

    public void setGestion(IntegerFilter gestion) {
        this.gestion = gestion;
    }

    public LongFilter getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(LongFilter idMateria) {
        this.idMateria = idMateria;
    }

    public StringFilter getObservacion() {
        return observacion;
    }

    public void setObservacion(StringFilter observacion) {
        this.observacion = observacion;
    }

    public StringFilter getIndicadorCualitativo() {
        return indicadorCualitativo;
    }

    public void setIndicadorCualitativo(StringFilter indicadorCualitativo) {
        this.indicadorCualitativo = indicadorCualitativo;
    }

    public DoubleFilter getNotaBimestralFinal() {
        return notaBimestralFinal;
    }

    public void setNotaBimestralFinal(DoubleFilter notaBimestralFinal) {
        this.notaBimestralFinal = notaBimestralFinal;
    }

    public DoubleFilter getPromedioAutoevaluacion() {
        return promedioAutoevaluacion;
    }

    public void setPromedioAutoevaluacion(DoubleFilter promedioAutoevaluacion) {
        this.promedioAutoevaluacion = promedioAutoevaluacion;
    }

    public DoubleFilter getAutoevaluacionDecir() {
        return autoevaluacionDecir;
    }

    public void setAutoevaluacionDecir(DoubleFilter autoevaluacionDecir) {
        this.autoevaluacionDecir = autoevaluacionDecir;
    }

    public DoubleFilter getAutoevaluacionSer() {
        return autoevaluacionSer;
    }

    public void setAutoevaluacionSer(DoubleFilter autoevaluacionSer) {
        this.autoevaluacionSer = autoevaluacionSer;
    }

    public DoubleFilter getDecirPromedio() {
        return decirPromedio;
    }

    public void setDecirPromedio(DoubleFilter decirPromedio) {
        this.decirPromedio = decirPromedio;
    }

    public DoubleFilter getHacerPromedio() {
        return hacerPromedio;
    }

    public void setHacerPromedio(DoubleFilter hacerPromedio) {
        this.hacerPromedio = hacerPromedio;
    }

    public DoubleFilter getSaberPromedio() {
        return saberPromedio;
    }

    public void setSaberPromedio(DoubleFilter saberPromedio) {
        this.saberPromedio = saberPromedio;
    }

    public DoubleFilter getSerPromedio() {
        return serPromedio;
    }

    public void setSerPromedio(DoubleFilter serPromedio) {
        this.serPromedio = serPromedio;
    }

    public IntegerFilter getNotaSer1() {
        return notaSer1;
    }

    public void setNotaSer1(IntegerFilter notaSer1) {
        this.notaSer1 = notaSer1;
    }

    public IntegerFilter getNotaSer2() {
        return notaSer2;
    }

    public void setNotaSer2(IntegerFilter notaSer2) {
        this.notaSer2 = notaSer2;
    }

    public IntegerFilter getNotaSer3() {
        return notaSer3;
    }

    public void setNotaSer3(IntegerFilter notaSer3) {
        this.notaSer3 = notaSer3;
    }

    public IntegerFilter getNotaSer4() {
        return notaSer4;
    }

    public void setNotaSer4(IntegerFilter notaSer4) {
        this.notaSer4 = notaSer4;
    }

    public IntegerFilter getNotaSer5() {
        return notaSer5;
    }

    public void setNotaSer5(IntegerFilter notaSer5) {
        this.notaSer5 = notaSer5;
    }

    public IntegerFilter getNotaSer6() {
        return notaSer6;
    }

    public void setNotaSer6(IntegerFilter notaSer6) {
        this.notaSer6 = notaSer6;
    }

    public IntegerFilter getNotaSaber1() {
        return notaSaber1;
    }

    public void setNotaSaber1(IntegerFilter notaSaber1) {
        this.notaSaber1 = notaSaber1;
    }

    public IntegerFilter getNotaSaber2() {
        return notaSaber2;
    }

    public void setNotaSaber2(IntegerFilter notaSaber2) {
        this.notaSaber2 = notaSaber2;
    }

    public IntegerFilter getNotaSaber3() {
        return notaSaber3;
    }

    public void setNotaSaber3(IntegerFilter notaSaber3) {
        this.notaSaber3 = notaSaber3;
    }

    public IntegerFilter getNotaSaber4() {
        return notaSaber4;
    }

    public void setNotaSaber4(IntegerFilter notaSaber4) {
        this.notaSaber4 = notaSaber4;
    }

    public IntegerFilter getNotaSaber5() {
        return notaSaber5;
    }

    public void setNotaSaber5(IntegerFilter notaSaber5) {
        this.notaSaber5 = notaSaber5;
    }

    public IntegerFilter getNotaSaber6() {
        return notaSaber6;
    }

    public void setNotaSaber6(IntegerFilter notaSaber6) {
        this.notaSaber6 = notaSaber6;
    }

    public IntegerFilter getNotaHacer1() {
        return notaHacer1;
    }

    public void setNotaHacer1(IntegerFilter notaHacer1) {
        this.notaHacer1 = notaHacer1;
    }

    public IntegerFilter getNotaHacer2() {
        return notaHacer2;
    }

    public void setNotaHacer2(IntegerFilter notaHacer2) {
        this.notaHacer2 = notaHacer2;
    }

    public IntegerFilter getNotaHacer3() {
        return notaHacer3;
    }

    public void setNotaHacer3(IntegerFilter notaHacer3) {
        this.notaHacer3 = notaHacer3;
    }

    public IntegerFilter getNotaHacer4() {
        return notaHacer4;
    }

    public void setNotaHacer4(IntegerFilter notaHacer4) {
        this.notaHacer4 = notaHacer4;
    }

    public IntegerFilter getNotaHacer5() {
        return notaHacer5;
    }

    public void setNotaHacer5(IntegerFilter notaHacer5) {
        this.notaHacer5 = notaHacer5;
    }

    public IntegerFilter getNotaHacer6() {
        return notaHacer6;
    }

    public void setNotaHacer6(IntegerFilter notaHacer6) {
        this.notaHacer6 = notaHacer6;
    }

    public IntegerFilter getNotaDecir1() {
        return notaDecir1;
    }

    public void setNotaDecir1(IntegerFilter notaDecir1) {
        this.notaDecir1 = notaDecir1;
    }

    public IntegerFilter getNotaDecir2() {
        return notaDecir2;
    }

    public void setNotaDecir2(IntegerFilter notaDecir2) {
        this.notaDecir2 = notaDecir2;
    }

    public IntegerFilter getNotaDecir3() {
        return notaDecir3;
    }

    public void setNotaDecir3(IntegerFilter notaDecir3) {
        this.notaDecir3 = notaDecir3;
    }

    public IntegerFilter getNotaDecir4() {
        return notaDecir4;
    }

    public void setNotaDecir4(IntegerFilter notaDecir4) {
        this.notaDecir4 = notaDecir4;
    }

    public IntegerFilter getNotaDecir5() {
        return notaDecir5;
    }

    public void setNotaDecir5(IntegerFilter notaDecir5) {
        this.notaDecir5 = notaDecir5;
    }

    public IntegerFilter getNotaDecir6() {
        return notaDecir6;
    }

    public void setNotaDecir6(IntegerFilter notaDecir6) {
        this.notaDecir6 = notaDecir6;
    }

    public LongFilter getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(LongFilter estudianteId) {
        this.estudianteId = estudianteId;
    }

    @Override
    public String toString() {
        return "BimestreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idCurso != null ? "idCurso=" + idCurso + ", " : "") +
                (idDocente != null ? "idDocente=" + idDocente + ", " : "") +
                (bimestre != null ? "bimestre=" + bimestre + ", " : "") +
                (paralelo != null ? "paralelo=" + paralelo + ", " : "") +
                (gestion != null ? "gestion=" + gestion + ", " : "") +
                (idMateria != null ? "idMateria=" + idMateria + ", " : "") +
                (observacion != null ? "observacion=" + observacion + ", " : "") +
                (indicadorCualitativo != null ? "indicadorCualitativo=" + indicadorCualitativo + ", " : "") +
                (notaBimestralFinal != null ? "notaBimestralFinal=" + notaBimestralFinal + ", " : "") +
                (promedioAutoevaluacion != null ? "promedioAutoevaluacion=" + promedioAutoevaluacion + ", " : "") +
                (autoevaluacionDecir != null ? "autoevaluacionDecir=" + autoevaluacionDecir + ", " : "") +
                (autoevaluacionSer != null ? "autoevaluacionSer=" + autoevaluacionSer + ", " : "") +
                (decirPromedio != null ? "decirPromedio=" + decirPromedio + ", " : "") +
                (hacerPromedio != null ? "hacerPromedio=" + hacerPromedio + ", " : "") +
                (saberPromedio != null ? "saberPromedio=" + saberPromedio + ", " : "") +
                (serPromedio != null ? "serPromedio=" + serPromedio + ", " : "") +
                (notaSer1 != null ? "notaSer1=" + notaSer1 + ", " : "") +
                (notaSer2 != null ? "notaSer2=" + notaSer2 + ", " : "") +
                (notaSer3 != null ? "notaSer3=" + notaSer3 + ", " : "") +
                (notaSer4 != null ? "notaSer4=" + notaSer4 + ", " : "") +
                (notaSer5 != null ? "notaSer5=" + notaSer5 + ", " : "") +
                (notaSer6 != null ? "notaSer6=" + notaSer6 + ", " : "") +
                (notaSaber1 != null ? "notaSaber1=" + notaSaber1 + ", " : "") +
                (notaSaber2 != null ? "notaSaber2=" + notaSaber2 + ", " : "") +
                (notaSaber3 != null ? "notaSaber3=" + notaSaber3 + ", " : "") +
                (notaSaber4 != null ? "notaSaber4=" + notaSaber4 + ", " : "") +
                (notaSaber5 != null ? "notaSaber5=" + notaSaber5 + ", " : "") +
                (notaSaber6 != null ? "notaSaber6=" + notaSaber6 + ", " : "") +
                (notaHacer1 != null ? "notaHacer1=" + notaHacer1 + ", " : "") +
                (notaHacer2 != null ? "notaHacer2=" + notaHacer2 + ", " : "") +
                (notaHacer3 != null ? "notaHacer3=" + notaHacer3 + ", " : "") +
                (notaHacer4 != null ? "notaHacer4=" + notaHacer4 + ", " : "") +
                (notaHacer5 != null ? "notaHacer5=" + notaHacer5 + ", " : "") +
                (notaHacer6 != null ? "notaHacer6=" + notaHacer6 + ", " : "") +
                (notaDecir1 != null ? "notaDecir1=" + notaDecir1 + ", " : "") +
                (notaDecir2 != null ? "notaDecir2=" + notaDecir2 + ", " : "") +
                (notaDecir3 != null ? "notaDecir3=" + notaDecir3 + ", " : "") +
                (notaDecir4 != null ? "notaDecir4=" + notaDecir4 + ", " : "") +
                (notaDecir5 != null ? "notaDecir5=" + notaDecir5 + ", " : "") +
                (notaDecir6 != null ? "notaDecir6=" + notaDecir6 + ", " : "") +
                (estudianteId != null ? "estudianteId=" + estudianteId + ", " : "") +
            "}";
    }

}
