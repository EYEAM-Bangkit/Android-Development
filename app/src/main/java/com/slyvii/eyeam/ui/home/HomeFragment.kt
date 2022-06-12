package com.slyvii.eyeam.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyvii.eyeam.data.Animal
import com.slyvii.eyeam.data.AnimalData
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.databinding.FragmentHomeBinding
import com.slyvii.eyeam.ui.ViewModelFactory
import com.slyvii.eyeam.ui.detail.DetailActivity
import com.slyvii.eyeam.ui.detail.NewsDetailActivity
import com.slyvii.eyeam.ui.detail.NewsDetailActivity.Companion.URL
import com.slyvii.eyeam.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var token: String? = null
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(context?.dataStore!!))
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var list: ArrayList<Animal> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(AnimalData.list)
        showList()
        getNews()

        homeViewModel.getName().observe(viewLifecycleOwner) {
            Log.e("TAG", "onViewCreated: $it", )
            binding.tvName.text = it
        }

        homeViewModel.getToken().observe(viewLifecycleOwner) {
            token = "Bearer $it"
        }

        binding.etSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val query = binding.etSearch.text.toString()
                detailIntent(requireContext(), query, token!!)
                return@OnKeyListener true
            }
            false
        })

        binding.searchImage.setOnClickListener {
            val query = binding.etSearch.text.toString()
            detailIntent(requireContext(), query, token!!)
        }
    }

    private fun detailIntent(context: Context, query: String, token: String) {
        startActivity(
            Intent(context, DetailActivity::class.java).putExtra(
                DetailActivity.RESULT,
                query
            ).putExtra(DetailActivity.TOKEN, token)
        )
    }

    private fun showList() {
        binding.apply {
            rvPopular.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val listPopularAdapter = PopularAdapter(list)
            rvPopular.adapter = listPopularAdapter

            listPopularAdapter.setOnItemClickCallback(object :
                PopularAdapter.OnPopularClickCallback {
                override fun onItemClick(data: Animal) {
                    detailIntent(requireContext(), data.name, token!!)
                }
            })
        }
    }

    private fun getNews() {
        homeViewModel.getNews(requireContext()).observe(viewLifecycleOwner) {
            val listItems = ArrayList<ArticlesItem>()
            listItems.addAll(it)
            val newsAdapter = NewsAdapter(listItems)
            with(_binding?.rvNews) {
                this?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                this?.adapter = newsAdapter
                this?.setHasFixedSize(true)
            }

            newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnNewsClickCallback {
                override fun onItemClick(data: ArticlesItem) {
                    val intent = Intent(context, NewsDetailActivity::class.java)
                    intent.putExtra(URL, data.url)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}