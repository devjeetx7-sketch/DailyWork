package com.crovian.ai.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crovian.ai.databinding.FragmentStatsBinding
import com.crovian.ai.viewmodel.StatsViewModel
import com.crovian.ai.utils.DateUtils.getCurrentMonthString

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.records.observe(viewLifecycleOwner) { records ->
            // bind records
        }

        viewModel.loadMonthlyData(getCurrentMonthString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
