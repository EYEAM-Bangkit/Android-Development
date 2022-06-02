package com.slyvii.eyeam.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyvii.eyeam.data.Animal
import com.slyvii.eyeam.data.AnimalData
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.databinding.FragmentHomeBinding
import com.slyvii.eyeam.ui.NewsDetailActivity
import com.slyvii.eyeam.ui.NewsDetailActivity.Companion.URL

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var list: ArrayList<Animal> = arrayListOf()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(AnimalData.list)
        showList()
        getNews()
    }

    private fun showList() {
        binding.apply {
            rvPopular.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, true)
            val listPopularAdapter = PopularAdapter(list)
            rvPopular.adapter = listPopularAdapter
        }
    }

    private fun getNews() {
        homeViewModel.getNews().observe(viewLifecycleOwner) {
            val listItems = ArrayList<ArticlesItem>()
            listItems.addAll(it)
            val newsAdapter = NewsAdapter(listItems)
            with(_binding?.rvNews) {
                this?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                this?.adapter = newsAdapter
                this?.setHasFixedSize(true)
            }

            newsAdapter.setOnItemClickCallback(object: NewsAdapter.OnNewsClickCallback{
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