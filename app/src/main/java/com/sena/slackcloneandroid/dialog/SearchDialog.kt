package com.sena.slackcloneandroid.dialog

import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.adapter.ChannelsAdapter
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.ChannelInterface
import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.JsonArray
import com.sena.slackcloneandroid.model.User
import kotlinx.android.synthetic.main.dialog_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchDialog : DialogFragment() {

    private var channelsAdapter: ChannelsAdapter? = null

    private var user: Data<User>? = null
    private var channels: List<Data<Channel>> = ArrayList()

    lateinit private var onDismissListener: () -> Unit
    fun setOnDismissListener(onDismissListener: () -> Unit) {
        this.onDismissListener = onDismissListener
    }

    companion object {
        fun newInstance(user: Data<User>?): SearchDialog {
            val searchDialog = SearchDialog()
            val args = Bundle()
            args.putSerializable("user", user)
            searchDialog.arguments = args
            return searchDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments.get("user") as Data<User>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_search, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setUpView()
    }

    private fun setUpView() {
        val layoutManager = LinearLayoutManager(activity)
        listChannels.layoutManager = layoutManager
        listChannels.setHasFixedSize(false)

        channelsAdapter = ChannelsAdapter(channels)
        listChannels.adapter = channelsAdapter
        
        textSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(textSearch.text.toString().length > 3) {
                    getChannels()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        buttonSearch.setOnClickListener {
            getChannels()
        }
    }

    private fun getChannels() {
        val call = ApiClient.getClient(user?.attributes?.token!!)!!.create(ChannelInterface::class.java)
                .search(textSearch.text.toString())

        call.enqueue(object : Callback<JsonArray<Channel>> {
            override fun onResponse(call: Call<JsonArray<Channel>>, response: Response<JsonArray<Channel>>) {
                if (response.isSuccessful) {
                    channels = response.body()!!.data!!
                    channelsAdapter!!.items = channels
                } else {
                    Toast.makeText(activity, "Error in the request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<JsonArray<Channel>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(activity, "Error in the request", Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onDismiss(dialog: DialogInterface?) {
        onDismissListener()
        super.onDismiss(dialog)
    }
}