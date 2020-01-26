package uk.co.zac_h.magicsquares

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import uk.co.zac_h.magicsquares.utils.Matrix
import kotlin.math.pow

class SquaresAdapter(private val matrix: Matrix) :
    RecyclerView.Adapter<SquaresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_squares,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val size = matrix.get().size
        val row = position.div(size)
        val column = position - size.times(row)

        holder.apply {
            if (matrix.get()[(row)][column] != 0) input.setText("${matrix.get()[(row)][column]}")

            input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //if (holder.input.text.isNotEmpty())
                        //matrix.get()[row][column] = holder.input.text.toString().toInt()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }

    override fun getItemCount(): Int = matrix.get().size.toDouble().pow(2).toInt()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val input: EditText = itemView.findViewById(R.id.list_item_input)
    }
}