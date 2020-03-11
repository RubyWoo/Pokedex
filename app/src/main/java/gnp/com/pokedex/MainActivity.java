package gnp.com.pokedex;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX"; //TAG para Log.e()
    private Retrofit retro                     ; //Referencia de tipo Retrofit
    private RecyclerView recycler              ; //Referencia de tipo RecyclerView
    private ListaPokemonAdapter listaPokemonA  ; //Referencia de tipo Adaptador

    private int offset;
    private boolean aptoParaCargar;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)        ;
        setContentView(R.layout.activity_main)    ;

        progressBar = findViewById(R.id.progress_horizontal);
        recycler = findViewById(R.id.recyclerView);//Asigno el RecyclerView del layout(Instancia)
        recycler.setHasFixedSize(true)            ;

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recycler.setLayoutManager(layoutManager);//Modo Linear(Checar modo Grid)

        listaPokemonA = new ListaPokemonAdapter(this);
        recycler.setAdapter(listaPokemonA)                   ;//Asignamos toda la info al RecyclerView


        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar){

                        if((visibleItemCount + pastVisibleItems) >= totalCount){
                            aptoParaCargar = false;
                            offset+=20;
                            progressBar.setVisibility(View.VISIBLE);
                            obtenerDatos(offset);

                        }

                    }

                }
            }
        });

        retro = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        offset = 0;
        aptoParaCargar = true;
        obtenerDatos(offset);
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////E ND onCreate



    private void obtenerDatos(final int offset) {

        //create() Metodo de Retrofit...Referencia 'service' de tipo PokeapiService (Interfaz)
        PokeapiService service = retro.create(PokeapiService.class);
        Call<PokemonRespuesta> pokeRespuestaCall = service.obtenerListaPokemon(20,offset);

        pokeRespuestaCall.enqueue(new Callback<PokemonRespuesta>() { //enqueue() metodo de Retrofit, Clase anonima

            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) { //METODO 1
                aptoParaCargar = true;


                if (response.isSuccessful()) {

                    PokemonRespuesta pokeRespuesta = response.body();
                    ArrayList<Pokemon> listaPoke   = pokeRespuesta.getResults();
                    listaPokemonA.adicionarListaPokemon(listaPoke);



                    listaPokemonA.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Exito al cargar los pokemon", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Error respuesta", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }

            } //------------------------------------------------------------------------------------------ End onResponse

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {

                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if(t.toString().contains("Unable to resolve host")){

                    AlertDialog.Builder alerta =
                            new AlertDialog.Builder(MainActivity.this);

                    alerta.setMessage(
                            "No estás conectado. Comprueba la conexión.")
                            .setCancelable(false)
                            .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    obtenerDatos(offset);

                                }
                            })
                            .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.GONE);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Conéctate a Internet");
                    titulo.show();

                } else{

                    AlertDialog.Builder alerta =
                            new AlertDialog.Builder(MainActivity.this);

                    alerta.setMessage(
                            "Ha exirado el tiempo de espera o algún error ha ocurrido")
                            .setCancelable(false)
                            .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    obtenerDatos(offset);

                                }
                            })
                            .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.GONE);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error al cargar los pokemon");
                    titulo.show();


                }
            }

        });

    }/////////////////////////////////////////////////////////////////////////////////// END obtenerDatos

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reload, menu);
        return super.onCreateOptionsMenu(menu);

    }//////////////////////////////////////////////////////////////////////END onCreateOptionsMenu()


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reload){
            obtenerDatos(offset);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }/////////////////////////////////////////////////////// END METHOD - BOTÓN 'BACK' EN ACTION BAR*/
}//////////////////////////////////////////////////////////////////////////////////////// END CLASS
