package aplicacioniot.datos;
public class RegistroSensor {
    private String fecha; private String temperatura; private String humedad;
    public RegistroSensor(String fecha, String temperatura, String humedad) {
        this.fecha = fecha; this.temperatura = temperatura; this.humedad = humedad;
    }
    public String getFecha() { return fecha; }
    public String getTemperatura() { return temperatura; }
    public String getHumedad() { return humedad; }
}
