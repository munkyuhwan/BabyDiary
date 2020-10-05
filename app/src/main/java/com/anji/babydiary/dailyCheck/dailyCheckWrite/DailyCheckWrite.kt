package com.anji.babydiary.dailyCheck.dailyCheckWrite

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.dailyCheck.DailyCheckViewModelFactory
import com.anji.babydiary.dailyCheck.listAdapter.DailyCheckListAdapter
import com.anji.babydiary.dailyCheck.listAdapter.DailyCheckListAdapterViewModelFactory
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.databinding.DailyCheckWriteFragmentBinding
import kotlinx.android.synthetic.main.comment_list_item.view.*

class DailyCheckWrite : Fragment() {


    private lateinit var viewModel: DailyCheckWriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val idx = MyShare.prefs.getLong("idx", 0L)

        Log.e("data","${ Utils.getDate(System.currentTimeMillis(), "YYYY - MM - d E")} ")


        var year = "${ Utils.getDate(System.currentTimeMillis(), "YYYY")}"
        var month = "${ Utils.getDate(System.currentTimeMillis(), "MM")}"
        var date = "${ Utils.getDate(System.currentTimeMillis(), "dd")}"
        var day = "${ Utils.getDate(System.currentTimeMillis(), "E")}"

        when(day) {
            "월" -> {
                day = "MON"
            }
            "화" -> {
                day = "TUE"

            }
            "수" -> {
                day = "WED"

            }
            "목" -> {
                day = "THU"

            }
            "금" -> {
                day = "FRI"

            }
            "토" -> {
                day = "SAT"

            }
            "일" -> {
                day = "SUN"

            }
        }


        if (requireArguments().size() <= 0) {

        }else {
            val arg = arguments?.let {
                DailyCheckWriteArgs.fromBundle(it)
            }

            arg?.let {
                year = it.year.toString()
                month = it.month.toString()
                date = it.date.toString()
                day = it.day.toString()
            }


        }

        val binding = DataBindingUtil.inflate<DailyCheckWriteFragmentBinding>(
            inflater,
            R.layout.daily_check_write_fragment,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner


        val application = requireNotNull(this.activity).application
        val database = DailyCheckDatabase.getInstance(application).database

        val viewModelFactory = DailyCheckWriteViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyCheckWriteViewModel::class.java)

        binding.writeViewModel = viewModel

        viewModel.selectedYear.value = year
        viewModel.selectedMonth.value = month
        viewModel.selectedDate.value = date
        viewModel.selectedDay.value = day


        val listAdapter = DailyCheckListAdapter(true,
            EditClickListener {
                //Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
            },
            DailyCheckDeleteClick {
                Log.e("tag","delete idx: ${it}")
                viewModel.deleteComment(it)
            },
            EditCompleteClickListener {
                viewModel.donTrigger(it)
            },
            requireParentFragment()
            )
        binding.recordList.adapter = listAdapter

        viewModel._dataToday.observe(viewLifecycleOwner, Observer {

            it?.let{
                listAdapter.submitList(it)
                if (it.size > 0) {
                    binding.recordList.background = resources.getDrawable(R.drawable.top_left_corner_white)
                }

            }
        })

        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //mList.remove(viewHolder.layoutPosition)
                //Toast.makeText(requireContext(),"${viewHolder.layoutPosition}", Toast.LENGTH_SHORT).show()
                if (direction == ItemTouchHelper.LEFT) {
                    viewHolder.itemView.editLayer.visibility = View.VISIBLE
                }else {
                    viewHolder.itemView.editLayer.visibility = View.GONE
                }
                //viewModel.deleteComment(viewModel.totalList.value!!.get(viewHolder.layoutPosition).idx)
                listAdapter.notifyItemRemoved(viewHolder.layoutPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recordList)


        binding.dailyCheckBacnBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.selectedMonth.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }
        binding.selectedDate.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }
        binding.selectedDay.setOnClickListener {
            findNavController().navigate(DailyCheckWriteDirections.actionDailyCheckWriteToDailyCheckCalendar22())
        }

        return binding.root
    }


}