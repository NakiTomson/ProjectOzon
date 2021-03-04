package com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject

class SignUpFragment : Fragment() {

    init {
        OzonApp.appComponent.inject(signUpFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {

            val login = emailLogin.text.toString().trim().toLowerCase()
            val password = password.text.toString().trim()
            val passwordRepeat = password2.text.toString().trim()
            val nameUser = name.text.toString().trim()
            val addresDelivery = addresDelivery.text.toString().trim()
            val phone = phone.text.toString().trim()

            val result = checkInput(
                email = login,
                password,
                passwordRepeat,
                nameUser,
                addresDelivery,
                phone
            )

            if (!result) return@setOnClickListener
            mAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener{ task->
                    when{
                        task.isSuccessful ->{
                            mainViewModel.setUser(UserDB(nameUser,phone,login,password,addresDelivery))
                            findNavController().popBackStack()
                            requireActivity().bottomNavigationView.selectedItemId = R.id.account
                        }
                        else ->{
                            Toast.makeText(activity, "Error SignUp", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    fun checkInput(
        email: String,
        password: String,
        password_repeat: String,
        name: String,
        addresDelivery: String,
        phone: String
    ): Boolean {
        return if (email.length <= 3 || password.length < 6 || password_repeat.length < 6 || name.length <= 1 ||addresDelivery.length <=5 ||phone.length<8) {
          Toast.makeText(
              activity,
              "Пороль 6 символов или Не полный адресс или Телефон",
              Toast.LENGTH_SHORT
          ).show()
            false
        } else if (password != password_repeat) {
            Toast.makeText(activity, "Разные Пороли", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}