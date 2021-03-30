package cl.duoc.ejemplolistviewclase;

public class Persona {

    private String nombre;
    private String apellido;

    public Persona(String nombre, String apellido) throws Exception {
        setNombre(nombre);
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if (nombre.length() > 4) {
            this.nombre = nombre;
        } else {
            throw new Exception(String.valueOf(R.string.nombre_exception)); //"1900025"
        }

    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
