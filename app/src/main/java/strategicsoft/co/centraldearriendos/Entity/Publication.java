package strategicsoft.co.centraldearriendos.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by servintesas on 3/08/15.
 */
public class Publication {

    @SerializedName("id")
    private int idPublication;

    @SerializedName("tipologia")
    private String tipologia;

    @SerializedName("inmobiliaria")
    private String inmobiliaria;

    @SerializedName("admin")
    private int costeAdministracion;

    @SerializedName("estrato")
    private int estrato;

    @SerializedName("precio")
    private double precio;

    @SerializedName("ciudad")
    private String ciudad;

    @SerializedName("barrio")
    private String barrio;

    @SerializedName("area")
    private String area;

    @SerializedName("alcobas")
    private int alcobas;

    @SerializedName("banos")
    private int banos;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("email")
    private String email;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("tiponegocio")
    private String tipoNegocio;

    @SerializedName("cordenada1")
    private Double latitud;

    @SerializedName("cordenada2")
    private Double longitud;

    @SerializedName("url")
    private String urlImagen;

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getInmobiliaria() {
        return inmobiliaria;
    }

    public void setInmobiliaria(String inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
    }

    public int getCosteAdministracion() {
        return costeAdministracion;
    }

    public void setCosteAdministracion(int costeAdministracion) {
        this.costeAdministracion = costeAdministracion;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getAlcobas() {
        return alcobas;
    }

    public void setAlcobas(int alcobas) {
        this.alcobas = alcobas;
    }

    public int getBanos() {
        return banos;
    }

    public void setBanos(int banos) {
        this.banos = banos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
