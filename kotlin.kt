import java.io.File
import java.util.HashMap
import java.util.ArrayList
// Compile: kotlinc kotlin.kt -include-runtime -d korlin.jar
// Run: java -jar kotlin.jar
fun main(args: Array<String>) {

    File("in").listFiles().forEach {
        val inputFilename = it.toString()
        println(inputFilename)
        val inputFileData = File(inputFilename).useLines { it.toList() }

        val stringLength: Long = inputFileData[0].toLong()
        var chars1 = inputFileData[1].toCharArray()
        var chars2 = inputFileData[2].toCharArray()
        var chars1Positions: Map<Long, MutableList<Long>> = HashMap()
        var chars2Positions: Map<Long, MutableList<Long>> = HashMap()
        var positionsMap: Map<Long, Long> = HashMap()

        var steps: Long = 0
        var i: Long = 0

        for (i in stringLength-1..0) {
            chars1Positions.get(chars1[i].toLong()).add(i)
            chars2Positions.get(chars2[i].toLong()).add(i)
        }

        println(inputFilename.replace("in", "out"))
        val outputFileData = File(inputFilename.replace("in", "out")).useLines { it.toList() }
        println(outputFileData[0])
        val outputSteps = outputFileData[0].toLong()

        if (steps == outputSteps) {
            println(inputFilename + " test passed! " + steps + " steps")
        } else {
            println(inputFilename + " test broken! actual: " + steps + " steps but expected: " + outputSteps + " steps")
        }

        //File("lit.out").writeText(steps.toString())
    }
}
