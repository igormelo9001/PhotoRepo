package com.project.app.photorepo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.app.photorepo.Foto;
import com.project.app.photorepo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFoto extends RecyclerView.Adapter<AdapterFoto.MyViewHolder> {

    private List<Foto> fotos;

    public AdapterFoto(List<Foto> fotos) {
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_foto, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Foto foto = fotos.get(i);
        holder.descricao.setText(foto.getDescricao());

        //Carregar imagem
        String urlImagem = foto.getUrlImagem();
        if(urlImagem != null){
            Picasso.get().load( urlImagem ).into( holder.fotoImagem );
        }else {
            holder.fotoImagem.setImageResource(R.drawable.perfil);
        }

    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoImagem;
        TextView descricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.textDescricao);
            fotoImagem = itemView.findViewById(R.id.imageFoto);
        }
    }
}

