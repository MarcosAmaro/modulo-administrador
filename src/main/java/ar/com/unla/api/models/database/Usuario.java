package ar.com.unla.api.models.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
@ApiModel
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "nombre", required = true, example = "Nicolas", position = 1)
    private String nombre;

    @Column(nullable = false)
    @ApiModelProperty(notes = "apellido", required = true, example = "Gianni", position = 2)
    private String apellido;

    @Column(nullable = false)
    @ApiModelProperty(notes = "telefono", required = true, example = "158789200", position = 3)
    private String telefono;

    @Column(nullable = false)
    @ApiModelProperty(notes = "dni", required = true, example = "36789637", position = 4)
    private String dni;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "email", required = true, example = "Nico@gmail.com", position = 5)
    private String email;

    @OneToOne(targetEntity = Direccion.class, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idDireccion")
    @ApiModelProperty(notes = "direccion", required = true, position = 6)
    private Direccion direccion;

    //@JsonProperty(access = Access.WRITE_ONLY)
    @Column(nullable = false)
    @ApiModelProperty(notes = "password", required = true, position = 7)
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    @ApiModelProperty(notes = "imagen", position = 8)
    private String imagen;

    @Column(nullable = false)
    @ApiModelProperty(notes = "primerIngreso", required = true, position = 9)
    private Boolean primerIngreso;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idRol")
    @ApiModelProperty(notes = "tipoUsuario", required = true, position = 10)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String telefono, String dni, String email,
            Direccion direccion, String password, String imagen, Boolean primerIngreso,
            Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
        this.direccion = direccion;
        this.password = password;
        this.imagen = imagen;
        this.primerIngreso = primerIngreso;
        this.rol = rol;
    }

    /*
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }*/
}
