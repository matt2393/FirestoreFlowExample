package com.matdev.firestoreflowexample.prueba

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matdev.firestoreflowexample.R
import com.matdev.firestoreflowexample.databinding.ActivityMainBinding
import com.matdev.firestoreflowexample.model.entities.Prueba

class MainActivity : AppCompatActivity() {
    private val viewModel: PruebaViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var adapter: PruebaAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = PruebaAdapter()
        binding.recyclerMain.layoutManager = LinearLayoutManager(this)
        binding.recyclerMain.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        initObserverrs()
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrs() {
        viewModel.prueba.observe(this) {
            Log.e("PRueba", it.toString())
            when(it.type) {
                Prueba.TYPE.ADD -> adapter?.arrayPrueba?.add(it)
                Prueba.TYPE.UPDATE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayPrueba?.set(pos, it)
                    }
                }
                Prueba.TYPE.REMOVE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayPrueba?.removeAt(pos)
                    }
                }
            }
            adapter?.notifyDataSetChanged()
        }
        viewModel.error.observe(this) {
            Log.e("ErrorPrueba", it.toString())
        }
        viewModel.getPruebas()
    }
    private fun removeObservers() {
        viewModel.prueba.removeObservers(this)
        viewModel.error.removeObservers(this)
        viewModel.removeListener()
    }
}