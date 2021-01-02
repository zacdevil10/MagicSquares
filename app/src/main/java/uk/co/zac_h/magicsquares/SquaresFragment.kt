package uk.co.zac_h.magicsquares

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_squares.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import uk.co.zac_h.magicsquares.utils.Matrix
import uk.co.zac_h.magicsquares.utils.isEven
import uk.co.zac_h.magicsquares.utils.isOdd
import kotlin.math.pow

class SquaresFragment : Fragment() {

    private lateinit var squaresAdapter: SquaresAdapter
    private var size = 3
    private var squaresMatrix = Matrix(size, size)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_squares, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        squaresAdapter = SquaresAdapter(squaresMatrix)

        squares_recycler.apply {
            layoutManager = GridLayoutManager(context, size)
            setHasFixedSize(true)
            adapter = squaresAdapter
        }

        squares_finish.setOnClickListener {
            val columnTotal = IntArray(size) { 0 }
            squaresMatrix.get().forEach {
                var total = 0
                for (r in 0..it.size.minus(1)) {
                    total += it[r]
                    columnTotal[r] += it[r]
                }
                if (total != squares_total.text.toString().toInt()) {
                    Toast.makeText(context, "Wrong!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            columnTotal.forEach { columnVal ->
                if (columnVal != squares_total.text.toString().toInt()) {
                    Toast.makeText(context, "Wrong!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            Toast.makeText(context, "Solved", Toast.LENGTH_SHORT).show()
        }

        squares_solve.setOnClickListener {
            when {
                size.isOdd() -> solveOdd()
                size.isEven() -> solveEven()
            }
            squaresAdapter.notifyDataSetChanged()
        }

        squares_size.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (squares_size.text.isNotEmpty()) {
                    if (squares_size.text.toString().toInt() in 2..9) {
                        size = squares_size.text.toString().toInt()

                        squaresMatrix.resize(size, size)

                        squares_total.text = ((size * (size.toDouble().pow(2) + 1)) / 2).toInt().toString()
                        squares_recycler.layoutManager = GridLayoutManager(context, size)
                        squaresAdapter.notifyDataSetChanged()
                    }

                    if (squares_size.text.toString().toInt() >= 10) squares_size.setText("9")
                    if (squares_size.text.toString().toInt() <= 1) squares_size.setText("2")

                    squaresAdapter.notifyDataSetChanged()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        squares_size.setText("$size")
    }

    private fun solveOdd() {
        var i = size / 2
        var j = size - 1

        var num = 1

        for (n in 1..(size * size)) {
            if (i == -1 && j == size) {
                i = 0
                j = size - 2
            } else {
                if (i < 0) i = size - 1
                if (j == size) j = 0
            }

            if (squaresMatrix.get()[i][j] != 0) {
                i++
                j -= 2
            }

            squaresMatrix.get()[i][j] = num++

            i--
            j++
        }
    }

    private fun solveEven() {

    }

}
