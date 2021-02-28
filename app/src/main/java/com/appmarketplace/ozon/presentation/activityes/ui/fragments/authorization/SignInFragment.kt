package com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.appmarketplace.ozon.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment() {


    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AuthorizationViewModel::class.java)

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


    fun signIn(mAuth: FirebaseAuth) {
        val email:String = emailLogin.text.toString()
        val passWord:String = password.text.toString()

        if (email.length <= 1 && passWord.length <= 3) {
            Toast.makeText(activity, "Заполните поля", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener { task->
                when{
                    task.isSuccessful ->{
                        val user = mAuth.currentUser
                        val bundel =Bundle()
                        bundel.putBoolean("isSuccessful",true)
                        requireActivity().bottomNavigationView.selectedItemId = R.id.account
                    }
                    else ->{
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

}