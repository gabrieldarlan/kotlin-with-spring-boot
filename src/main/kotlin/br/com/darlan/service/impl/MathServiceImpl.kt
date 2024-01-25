package br.com.darlan.service.impl

import br.com.darlan.converter.NumberConverter
import br.com.darlan.exceptions.UnsupportedMathOperatorException
import br.com.darlan.service.MathService
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class MathServiceImpl : MathService {
    override fun sum(numberOne: String?, numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)
    }

    override fun subtraction(numberOne: String?, numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo)
    }

    override fun multiplication(numberOne: String?, numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo)
    }

    override fun division(numberOne: String?, numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        if (NumberConverter.convertToDouble(numberOne) <= 0 || NumberConverter.convertToDouble(numberTwo) <= 0)
            throw Exception("division by zero not not allowed")

        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo)
    }

    override fun average(numberOne: String?, numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        return (NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)) / 2
    }

    override fun sqrt(numberOne: String?): Double {
        if (!NumberConverter.isNumeric(numberOne))
            throw UnsupportedMathOperatorException("Please set a numeric value")

        return sqrt(NumberConverter.convertToDouble(numberOne))
    }
}