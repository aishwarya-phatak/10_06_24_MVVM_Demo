package com.bitcode.a10_06_24_mvvm_demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bitcode.a10_06_24_mvvm_demo.R
import com.bitcode.a10_06_24_mvvm_demo.adapters.UsersAdapter
import com.bitcode.a10_06_24_mvvm_demo.databinding.UsersFragmentBinding
import com.bitcode.a10_06_24_mvvm_demo.factory.MyViewModelFactory
import com.bitcode.a10_06_24_mvvm_demo.network.UsersApiService
import com.bitcode.a10_06_24_mvvm_demo.repository.UsersRepository
import com.bitcode.a10_06_24_mvvm_demo.viewmodels.UsersViewModel

class UsersFragment : Fragment() {
    private lateinit var usersFragmentBinding: UsersFragmentBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       usersFragmentBinding = UsersFragmentBinding.inflate(layoutInflater)
        initViews()
        initViewModels()
        initAdapters()
        initObservers()
        initListeners()

        usersViewModel.fetchUsers()
        return usersFragmentBinding.root
    }

    private fun initObservers(){
        usersViewModel.usersUpdatedAvailableLiveData.observe(
            viewLifecycleOwner
        ) {
            if (it){
                usersAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initListeners(){
        usersFragmentBinding.recyclerUsers.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                        usersViewModel.fetchUsers()
                    }
                }
            }
        )

        usersFragmentBinding.btnAddUser.setOnClickListener {
            addUserFragment()
        }
    }

    private fun addUserFragment(){
        val addUserFragment = AddUserFragment()
        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer, addUserFragment, null)
            .addToBackStack(null)
            .commit()
    }

    private fun initAdapters(){
        usersAdapter = UsersAdapter(usersViewModel.users)
        usersFragmentBinding.recyclerUsers.adapter = usersAdapter
    }

    private fun initViewModels(){
        usersViewModel = ViewModelProvider(this,
            MyViewModelFactory(
                UsersRepository((
                        UsersApiService.getInstance())))
        ).get(UsersViewModel::class.java)
    }

    private fun initViews(){
        usersFragmentBinding.recyclerUsers.layoutManager = LinearLayoutManager(requireContext(),
                                                                                LinearLayoutManager.VERTICAL,
                                                                                false)
    }
}