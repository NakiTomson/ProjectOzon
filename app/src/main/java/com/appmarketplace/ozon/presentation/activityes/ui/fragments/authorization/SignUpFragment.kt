package com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appmarketplace.ozon.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {


    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AuthorizationViewModel::class.java)


        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val login = emailLogin.text.toString()
        val password = password.text.toString()
        val passwordRepeat = password2.text.toString()
        val nameUser = name.text.toString()
        val addresDelivery = addresDelivery.text.toString()


        signUp.setOnClickListener {
            val result = checkInput(email = login, password, passwordRepeat, nameUser, addresDelivery)

            if (!result) return@setOnClickListener
            mAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener{ task->
                    when{
                        task.isSuccessful ->{
                            val user = mAuth.currentUser
                            val bundel =Bundle()
                            bundel.putBoolean("isSuccessful",true)
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
        addresDelivery: String
    ): Boolean {
        return if (email.length <= 3 || password.length < 6 || password_repeat.length < 6 || name.length <= 1 ||addresDelivery.length <=5) {
          Toast.makeText(activity, "Пороль 6 символов или Не полный адресс", Toast.LENGTH_SHORT).show()
            false
        } else if (password != password_repeat) {
            Toast.makeText(activity, "Разные Пороли", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}