package com.eugens.githubsearch.presentation.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.eugens.githubsearch.databinding.FragmentAuthBinding
import com.eugens.githubsearch.presentation.base.BaseFragment
import com.eugens.githubsearch.presentation.utils.extensions.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment() {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModel()

    companion object {
        fun getInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginEvent.observe(viewLifecycleOwner, Observer {
            when (val content = it.peekContent()) {
                is AuthViewModel.LoginEvent.LoginSuccess -> {
                    Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                }
                is AuthViewModel.LoginEvent.LoginError -> {
                    Toast.makeText(context, content.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}