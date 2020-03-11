package gnp.com.pokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.MiViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context           ;

    public ListaPokemonAdapter(Context context) { //Constructor
        this.context = context;
        dataset = new ArrayList<>();
    } // END constructor

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new MiViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) { //Cargo el nombre e imagen del pokemon

        Pokemon p = dataset.get(position)         ;
        holder.nombreTextView.setText(p.getName());

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber()+ ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.fotoImageView);
    }
    @Override
    public int getItemCount() { //Metodo que retorna el tamaño de la lista
        return dataset.size();
    }

    //*****************************************************************************************************
    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) { //Metodo utilizado en MainActivity
        dataset.addAll(listaPokemon);
        notifyDataSetChanged()      ;
    }


    //-----------------------------------------------------------------------------------INNER CLASS
    public class MiViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView ;
        private TextView  nombreTextView;

        public MiViewHolder(View itemView) {
            super(itemView);
        fotoImageView  = itemView.findViewById(R.id.fotoImageView) ;
        nombreTextView = itemView.findViewById(R.id.nombreTextView);

        } ////// END Constructor INNER CLASS
    }///////////////////////////////////////////////////////////////////////////////END INNER CLASS
}///////////////////////////////////////////////////////////////////////////////////END CLASS
