package com.app.marketPlace.presentation.activities.ui.fragments.authorization

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.marketPlace.R
import com.app.marketPlace.data.db.models.UserDb
import com.app.marketPlace.presentation.activities.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {


    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            mainViewModel.setUser(UserDb(nameUser,phone,login,password,addressDelivery))
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