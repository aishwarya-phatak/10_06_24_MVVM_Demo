package com.bitcode.a10_06_24_mvvm_demo.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitcode.a10_06_24_mvvm_demo.models.User
import com.bitcode.a10_06_24_mvvm_demo.models.UserPostModel
import com.bitcode.a10_06_24_mvvm_demo.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(private val usersRepository : UsersRepository) : ViewModel() {

    private val usersUpdatedAvailableLiveData = MutableLiveData<Boolean>()
    private val users = ArrayList<User>()

    private var pageNumber : Int = 0

    fun fetchUsers(){
        CoroutineScope(Dispatchers.IO).launch {
            val users = usersRepository.fetchUsers(++pageNumber)

            withContext(Dispatchers.Main){
                this@UsersViewModel.users.addAll(users)
                usersUpdatedAvailableLiveData.postValue(true)
            }
        }
    }

    private val userPostedLiveData = MutableLiveData<Boolean>()
    fun addUser(userPostModel: UserPostModel){
        CoroutineScope(Dispatchers.IO).launch {
            val responsePostUser = usersRepository.addUser(userPostModel)
            Log.e("tag",responsePostUser.toString())
            withContext(Dispatchers.Main){
                userPostedLiveData.postValue(true)
            }
        }
    }
}