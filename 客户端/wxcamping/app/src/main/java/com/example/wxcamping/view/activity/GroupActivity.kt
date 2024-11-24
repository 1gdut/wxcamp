package com.example.wxcamping.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wxcamping.R
import com.example.wxcamping.databinding.ActivityGroupBinding
import com.example.wxcamping.model.Model
import com.example.wxcamping.model.entity.Message
import com.example.wxcamping.utils.SoftHideKeyBoardUtil
import com.example.wxcamping.viewmodel.GroupViewModel
import com.example.wxcamping.view.adapter.ChatRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GroupActivity : AppCompatActivity() {
    private lateinit var viewModel: GroupViewModel
    private lateinit var binding: ActivityGroupBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ChatRecyclerAdapter
    lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group)
        viewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        binding.lifecycleOwner = this
        binding.groupViewModel = viewModel
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = binding.groupRecycler
        SoftHideKeyBoardUtil.assistActivity(this)
        val intent = intent
        val stringExtra = intent.getStringExtra("name")

        binding.groupName.text = stringExtra
        binding.groupEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        binding.groupSend.visibility = View.VISIBLE
                        binding.groupAdd.visibility = View.GONE
                    } else {
                        binding.groupSend.visibility = View.GONE
                        binding.groupAdd.visibility = View.VISIBLE
                    }
                }
            }

        })
        messageList = ArrayList()
        adapter = ChatRecyclerAdapter(messageList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel.message.observe(this) {
            updateUI(it)
            adapter.notifyDataSetChanged()
        }
        binding.groupSend.setOnClickListener {
            val message = binding.groupEdittext.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.sendMessage(message)
                binding.groupEdittext.text.clear()
            }

        }

    }

    private fun updateUI(message: String) {
        if (message.contains(":")) {
            var split = message.split(":")
            if (split[0] == Model.userNickName) {
                messageList.add(Message(Model.userNickName!!, split[1], true))
            } else {
                messageList.add(Message(split[0], split[1], false))
            }
        }

    }


}