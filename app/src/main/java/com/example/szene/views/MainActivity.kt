package com.example.szene.views

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.szene.R
import com.example.szene.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.core.API
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.viewmodels.PeliculasViewModel
import com.example.myapplication.views.AdapterPeliculas
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PeliculasViewModel
    private lateinit var adapterPeliculas: AdapterPeliculas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializamos el binding ANTES de usarlo
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PeliculasViewModel::class.java]

        setupRecyclerView() // ✅ Ahora sí se puede usar el binding


        lifecycleScope.launch {
            try {
                Log.d("API_CALL", "Llamando a la API de populares...")
                val response = RetrofitClient.webService.obtenerPopulares(API.API_KEY)

                if (response.isSuccessful) {
                    val peliculas = response.body()?.resultados
                    Log.d("API_SUCCESS", "Peliculas recibidas: ${peliculas?.size}")
                } else {
                    Log.e("API_FAILURE", "Errosito: ${response.code()} - ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Excepción en la API: ${e.localizedMessage}", e)
            }
        }


        viewModel.listaPeliculas.observe(this) {
            adapterPeliculas.listaPeliculas = it
            adapterPeliculas.notifyDataSetChanged()
        }

        binding.cvCartelera.setOnClickListener {
            viewModel.obtenerCartelera()
            cambiarColorBoton("car")
        }

        binding.cvPopulares.setOnClickListener {
            viewModel.obtenerPopulares()
            cambiarColorBoton("pop")
        }

        viewModel.obtenerCartelera()
    }

    private fun cambiarColorBoton(boton:String){
        when(boton){
            "car" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.verde_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.azul_200))
            }

            "pop" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.verde_200))
            }
        }


    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this,3)
        binding.rvPeliculas.layoutManager = layoutManager
        adapterPeliculas = AdapterPeliculas(this, arrayListOf())
        binding.rvPeliculas.adapter = adapterPeliculas

    }
}