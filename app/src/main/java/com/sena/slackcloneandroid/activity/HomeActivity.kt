package com.sena.slackcloneandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.sena.slackcloneandroid.App
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.adapter.ChannelsAdapter
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.ChannelInterface
import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.JsonArray
import com.sena.slackcloneandroid.model.User
import com.sena.slackcloneandroid.util.Utils
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    private var loading: AwesomeProgressDialog? = null

    private var user: User? = null
    private var channels: List<Data<Channel>> = ArrayList()

    private var channelsAdapter: ChannelsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = (application as App).preferences!!.getUser()
        setUpView()
    }

    private fun setUpView() {
        textUsername.text = user!!.username
        textInitials.text = Utils.getInitials(user!!.username!!)

        val layoutManager = LinearLayoutManager(this)
        listChannels.layoutManager = layoutManager
        listChannels.setHasFixedSize(true)

        channelsAdapter = ChannelsAdapter(channels)
        listChannels.adapter = channelsAdapter

        pullToRefresh.setOnRefreshListener {

            getChannels()
        }

        showLoading()
        getChannels()
    }

    private fun getChannels() {
        val call = ApiClient.getClient(user!!.token!!)!!.create(ChannelInterface::class.java).get()

        call.enqueue(object : Callback<JsonArray<Channel>> {
            override fun onResponse(call: Call<JsonArray<Channel>>, response: Response<JsonArray<Channel>>) {
                goneLoading()
                if (response.isSuccessful) {
                    channels = response.body()!!.data!!
                    channelsAdapter!!.items = channels
                } else {
                    Toast.makeText(this@HomeActivity, "Error in the request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<JsonArray<Channel>>, t: Throwable) {
                goneLoading()
                t.printStackTrace()
            }
        })
    }

    private fun showLoading() {
        loading = AwesomeProgressDialog(this)
                .setTitle("Channels")
                .setMessage("Loading...")
                .setColoredCircle(R.color.colorPrimary)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(false)

        loading!!.show()
    }

    private fun goneLoading() {
        runOnUiThread({
            if (null != loading) {
                loading!!.hide()
            }
            pullToRefresh.setRefreshing(false)
        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }
}
