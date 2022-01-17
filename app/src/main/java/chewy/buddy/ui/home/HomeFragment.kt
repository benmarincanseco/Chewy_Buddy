package chewy.buddy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import chewy.buddy.R
import android.util.SparseBooleanArray
import android.widget.EditText

class HomeFragment : Fragment() {

    companion object {
        var tasks = arrayListOf<String>()
    }
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Instantiate all the view Models and buttons
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        val listView = root.findViewById<ListView>(R.id.taskView)
        val add = root.findViewById(R.id.add) as Button
        val delete = root.findViewById(R.id.delete) as Button
        val clear = root.findViewById(R.id.clear) as Button
        val editText = root.findViewById<EditText>(R.id.editText)
        // adapter for the list view
        var adapter = ArrayAdapter<String>(root.context, android.R.layout.simple_list_item_multiple_choice, tasks)
        listView.adapter =  adapter
        //set up listeners for buttons and set up the logic
        add.setOnClickListener {

                if(editText.text.toString()!="")tasks.add(editText.text.toString())
                listView.adapter =  adapter
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        clear.setOnClickListener {

                tasks.clear()
                adapter.notifyDataSetChanged()
            }
        delete.setOnClickListener {
                val position: SparseBooleanArray = listView.checkedItemPositions
                val count = listView.count
                var item = count - 1
                while (item >= 0) {
                    if (position.get(item))
                    {
                        adapter.remove(tasks.get(item))
                    }
                    item--
                }
                position.clear()
                adapter.notifyDataSetChanged()
            }

        })
        return root
    }
}