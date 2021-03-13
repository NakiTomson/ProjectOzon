package com.app.marketPlace.presentation.activities.ui.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.marketPlace.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val navController = findNavController()

        if (mAuth.currentUser != null){
            navController.popBackStack()
        }
        signUp.setOnClickListener {
            navController.navigate(R.id.signUpFragment)
        }
        imageSignUp.setOnClickListener {
            navController.navigate(R.id.signUpFragment)
        }
        signIn.setOnClickListener {
            signIn(mAuth)
        }
        imageSignIn.setOnClickListener {
            signIn(mAuth)
        }
    }


    private fun signIn(mAuth: FirebaseAuth) {
        val email:String = emailLogin.text.toString()
        val passWord:String = password.text.toString()

        if (email.length <= 1 && passWord.length <= 3) {
            Toast.makeText(activity, "Заполните поля", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        val user = mAuth.currentUser
                        val bundle = Bundle()
                        bundle.putBoolean("isSuccessful", true)
                        requireActivity().bottomNavigationView.selectedItemId = R.id.account
                    }
                    else -> {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }
}