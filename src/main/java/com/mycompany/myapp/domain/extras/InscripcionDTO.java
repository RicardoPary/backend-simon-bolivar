package com.mycompany.myapp.domain.extras;
import com.mycompany.myapp.domain.EstudianteCurso;

import java.time.Instant;

public class InscripcionDTO {

    private Long idEstudiante;
    private Long idCurso;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;



    public InscripcionDTO(EstudianteCurso estudianteCurso) {
        this.idEstudiante = estudianteCurso.getIdEstudiante();
        this.idCurso = estudianteCurso.getIdCurso();
        this.createdBy = estudianteCurso.getCreatedBy();
        this.createdDate = estudianteCurso.getCreatedDate();
        this.lastModifiedBy = estudianteCurso.getLastModifiedBy();
        this.lastModifiedDate = estudianteCurso.getLastModifiedDate();
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
