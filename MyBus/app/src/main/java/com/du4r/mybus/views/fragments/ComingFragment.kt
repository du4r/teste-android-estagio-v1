package com.du4r.mybus.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
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
import com.du4r.mybus.viewmodels.MainViewModel
import com.du4r.mybus.views.adapters.LinesAdapter
import com.du4r.mybus.views.adapters.StoppagesAdapter
import com.google.android.material.textfield.TextInputEditText

class ComingFragment : Fragment() {
    private lateinit var filterBtn: ImageView
    private lateinit var searchBtn: ImageView
    private lateinit var progress: ProgressBar
    private lateinit var searchTextInput: TextInputEditText
    private lateinit var rvComings: RecyclerView
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
        filterBtn = view.findViewById(R.id.filter_btn_coming)
        searchBtn = view.findViewById(R.id.search_btn_coming)
        progress = view.findViewById(R.id.progress_search_coming)
        searchTextInput = view.findViewById(R.id.search_edit_text_coming)

        rvComings = view.findViewById(R.id.rv_search_results_lines_coming)

        rvComings.layoutManager = LinearLayoutManager(context)
        setupBtns()
        return view
    }

    override fun onResume() {
        super.onResume()

        viewModel.forecast.observe(viewLifecycleOwner){

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

    private fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    var linha = ""
    var parada = ""
    fun search() {

        var linha = when(checkLinha?.isChecked){
            true -> linha = searchText
            false -> parada = searchText
            null -> ""
        }

        if (checkLinha?.isChecked == true || checkLinha == null) {
            progress.visibility = View.VISIBLE
            //viewModel.getLinhas(searchText)
            linha = searchText
        } else if (checkParada?.isChecked == true) {
            progress.visibility = View.VISIBLE
            //viewModel.getParadas(searchText)
            parada = searchText
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