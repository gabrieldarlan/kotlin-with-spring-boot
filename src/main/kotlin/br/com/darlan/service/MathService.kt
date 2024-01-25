package br.com.darlan.service

interface MathService {

    fun sum(numberOne: String?, numberTwo: String?): Double
    fun subtraction(numberOne: String?, numberTwo: String?): Double
    fun multiplication(numberOne: String?, numberTwo: String?): Double
    fun division(numberOne: String?, numberTwo: String?): Double
    fun average(numberOne: String?, numberTwo: String?): Double
    fun sqrt(numberOne: String?): Double

}