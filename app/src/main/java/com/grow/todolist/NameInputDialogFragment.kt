package com.grow.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.grow.model.Task
import java.lang.ClassCastException
import java.lang.IllegalStateException

class NameInputDialogFragment : DialogFragment() {

  internal lateinit var listener: NoticeDialogListener

  interface NoticeDialogListener {
    fun onDialogPositiveClick(dialog: DialogFragment, newTask: Task)
    fun onDialogNegativeClick(dialog: DialogFragment)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    try {
      listener = context as NoticeDialogListener
    } catch (e: ClassCastException) {
      throw ClassCastException((context.toString() + " must implement NoticeDialogListener"))
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    super.onCreateDialog(savedInstanceState)
    return activity?.let {
      val builder = AlertDialog.Builder(it)
      val inflater = requireActivity().layoutInflater
      val dialogView = inflater.inflate(R.layout.activity_name_input, null)
      val taskTitle: TextInputEditText = dialogView.findViewById(R.id.task_title)
      val taskDesc: TextInputEditText = dialogView.findViewById(R.id.task_description)

      builder
        .setView(dialogView)
        .setTitle("Add a Task")
        .setPositiveButton(
          R.string.add
        ) { _, _ ->
          listener.onDialogPositiveClick(
            this,
            Task(taskTitle.text.toString(), if (taskDesc.text.toString() != "") taskDesc.text.toString() else null)
          )
        }
        .setNegativeButton(
          android.R.string.cancel
        ) { _, _ -> listener.onDialogNegativeClick(this) }

      builder.create()
    } ?: throw IllegalStateException("Activity cannot be null")
  }
}
