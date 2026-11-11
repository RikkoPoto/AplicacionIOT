package aplicacioniot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import aplicacioniot.datos.Conexion;
import aplicacioniot.datos.RegistroSensor;

public class MainActivity extends AppCompatActivity {
    private TextView tvTempActual, tvHumActual;
    private Button btnActualizar;
    private RecyclerView rvHistorial;
    private HistorialAdapter adapter;
    private List<RegistroSensor> listaHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTempActual = findViewById(R.id.tv_temp_actual);
        tvHumActual = findViewById(R.id.tv_hum_actual);
        btnActualizar = findViewById(R.id.btn_actualizar_historial);
        rvHistorial = findViewById(R.id.rv_historial);

        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        listaHistorial = new ArrayList<>();
        adapter = new HistorialAdapter(listaHistorial);
        rvHistorial.setAdapter(adapter);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarHistorial();
            }
        });
        
        cargarHistorial();
    }

    private void cargarHistorial() {
        String url = Conexion.URL_WEB_SERVICES;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaHistorial.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                double timestampDouble = jsonObject.getDouble("timestamp");
                                long timestampMilis = (long) timestampDouble;
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                String fechaFormateada = sdf.format(new java.util.Date(timestampMilis));

                                String temp = String.valueOf(jsonObject.getDouble("temperatura"));
                                String hum = String.valueOf(jsonObject.getDouble("humedad"));

                                if (i == 0) {
                                    tvTempActual.setText(temp + " °C");
                                    tvHumActual.setText(hum + " %");
                                } else {
                                    listaHistorial.add(new RegistroSensor(fechaFormateada, temp, hum));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error formateando el JSON de Lambda", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error de conexión a AWS", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
