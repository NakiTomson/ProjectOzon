package com.app.marketPlace.presentation.activities.ui.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.modelsDB.UserDB
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.*
import javax.inject.Inject

class SignUpFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(signUpFragment = this)
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

            val login = emailLogin.text.toString().trim().toLowerCase(Locale.ROOT)
            val password = password.text.toString().trim()
            val passwordRepeat = password2.text.toString().trim()
            val nameUser = name.text.toString().trim()
            val addressDelivery = addressDelivery.text.toString().trim()
            val phone = phone.text.toString().trim()

            val result = checkInput(
                email = login,
                password,
                passwordRepeat,
                nameUser,
                addressDelivery,
                phone
            )

            if (!result) return@setOnClickListener
            mAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener{ task->
                    when{
                        task.isSuccessful ->{
                            mainViewModel.setUser(UserDB(nameUser,phone,login,password,addressDelivery))
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

    private fun checkInput(
        email: String,
        password: String,
        password_repeat: String,
        name: String,
        adoresDelivery: String,
        phone: String
    ): Boolean {
        return if (email.length <= 3 || password.length < 6 || password_repeat.length < 6 || name.length <= 1 ||adoresDelivery.length <=5 ||phone.length<8) {
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