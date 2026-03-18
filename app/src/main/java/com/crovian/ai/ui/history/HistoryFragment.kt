package com.crovian.ai.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crovian.ai.databinding.FragmentHistoryBinding
import com.crovian.ai.viewmodel.HistoryViewModel
import com.crovian.ai.utils.DateUtils.getCurrentMonthString

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.records.observe(viewLifecycleOwner) { records ->
            // bind records
        }

        viewModel.advances.observe(viewLifecycleOwner) { advances ->
            // bind advances
        }

        viewModel.loadMonthlyData(getCurrentMonthString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
