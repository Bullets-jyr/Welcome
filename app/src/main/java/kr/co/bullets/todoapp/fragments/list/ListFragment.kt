package kr.co.bullets.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.bullets.todoapp.R
import kr.co.bullets.todoapp.data.viewmodel.ToDoViewModel
import kr.co.bullets.todoapp.databinding.FragmentListBinding
import kr.co.bullets.todoapp.fragments.SharedViewModel
import kr.co.bullets.todoapp.fragments.list.adapter.ListAdapter

class ListFragment : Fragment() {
//    private lateinit var binding: FragmentListBinding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentListBinding.inflate(inflater, container, false)
        // Data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        // Setup RecyclerView
        setupRecyclerView()

        // Observe LiveData
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

//        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
//            showEmptyDatabaseViews(it)
//        })

//        binding.floatingActionButton.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_addFragment)
//        }

//        binding.listLayout.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
//        }

        // Set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // Swipe to Delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                // Delete Item
                mToDoViewModel.deleteItem(itemToDelete)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: '${itemToDelete.title}'",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

//    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
//        if (emptyDatabase) {
//            binding.noDataImageView.visibility = View.VISIBLE
//            binding.noDataTextView.visibility = View.VISIBLE
//        } else {
//            binding.noDataImageView.visibility = View.INVISIBLE
//            binding.noDataTextView.visibility = View.INVISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Show AlertDialog to Confirm Removal of All Items from Database Table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove Everything'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}