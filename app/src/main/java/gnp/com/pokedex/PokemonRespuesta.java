package gnp.com.pokedex;


import java.util.ArrayList;

public class PokemonRespuesta {


    public ArrayList<Pokemon> results; //Referencia 'results' de tipo ArrayList



    //NOTA: Creamos un objeto de tipo PokemonRespuesta y luego invocamos al metodo getResults() para obtener
    //..... 'results' de tipo ArrayList<>
    //GETTER
    public ArrayList<Pokemon> getResults() { //Se usa este metodo en MainActivity
        return results;
    }

    //-----------------------------------------------------------------------------------------------------


    //SETTER
    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }

} ///////////////////////////////////////////////////////////// END CLASS
