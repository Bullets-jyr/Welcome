package kr.co.bullets.todoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.bullets.todoapp.data.models.ToDoData
import kr.co.bullets.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
//            binding.titleTxt.text = toDoData.title
//            binding.descriptionTxt.text = toDoData.description
//            binding.rowBackground.setOnClickListener {
//                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
////                binding.root.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
//                binding.root.findNavController().navigate(action)
//            }

//            when (toDoData.priority) {
//                Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(
//                    ContextCompat.getColor(
//                        binding.root.context,
//                        R.color.red
//                    )
//                )
//                Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(
//                    ContextCompat.getColor(
//                        binding.root.context,
//                        R.color.yellow
//                    )
//                )
//                Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(
//                    ContextCompat.getColor(
//                        binding.root.context,
//                        R.color.green
//                    )
//                )
//            }
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}