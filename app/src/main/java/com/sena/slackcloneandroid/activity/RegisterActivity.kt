package com.sena.slackcloneandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.UserInterface
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.JsonObject
import com.sena.slackcloneandroid.model.User
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    private var loading: AwesomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonLogin.setOnClickListener {
            finish()
        }

        buttonRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (!validateRegister()) return
        showLoading()

        val newUser = User(textUsername.text.toString(), textEmail.text.toString(),
                textPassword.text.toString(), "")

        val data = Data("users", newUser)
        val jsonObject = JsonObject(data)

        val call = ApiClient.getClient()!!.create(UserInterface::class.java)
                .post(jsonObject)

        call.enqueue(object : Callback<JsonObject<User>> {
            override fun onResponse(call: Call<JsonObject<User>>, response: Response<JsonObject<User>>) {
                goneLoading()
                if (response.isSuccessful) {
                    showDialogSuccess()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error in the request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<JsonObject<User>>, t: Throwable) {
                goneLoading()
                t.printStackTrace()
            }
        })
    }

    private fun validateRegister(): Boolean {
        var isValid = true
        val fields = arrayOf(textUsername, textEmail, textPassword, textPasswordRepeat)
        fields.forEach { field ->
            if (field.text.toString().isEmpty()) {
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
        loading = AwesomeProgressDialog(this)
                .setTitle("Register")
                .setMessage("Loading...")
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(false)

        loading!!.show()
    }

    private fun showDialogSuccess() {
        AwesomeSuccessDialog(this)
                .setTitle(R.string.register)
                .setMessage("Register successful...")
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.dialog_ok_button))
                .setPositiveButtonbackgroundColor(R.color.colorPrimary)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick {
                    finish()
                }
                .show()
                .setOnDismissListener {
                    finish()
                }
    }

    private fun goneLoading() {
        if (null != loading) {
            runOnUiThread({
                loading!!.hide()
            })
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            return intent
        }
    }
}
