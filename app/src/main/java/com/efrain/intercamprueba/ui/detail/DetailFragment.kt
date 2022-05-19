package com.efrain.intercamprueba.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.adapters.FoodPairingAdapter
import com.efrain.intercamprueba.adapters.HoopsAdapter
import com.efrain.intercamprueba.data.dao.BeersDao
import com.efrain.intercamprueba.databinding.FragmentDetailBinding
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.entities.EntityDetailBeer
import com.efrain.intercamprueba.ui.base.BaseFragment
import com.efrain.intercamprueba.viewmodels.main.DetailBeerViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args: DetailFragmentArgs by navArgs()
    private var  beer: EntityDetailBeer? = null
    @Inject lateinit var beersDao: BeersDao
    private val detailBeersViewModel: DetailBeerViewModel by viewModels()

    private val foodPairingAdapter = FoodPairingAdapter()
    private val hoopsAdapter = HoopsAdapter()
    private val maltAdapter = HoopsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserve()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fav, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_fav -> {
                beer?.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val beer = EntityBeers(it.id, it.name, it.imageUrl, it.contributedBy)
                        beersDao.inserBeer(beer)
                        Toast.makeText(requireContext(), getString(R.string.text_add_favorites), Toast.LENGTH_LONG).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated() {
        detailBeersViewModel.detailBeer(args.id)

        binding.apply {
            rvFoodPairing.layoutManager = LinearLayoutManager(requireContext())
            rvFoodPairing.adapter = foodPairingAdapter

            rvHops.layoutManager = LinearLayoutManager(requireContext())
            rvHops.adapter = hoopsAdapter

            rvMalt.layoutManager = LinearLayoutManager(requireContext())
            rvMalt.adapter = maltAdapter

            swipe.setOnRefreshListener {
                detailBeersViewModel.detailBeer(args.id)
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?)
            = FragmentDetailBinding.inflate(inflater, container, false)

    private fun setDataBeer(detailBeer: EntityDetailBeer) {
        beer = detailBeer
        binding.apply {
            Picasso.get().load(detailBeer.imageUrl).into(ivLogo)

            tvTitle.text = detailBeer.name
            tvAbv.text = "${detailBeer.abv} %"
            tvFg.text = detailBeer.targetFg
            tvFirstBrewed.text = getString(R.string.text_first_brewen, detailBeer.firstBrewed)
            tvIbu.text = "${detailBeer.ibu}G"
            tvOg.text = detailBeer.targetOg
            tvTagLine.text = detailBeer.tagLine
            tvSubtitle.text = detailBeer.ingredients.yeast
            tvTips.text = detailBeer.brewersTips
            tvVolume.text = "${detailBeer.volume.value} ${detailBeer.volume.unit}"

            scroll.visibility = View.VISIBLE

            foodPairingAdapter.addItems(detailBeer.foodPairing)
            hoopsAdapter.addItems(detailBeer.ingredients.hops)
            maltAdapter.addItems(detailBeer.ingredients.malt)
        }
        setHasOptionsMenu(true)
    }

    private fun initObserve() {
        detailBeersViewModel.apply {
            loading.observe(this@DetailFragment, {
                binding.swipe.isRefreshing = it
            })

            error.observe(this@DetailFragment, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            })

            beers.observe(this@DetailFragment, {
                setDataBeer(it)
            })
        }
    }
}