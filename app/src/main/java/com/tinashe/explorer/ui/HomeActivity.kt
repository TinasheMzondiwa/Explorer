package com.tinashe.explorer.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.MenuItem
import com.tinashe.explorer.R
import com.tinashe.explorer.di.ViewModelFactory
import com.tinashe.explorer.sdk.data.model.EntityType.MALL
import com.tinashe.explorer.sdk.data.model.EntityType.SHOP
import com.tinashe.explorer.ui.adapter.EntityListAdapter
import com.tinashe.explorer.utils.getViewModel
import com.tinashe.explorer.utils.hide
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    private lateinit var lisAdapter: EntityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel(this, viewModelFactory)

        viewModel.displayTitle.observe(this, Observer {
            title = it ?: getString(R.string.cities)
        })
        viewModel.level.observe(this, Observer {
            supportActionBar?.setDisplayHomeAsUpEnabled(it == MALL || it == SHOP)
        })
        viewModel.errorRes.observe(this, Observer { it ->
            it?.let { res ->
                progressBar.hide()

                Snackbar.make(recyclerView, res, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {}
                        .show()
            }
        })
        viewModel.entityList.observe(this, Observer {
            progressBar.hide()
            it?.let { entities ->
                lisAdapter.items = entities
            }
        })

        initUI()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)

        lisAdapter = EntityListAdapter { viewModel.entitySelected(it) }
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = lisAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (viewModel.navigateBack()) {
            super.onBackPressed()
        }
    }
}
