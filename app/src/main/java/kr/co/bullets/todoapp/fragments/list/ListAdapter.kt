package kr.co.bullets.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.bullets.todoapp.R
import kr.co.bullets.todoapp.data.models.Priority
import kr.co.bullets.todoapp.data.models.ToDoData
import kr.co.bullets.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDoData) {
            binding.titleTxt.text = item.title
            binding.descriptionTxt.text = item.description

            when (item.priority) {
                Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
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