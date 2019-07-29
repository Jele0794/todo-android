package com.grow.todolist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.grow.model.Task

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NameInputDialogFragment.NoticeDialogListener {

  private lateinit var adapter: TaskListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    adapter = TaskListAdapter(this, android.R.layout.simple_list_item_2, mutableListOf())
    listView.adapter = adapter
    listView.setOnItemClickListener { parent, _, position, _ ->
      Toast.makeText(this, "item clicked ${parent.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()
    }

    add.setOnClickListener {
      openDialog()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      R.id.action_clear_list -> clearList()
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun openDialog() {
    val dialog = NameInputDialogFragment()
    dialog.show(supportFragmentManager, "missiles")
  }

  override fun onDialogPositiveClick(dialog: DialogFragment, newTask: Task) {
    adapter.add(newTask)
//    names.add(newTask)
  }

  override fun onDialogNegativeClick(dialog: DialogFragment) {
    dialog.dialog.cancel()
  }

  private fun clearList(): Boolean {
    adapter.clear()
    return true
  }
}

class TaskListAdapter(
  private val context: Context,
  private val resource: Int, // TODO: Check how to initialize the view
  private val taskList: MutableList<Task>
) : BaseAdapter() {

  fun add(task: Task) {
    taskList.add(task)
  }

  fun clear() {
    taskList.clear()
    super.notifyDataSetChanged()
  }

  override fun getItem(p0: Int): Task {
    return taskList[p0]
  }

  override fun getItemId(p0: Int): Long {
    return taskList[p0].hashCode().toLong()
  }

  override fun getCount(): Int {
    return taskList.size
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    return if (convertView?.sourceLayoutResId == android.R.layout.simple_list_item_2) {
      setTaskItemView(convertView, position)
    } else {
      setTaskItemView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null), position)
    }
  }

  private fun setTaskItemView(view: View, position: Int): View {
    view.findViewById<TextView>(android.R.id.text1).text = getItem(position).title
    view.findViewById<TextView>(android.R.id.text2).text = getItem(position).description
    return view
  }
}
