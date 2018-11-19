import java.io.File

fun main(args: Array<String>) {

    File("in").listFiles().forEach {
        val startTime = System.currentTimeMillis()

        val inputFilename = it.toString()
        println("---")
        val inputFileData = File(inputFilename).useLines { it.toList() }

        val stringLength: Long = inputFileData[0].toLong()
        var chars1 = inputFileData[1].toCharArray()
        var chars2 = inputFileData[2].toCharArray()
        var chars1Positions = hashMapOf<Long, MutableList<Long>>()
        var chars2Positions = hashMapOf<Long, MutableList<Long>>()
        var positionsMap = hashMapOf<Long, Long>()

        var steps: Long = 0

        for (i in 0..stringLength-1) {
            var intI = i.toInt()
            var chars1PositionKey = chars1[intI].toLong()
            var chars2PositionKey = chars2[intI].toLong()
            if (chars1Positions.get(chars1PositionKey) == null) {
                chars1Positions.put(chars1PositionKey, mutableListOf<Long>())
            }
            if (chars2Positions.get(chars2PositionKey) == null) {
                chars2Positions.put(chars2PositionKey, mutableListOf<Long>())
            }
            chars1Positions.get(chars1PositionKey)?.add(i)
            chars2Positions.get(chars2PositionKey)?.add(i)
        }

        var char: Char = 'A'
        while (char <= 'Z') {
            var chars1PositionsList = chars1Positions.get(char.toLong())
            var chars2PositionsList = chars2Positions.get(char.toLong())

            if (chars1PositionsList == null || chars2PositionsList == null) {
                char++
                continue;
            }

            for (i in chars1PositionsList.indices) {
                positionsMap.put(chars1PositionsList.get(i), chars2PositionsList.get(i))
            }
            char++
        }

        var zeroCount: Int = 0
        var oddPositions = hashMapOf<Long, Long>()

        while (zeroCount < stringLength) {
            for (i in 0..stringLength) {
                oddPositions.put(i, 0.toLong())
            }
            zeroCount = 0
            for (i in 0 until stringLength) {
                var position = positionsMap[i]

                if (position == null) {
                    return;
                }

                var nextOddPosition = oddPositions.get(position + 1)
                var oddValue = oddPositions.get(position)
                if (oddValue == null || nextOddPosition == null) {
                    return;
                }
                if (position % 2 == 0.toLong()) {
                    steps += nextOddPosition
                } else {
                    oddPositions.put(position, oddValue+1)
                }
                positionsMap.put(i, position/2)
                if (position == 0.toLong()) {
                    ++zeroCount
                }
            }
        }

        val endTime = System.currentTimeMillis()
        val outputFileData = File(inputFilename.replace("in", "out")).useLines { it.toList() }
        val outputSteps = outputFileData[0].toLong()

        // 20 seconds limit
        if (steps == outputSteps && (endTime-startTime)/1000 < 20) {
            println(inputFilename + " test passed! " + steps + " steps")
        } else {
            println(inputFilename + " test broken! actual: " + steps + " steps but expected: " + outputSteps + " steps")
            return;
        }
    }
}