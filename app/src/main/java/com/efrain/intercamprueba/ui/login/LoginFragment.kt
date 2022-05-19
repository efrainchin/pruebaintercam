package com.efrain.intercamprueba.ui.login

import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.models.UserModel
import com.efrain.intercamprueba.data.dao.UsersDao
import com.efrain.intercamprueba.databinding.FragmentLoginBinding
import com.efrain.intercamprueba.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject lateinit var usersDao: UsersDao

    private val emails: List<String> = listOf("intercam@gmail.com","intercam01@gmail.com",
        "intercam02@gmail.com","intercam03@gmail.com","intercam04@gmail.com")

    override fun onViewCreated() {
        binding.btnLogin.setOnClickListener {
            validateFields()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?)
            = FragmentLoginBinding.inflate(inflater, container, false)

    private fun validateFields() {
        if(binding.etEmail.text.isNullOrBlank() || binding.etPassword.text.isNullOrBlank()) {
            if (binding.etEmail.text.isNullOrBlank()) {
                binding.ilEmail.isErrorEnabled = true
                binding.ilEmail.error = getString(R.string.text_required_field)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                binding.ilEmail.isErrorEnabled = true
                binding.ilEmail.error = getString(R.string.text_warning_email)
            }
            else {
                binding.ilEmail.isErrorEnabled = false
            }

            if (binding.etPassword.text.isNullOrBlank()) {
                binding.ilPassword.isErrorEnabled = true
                binding.ilPassword.error = getString(R.string.text_required_field)
            } else {
                binding.ilPassword.isErrorEnabled = false
            }
        } else {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(emails.contains(email) && password.length > 5) {
                val pass = encrypt(password, email)
                val user = UserModel(email, pass)
                viewLifecycleOwner.lifecycleScope.launch {
                    usersDao.inserUser(user)
                }
                navController.navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                AlertDialog.Builder(requireContext()).apply {
                    setMessage(getString(R.string.text_warning_email_password))
                    setPositiveButton(getString(R.string.text_ok)) {_,_ ->

                    }
                }.show()
            }
        }
    }

    private fun encrypt(value: String, pass: String): String {
        val secretKey: SecretKeySpec = generateKey(pass)
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val dataBytes: ByteArray = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(dataBytes, Base64.DEFAULT)
    }

    private fun generateKey (pass: String): SecretKeySpec {
        val sha: MessageDigest = MessageDigest.getInstance("SHA-256")
        var key: ByteArray = pass.toByteArray(Charsets.UTF_8)
        key = sha.digest(key)
        return SecretKeySpec(key, "AES")
    }

}