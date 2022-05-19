package com.efrain.intercamprueba.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.adapters.MainAdapter
import com.efrain.intercamprueba.data.dao.BeersDao
import com.efrain.intercamprueba.databinding.FragmentMainBinding
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.ui.base.BaseFragment
import com.efrain.intercamprueba.viewmodels.main.BeersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), MainAdapter.AddBeersDataBase {

    @Inject lateinit var beersDao: BeersDao
    private val mainAdapter = MainAdapter(this)
    private var mBeers = mutableListOf<EntityBeers>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private var isLoading: Boolean = false
    private var page = 1

    private val beersViewModel: BeersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initObserve()
        beersViewModel.getAllBeersPage("$page")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fav, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_fav -> {
                navController.navigate(R.id.action_mainFragment_to_favoritesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated() {
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.recycler.apply {
            layoutManager = mLayoutManager
            adapter = mainAdapter
        }

        binding.swipe.setOnRefreshListener {
            mainAdapter.submitList(emptyList())
            mBeers = mutableListOf()
            page = 1
            beersViewModel.getAllBeersPage("$page")
        }

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mBeers.size - 1) {
                        page++
                        beersViewModel.getAllBeersPage("$page")
                    }
                }
            }
        })

        mainAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                mLayoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })
    }

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?)
            = FragmentMainBinding.inflate(inflater, container, false)

    override fun addBeer(beers: EntityBeers) {
        viewLifecycleOwner.lifecycleScope.launch {
            beersDao.inserBeer(beers)
            Toast.makeText(requireContext(), getString(R.string.text_add_favorites), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateDataList(newList:List<EntityBeers>) {
        val tempList = mBeers.toMutableList()
        tempList.addAll(newList)
        mainAdapter.submitList(tempList)
        mBeers = tempList
    }

    private fun initObserve() {
        beersViewModel.apply {
            beers.observe(this@MainFragment, {
                if(mBeers.isEmpty()) {
                    mainAdapter.submitList(it.toMutableList())
                    mBeers = it.toMutableList()
                } else {
                    updateDataList(it)
                }
            })

            loading.observe(this@MainFragment, {
                isLoading = it
                binding.swipe.isRefreshing = it
            })

            error.observe(this@MainFragment, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })
        }
    }
}