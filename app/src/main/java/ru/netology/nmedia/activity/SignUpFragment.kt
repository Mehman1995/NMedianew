package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.SignUpFragmentBinding
import ru.netology.nmedia.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SignUpFragmentBinding.inflate(inflater, container, false)

        val viewModel: SignUpViewModel by viewModels()

        viewModel.data.observe(viewLifecycleOwner, {
            AppAuth.getInstance().setAuth(
                it.id,
                it.token
            )
            findNavController().navigateUp()
        })

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.errorRegistration)
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry_loading) {
                        viewModel.registrationUser(
                            binding.loginField.editText?.text.toString(),
                            binding.passwordField.editText?.text.toString(),
                            binding.nameField.editText?.text.toString()
                        )
                    }
                    .show()

        }


        binding.signUpButton.setOnClickListener {
            if (binding.passwordField.editText?.text.toString() == binding.repeatPasswordField.editText?.text.toString()) {
                viewModel.registrationUser(
                    binding.loginField.editText?.text.toString(),
                    binding.passwordField.editText?.text.toString(),
                    binding.nameField.editText?.text.toString()
                )
            } else binding.repeatPasswordField.error = getString(R.string.error_pass_mismatch)

        }

        return binding.root
    }
}