package com.slyvii.eyeam.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyvii.eyeam.R
import com.slyvii.eyeam.data.DataItems
import com.slyvii.eyeam.databinding.ActivityHistoryBinding
import com.slyvii.eyeam.ui.ViewModelFactory
import com.slyvii.eyeam.ui.detail.DetailActivity
import com.slyvii.eyeam.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.history)

        viewModel.getToken().observe(this) { token ->
            Log.e("token", "onCreate: $token")
            viewModel.getItems("Bearer $token").observe(this@HistoryActivity) {
                binding.rvHistory.layoutManager =
                    LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
                val historyAdapter = HistoryAdapter(it)
                binding.rvHistory.adapter = historyAdapter
                binding.rvHistory.setHasFixedSize(true)
            }
        }

    }
}