package com.sena.slackcloneandroid.dialog

import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.ChannelInterface
import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.JsonObject
import kotlinx.android.synthetic.main.dialog_new_channel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewChannelDialog : DialogFragment() {

    private var loading: AwesomeProgressDialog? = null

    lateinit private var onDismissListener: () -> Unit
    fun setOnDismissListener(onDismissListener: () -> Unit) {
        this.onDismissListener = onDismissListener
    }

    companion object {
        fun newInstance(): NewChannelDialog {
            val newChannelDialog = NewChannelDialog()
            val args = Bundle()
            newChannelDialog.arguments = args
            return newChannelDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_new_channel, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setUpView()
    }

    private fun setUpView() {
        buttonSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        if (!validateRegister()) return
        container.visibility = View.GONE
        showLoading()

        val newChannel = Channel(textName.text.toString(), textDescription.text.toString(),
                checkIsPrivate.isChecked)

        val data = Data("channels", newChannel)
        val jsonObject = JsonObject(data)

        val call = ApiClient.getClient()!!.create(ChannelInterface::class.java)
                .post(jsonObject)

        call.enqueue(object : Callback<JsonObject<Channel>> {
            override fun onResponse(call: Call<JsonObject<Channel>>, response: Response<JsonObject<Channel>>) {
                goneLoading()
                if (response.isSuccessful) {
                    showDialogSuccess()
                } else {
                    Toast.makeText(activity, "Error in the request", Toast.LENGTH_LONG).show()
                    dismiss()
                }
            }

            override fun onFailure(call: Call<JsonObject<Channel>>, t: Throwable) {
                t.printStackTrace()

                Toast.makeText(activity, "Error in the request", Toast.LENGTH_LONG).show()
                goneLoading()
                dismiss()
            }
        })
    }

    private fun validateRegister(): Boolean {
        var isValid = true
        if (textName.text.toString().isEmpty()) {
            isValid = false
            textName.error = "This Field is required"
        }

        return isValid
    }

    private fun showLoading() {
        loading = AwesomeProgressDialog(activity)
                .setTitle("New Channel")
                .setMessage("Loading...")
                .setColoredCircle(R.color.colorPrimary)
                .setCancelable(false)

        loading!!.show()
    }

    private fun goneLoading() {
        if (null != loading) {
            activity.runOnUiThread({
                loading!!.hide()
            })
        }
    }

    private fun showDialogSuccess() {
        AwesomeSuccessDialog(activity)
                .setTitle(R.string.register)
                .setMessage("Channel created successful...")
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.dialog_ok_button))
                .setPositiveButtonbackgroundColor(R.color.colorPrimary)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick {
                    dismiss()
                }
                .show()
                .setOnDismissListener {
                    dismiss()
                }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismissListener()
        super.onDismiss(dialog)
    }
}