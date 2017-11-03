package com.sena.slackcloneandroid.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.UserInterface
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.Json
import com.sena.slackcloneandroid.model.User
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (!validateRegister()) return
        showLoading()

        val newUser = User(textUsername.text.toString(), textEmail.text.toString(),
                textPassword.text.toString(), "")

        ApiClient.getClient()!!.create(UserInterface::class.java)
                .post(Json(Data("users", newUser)))
    }

    private fun validateRegister(): Boolean {
        var isValid = true
        val fields = arrayOf(textUsername, textEmail, textPassword, textPasswordRepeat)
        fields.forEach { field ->
            if ("" == field.text.toString()) {
                isValid = false
                field.error = "This Field is required"
            }
        }

        if (textPassword.text.toString() != textPasswordRepeat.text.toString()) {
            isValid = false
            textPasswordRepeat.error = "The passwords are different"
        }

        return isValid
    }

    private fun showLoading() {
        dialog = ProgressDialog.show(this, "Loading",
                "Loading. Please wait...", true)
    }
}
