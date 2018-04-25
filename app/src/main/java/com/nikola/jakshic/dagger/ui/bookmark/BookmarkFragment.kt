package com.nikola.jakshic.dagger.ui.bookmark

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nikola.jakshic.dagger.DaggerApp
import com.nikola.jakshic.dagger.R
import com.nikola.jakshic.dagger.inflate
import com.nikola.jakshic.dagger.view.activity.PlayerActivity
import com.nikola.jakshic.dagger.viewModel.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_bookmark.*
import javax.inject.Inject

class BookmarkFragment : Fragment() {

    @Inject lateinit var factory: DaggerViewModelFactory

    override fun onAttach(context: Context?) {
        (activity?.application as DaggerApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_bookmark)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewModel = ViewModelProviders.of(this, factory)[BookmarkViewModel::class.java]

        val adapter = PlayerAdapter {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("player-personaname", it.personaName)
            intent.putExtra("player-account-id", it.id)
            intent.putExtra("player-avatar-full", it.avatarUrl)
            startActivity(intent)
        }

        recView.layoutManager = LinearLayoutManager(activity)
        recView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recView.adapter = adapter
        recView.setHasFixedSize(true)

        viewModel.list.observe(this, Observer(adapter::addData))
    }

    override fun onResume() {
        super.onResume()
        // This fragment is an item from BottomNavigationView
        // Set the proper title when this fragment is not hidden
        if (!isHidden) activity?.title = "Bookmark"
    }
}