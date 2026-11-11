package aplicacioniot;
import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.TextView;
import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView; import java.util.List;
import aplicacioniot.datos.RegistroSensor;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {
    private List<RegistroSensor> listaRegistros;
    public HistorialAdapter(List<RegistroSensor> listaRegistros) { this.listaRegistros = listaRegistros; }
    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registro, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegistroSensor registro = listaRegistros.get(position);
        holder.tvFecha.setText(registro.getFecha());
        holder.tvTemp.setText(registro.getTemperatura() + " °C");
        holder.tvHum.setText(registro.getHumedad() + " %");
    }
    @Override public int getItemCount() { return listaRegistros.size(); }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvTemp, tvHum;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_item_fecha);
            tvTemp = itemView.findViewById(R.id.tv_item_temp);
            tvHum = itemView.findViewById(R.id.tv_item_hum);
        }
    }
}
