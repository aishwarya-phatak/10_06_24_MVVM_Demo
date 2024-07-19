package com.bitcode.a10_06_24_mvvm_demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bitcode.a10_06_24_mvvm_demo.R
import com.bitcode.a10_06_24_mvvm_demo.factory.MyViewModelFactory
import com.bitcode.a10_06_24_mvvm_demo.models.UserPostModel
import com.bitcode.a10_06_24_mvvm_demo.network.UsersApiService
import com.bitcode.a10_06_24_mvvm_demo.repository.UsersRepository
import com.bitcode.a10_06_24_mvvm_demo.viewmodels.UsersViewModel

class AddUserFragment : Fragment() {
    private lateinit var edtName : EditText
    private lateinit var edtJob : EditText
    private lateinit var btnAddUser : Button

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_user_fragment,null)

        initViews(view)
        initViewModels()
        initObservers()
        initListeners()

        return view
    }

    private fun initViews(view : View){
        edtJob = view.findViewById(R.id.edtJob)
        edtName = view.findViewById(R.id.edtName)
        btnAddUser = view.findViewById(R.id.btnAddUser)
    }

    private fun initViewModels(){
        usersViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                UsersRepository(UsersApiService.getInstance())
            )
        ).get(UsersViewModel::class.java)
    }

    private fun initListeners(){
        btnAddUser.setOnClickListener {
            usersViewModel.addUser(
                UserPostModel(
                    edtName.text.toString(),
                    edtJob.text.toString()
                )
            )
        }
    }

    private fun initObservers() {
        usersViewModel.userPostedLiveData.observe(viewLifecycleOwner) {
                removeCurrentFragment()
        }
    }

    private fun removeCurrentFragment(){
        parentFragmentManager.popBackStack()
    }
}