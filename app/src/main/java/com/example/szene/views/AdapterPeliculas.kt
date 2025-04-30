package com.example.myapplication.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.core.API
import com.example.myapplication.models.PeliculaModel
import com.example.myapplication.network.RetrofitClient
import com.example.szene.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterPeliculas(
    val context: Context,
    var listaPeliculas: List<PeliculaModel>
) : RecyclerView.Adapter<AdapterPeliculas.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvPelicula = itemView.findViewById(R.id.cvPelicula) as CardView
        val ivPoster = itemView.findViewById(R.id.ivPoster) as ImageView
        val pcIndicator = itemView.findViewById(R.id.circular_progress) as CircularProgressIndicator

    }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_peliculas, parent, false)
       return ViewHolder(vista)
    }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peliculas = listaPeliculas[position]

       Glide
           .with(context)
           .load("${API.BASE_URL_IMAGEN}${peliculas.poster}")
           .apply(RequestOptions().override(API.IMAGEN_ANCHO,API.IMAGEN_ALTO))
           .into(holder.ivPoster)

       holder.pcIndicator.maxProgress = API.MAX_CALIFICATION
       holder.pcIndicator.setCurrentProgress(peliculas.votoPromedio.toDouble())


   }

   override fun getItemCount(): Int {
       return listaPeliculas.size
   }

    fun actualizarPeliculas( listaPeliculas: List<PeliculaModel>){
        this.listaPeliculas = listaPeliculas
        notifyDataSetChanged()
    }
}