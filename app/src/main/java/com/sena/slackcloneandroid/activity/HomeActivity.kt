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
import com.sena.slackcloneandroid.api.endpoint.GeneralInterface
import com.sena.slackcloneandroid.api.endpoint.UserInterface
import com.sena.slackcloneandroid.dialog.NewChannelDialog
import com.sena.slackcloneandroid.dialog.SearchDialog
import com.sena.slackcloneandroid.model.*
import com.sena.slackcloneandroid.util.Utils
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private var loading: AwesomeProgressDialog? = null

    private var user: Data<User>? = Data("users", null)
    private var channels: List<Data<Channel>> = ArrayList()

    private var channelsAdapter: ChannelsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user?.attributes = (application as App).preferences!!.getUser()
        setUpView()
    }

    private fun setUpView() {
        textUsername.text = user?.attributes?.username
        textInitials.text = Utils.getInitials(user?.attributes?.username!!)

        buttonLogout.setOnClickListener {
            (application as App).preferences?.setUser(null)
            startActivity(LoginActivity.newIntent(this))
            finish()
        }

        buttonAdd.setOnClickListener {
            val newChannelDialog = NewChannelDialog.newInstance()
            newChannelDialog.show(fragmentManager, "dialog")
            newChannelDialog.setOnDismissListener {
                getChannels()
            }
        }

        buttonSearch.setOnClickListener {
            val searchDialog = SearchDialog.newInstance(user)
            searchDialog.show(fragmentManager, "dialog")
            searchDialog.setOnDismissListener {
                getChannels()
            }
        }

        val layoutManager = LinearLayoutManager(this)
        listChannels.layoutManager = layoutManager
        listChannels.setHasFixedSize(true)

        channelsAdapter = ChannelsAdapter(channels)
        listChannels.adapter = channelsAdapter

        pullToRefresh.setOnRefreshListener {
            getChannels()
        }

        showLoading()
        getUser()
    }

    private fun getUser() {
        val call = ApiClient.getClient(user?.attributes?.token!!)!!.create(UserInterface::class.java).get(user?.attributes?.id!!)

        call.enqueue(object : Callback<JsonObject<User>> {
            override fun onResponse(call: Call<JsonObject<User>>, response: Response<JsonObject<User>>) {
                goneLoading()
                if (response.isSuccessful) {
                    user?.relationships = response.body()?.data?.relationships
                    getChannels()
                } else {
                    Toast.makeText(this@HomeActivity, "Error in the request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<JsonObject<User>>, t: Throwable) {
                t.printStackTrace()

                goneLoading()
                Toast.makeText(this@HomeActivity, "Error in the request", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getChannels() {
        val call = ApiClient.getClient(user?.attributes?.token!!)!!.create(GeneralInterface::class.java)
                .get(user?.relationships?.get("channels")?.links?.related!!)

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
                t.printStackTrace()

                goneLoading()
                Toast.makeText(this@HomeActivity, "Error in the request", Toast.LENGTH_LONG).show()
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
