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

    private LongFilter idEstudiante;

    private LongFilter idDocente;

    private IntegerFilter bimestre;

    private StringFilter paralelo;

    private IntegerFilter gestion;

    private LongFilter idMateria;

    public BimestreCriteria() {
    }

    public BimestreCriteria(LongFilter idEstudiante, LongFilter idDocente, IntegerFilter bimestre, StringFilter paralelo, IntegerFilter gestion, LongFilter idMateria) {
        this.idEstudiante = idEstudiante;
        this.idDocente = idDocente;
        this.bimestre = bimestre;
        this.paralelo = paralelo;
        this.gestion = gestion;
        this.idMateria = idMateria;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LongFilter getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(LongFilter idEstudiante) {
        this.idEstudiante = idEstudiante;
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

    @Override
    public String toString() {
        return "BimestreCriteria{" +
            "idEstudiante=" + idEstudiante +
            ", idDocente=" + idDocente +
            ", bimestre=" + bimestre +
            ", paralelo=" + paralelo +
            ", gestion=" + gestion +
            ", idMateria=" + idMateria +
            '}';
    }
}
