package uk.co.zac_h.magicsquares.utils

class Matrix {

    private var matrix: Array<IntArray>

    constructor(i: Int, j: Int) {
        matrix = Array(i) { IntArray(j) { 0 } }
    }

    constructor(matrix: Array<IntArray>) {
        this.matrix = matrix
    }

    fun update(i: Int, j: Int, value: Int) {
        matrix[i][j] = value
    }

    fun get(): Array<IntArray> = matrix

    fun value(i: Int, j: Int): Int = matrix[i][j]

    fun resize(i: Int, j: Int) {
        matrix = Array(i) {IntArray(j) { 0 } }
    }

}