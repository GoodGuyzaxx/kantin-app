package uningrat.kantin.helper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import uningrat.kantin.R
import java.util.Calendar

class MonthYearPickerDialog(
    context: Context,
    private val onDateSelected: (Int, Int) -> Unit,
    private val initialYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    private val initialMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(context)

        val dialogView = inflater.inflate(R.layout.dialog_riwayat_filter,null)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)

        // Set up month picker
        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.value = initialMonth
        monthPicker.displayedValues = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        // Set up year picker
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        yearPicker.minValue = currentYear - 100 // 100 years back
        yearPicker.maxValue = currentYear + 100 // 100 years ahead
        yearPicker.value = initialYear

        builder.setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                onDateSelected(monthPicker.value, yearPicker.value)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setTitle("Select Month and Year")

        return builder.create()
    }
}