package com.example.wxcamping.view.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wxcamping.R
import com.example.wxcamping.view.adapter.WxRecyclerViewAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WxFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WxRecyclerViewAdapter
    lateinit var image:List<Int>
    lateinit var name:List<String>
    lateinit var news:List<String>
    lateinit var time:List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wx, container, false)
        recyclerView = view.findViewById(R.id.wx_recyclerview)
        init()
        val layoutManager = LinearLayoutManager(context)
        adapter = WxRecyclerViewAdapter(image,name,news,time)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun init() {
        image = ArrayList()
        name = ArrayList()
        news = ArrayList()
        time = ArrayList()
        for (i in 0..20) {
            (image as ArrayList<Int>).add(R.mipmap.ic_launcher)
            (name as ArrayList<String>).add("群聊$i")
            (news as ArrayList<String>).add("信息信息信息信息$i")
            // 修改时间格式
            val now = LocalDateTime.now()
            val timeAgo = now.minusDays(i.toLong())
            if (ChronoUnit.DAYS.between(timeAgo.toLocalDate(), now.toLocalDate()) == 0.toLong()) {
                // 今天的时间
                val timeOfDay = when {
                    timeAgo.hour < 12 -> "上午"
                    timeAgo.hour < 18 -> "下午"
                    else -> "晚上"
                }
                val formatter = DateTimeFormatter.ofPattern("hh:mm")
                (time as ArrayList<String>).add("$timeOfDay ${timeAgo.format(formatter)}")
            } else {
                // 昨天或更早的时间
                val dateFormatter = DateTimeFormatter.ofPattern("MM月dd日")
                (time as ArrayList<String>).add(timeAgo.format(dateFormatter))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WxFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WxFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}