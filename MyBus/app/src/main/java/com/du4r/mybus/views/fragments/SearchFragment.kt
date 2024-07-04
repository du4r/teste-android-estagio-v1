package com.du4r.mybus.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.du4r.mybus.R
import com.du4r.mybus.models.Stoppage
import com.du4r.mybus.viewmodels.MainViewModel
import com.du4r.mybus.views.adapters.LinesAdapter
import com.du4r.mybus.views.adapters.StoppagesAdapter
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {
    private lateinit var filterBtn: ImageView
    private lateinit var searchBtn: ImageView
    private lateinit var progress: ProgressBar
    private lateinit var searchTextInput: TextInputEditText
    private lateinit var rvLines: RecyclerView
    private lateinit var rvStoppages: RecyclerView
    private var searchText = ""
    private var checkLinha: CheckBox? = null
    private var checkParada: CheckBox? = null
    private lateinit var viewModel: MainViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        filterBtn = view.findViewById(R.id.filter_btn)
        searchBtn = view.findViewById(R.id.search_btn)
        progress = view.findViewById(R.id.progress_search)
        searchTextInput = view.findViewById(R.id.search_edit_text)
        rvLines = view.findViewById(R.id.rv_search_results_lines)
        rvStoppages = view.findViewById(R.id.rv_search_results_stoppages)
        rvLines.layoutManager = LinearLayoutManager(context)
        rvStoppages.layoutManager = LinearLayoutManager(context)
        setupBtns()
        return view
    }

    override fun onResume() {
        super.onResume()

        viewModel.lines.observe(viewLifecycleOwner){
            rvLines.adapter = LinesAdapter(it)
            rvStoppages.visibility = View.GONE
            rvLines.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }

        viewModel.stoppages.observe(viewLifecycleOwner){
            rvStoppages.adapter = StoppagesAdapter(it)
            rvLines.visibility = View.GONE
            rvStoppages.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }
    }

    fun setupBtns() {
        filterBtn.setOnClickListener {
            openFiltersDialog()
        }

        searchBtn.setOnClickListener {
            hideKeyBoard()
            searchText = searchTextInput.text.toString()
            search()
        }
    }

    private fun hideKeyBoard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun search() {
        if (checkLinha?.isChecked == true || checkLinha == null) {
            progress.visibility = View.VISIBLE
            viewModel.getLinhas(searchText)
        } else if (checkParada?.isChecked == true) {
            progress.visibility = View.VISIBLE
            viewModel.getParadas(searchText)
        }
    }

    fun openFiltersDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_layout, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialogView.findViewById<Button>(R.id.dialog_button).setOnClickListener {
            dialog.dismiss()
        }

        checkLinha = dialogView.findViewById<CheckBox?>(R.id.check_linha).apply {
            setOnCheckedChangeListener { _, isChecked ->
                checkParada?.isChecked = !(isChecked)
            }
        }
        checkParada = dialogView.findViewById<CheckBox>(R.id.check_parada).apply {
            setOnCheckedChangeListener { _, isChecked ->
                checkLinha?.isChecked = !(isChecked)
            }
        }
        dialog.show()
    }


}