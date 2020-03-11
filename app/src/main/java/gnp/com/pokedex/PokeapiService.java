package gnp.com.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {//Client Service

    @GET("pokemon")//Hago la peticion al recurso "pokemon"
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit,@Query("offset") int offset);

} //END INTERFACE
