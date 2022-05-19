package com.efrain.intercamprueba.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.adapters.FavoritesAdapter
import com.efrain.intercamprueba.data.dao.BeersDao
import com.efrain.intercamprueba.databinding.FragmentFavoritesBinding
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(), FavoritesAdapter.DeleteFavDataBase {

    private val mainAdapter = FavoritesAdapter(this)
    @Inject lateinit var beersDao: BeersDao

    override fun onViewCreated() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }

        binding.swipe.setOnRefreshListener {
            mainAdapter.addItems(beersDao.getAllBeers())
        }

        mainAdapter.addItems(beersDao.getAllBeers())
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?)
        = FragmentFavoritesBinding.inflate(inflater, container, false)

    override fun deleteBeer(beers: EntityBeers) {
        AlertDialog.Builder(requireContext()).apply {
            setMessage(getString(R.string.text_warning_remove_fav))
            setPositiveButton(getString(R.string.text_ok)) { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    beersDao.delete(beers)
                    mainAdapter.addItems(beersDao.getAllBeers())
                }
            }
            setNegativeButton(getString(R.string.btn_cancel)) { _,_ ->

            }
        }.show()

    }

}