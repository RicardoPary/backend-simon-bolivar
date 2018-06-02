package com.mycompany.myapp.domain.extras;

public class FamiliarDTO {

    private Long id;
    private Long ci;
    private String nombre;
    private String paterno;
    private String materno;
    private String genero;
    private String fechaNacimiento;
    private String nacionalidad;
    private String direccion;
    private Long telefono;
    private Long idEstudiante;
    private String parentesco;

    public FamiliarDTO(Long id, Long ci, String nombre, String paterno, String materno, String genero, String fechaNacimiento, String nacionalidad, String direccion, Long telefono, Long idEstudiante, String parentesco) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idEstudiante = idEstudiante;
        this.parentesco = parentesco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCi() {
        return ci;
    }

    public void setCi(Long ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
