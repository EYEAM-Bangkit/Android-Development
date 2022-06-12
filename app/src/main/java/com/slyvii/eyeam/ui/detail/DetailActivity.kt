package com.slyvii.eyeam.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.slyvii.eyeam.R
import com.slyvii.eyeam.data.Data
import com.slyvii.eyeam.databinding.ActivityDetailBinding
import com.slyvii.eyeam.ui.ViewModelFactory
import com.slyvii.eyeam.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("UNCHECKED_CAST")
class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(dataStore))
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state(true)

        supportActionBar?.title = resources.getString(R.string.detail)

        val extra = intent.getStringExtra(RESULT)
        val token = intent.getStringExtra(TOKEN)

        viewModel.getAnimal(token!!, extra!!, this).observe(this) { data ->
            for (extras in data) {
                state(false)
                val photos = extras.foto?.split(", ")
                val family = "${resources.getString(R.string.family)}: ${extras.family}"
                val kingdom = "${resources.getString(R.string.kingdom)}: ${extras.kingdom}"
                val phylum = "${resources.getString(R.string.phylum)}: ${extras.phylum}"
                val genus = "${resources.getString(R.string.genus)}: ${extras.genus}"
                val order = "${resources.getString(R.string.order)}: ${extras.order}"
                val klas = "${resources.getString(R.string.klas)}: ${extras.jsonMemberClass}"

                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(photos?.get(0))
                        .into(imageDetail)
                    tvClass.text = klas
                    tvDetailNamaIlmiah.text = extras.namailmiah
                    tvDetailNamaPopuler.text = extras.namapopuler
                    tvFamily.text = family
                    tvKingdom.text = kingdom
                    tvGenus.text = genus
                    tvOrder.text = order
                    tvPhylum.text = phylum
                    tvIsiDesc.text = extras.deskripsi
                    tvConserv.text = extras.conserv
                    tvPopulation.text = extras.populasi
                    tvHabitat.text = extras.habitat
                    tvThreats.text = extras.threats
                    tvSpread.text = extras.persebaran

                    rvPopular.layoutManager = LinearLayoutManager(
                        this@DetailActivity,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    val listAdapter = DetailAdapter(photos as ArrayList<String>)
                    rvPopular.adapter = listAdapter
                }
            }
        }
    }

    private fun state(b: Boolean){
        if (b){
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    companion object {
        const val RESULT = "result"
        const val TOKEN = "token"
    }
}