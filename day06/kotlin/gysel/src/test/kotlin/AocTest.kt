package ch.mgysel.aoc

import io.kotlintest.matchers.haveSize
import io.kotlintest.should
import io.kotlintest.shouldBe
import org.junit.Test

class AocTest {

    @Test
    fun `remove duplicates`() {
        parse("345, 228") shouldBe Point(345, 228)
    }

    @Test
    fun `example from instructions`() {
        val points = """1, 1
                |1, 6
                |8, 3
                |3, 4
                |5, 5
                |8, 9""".trimMargin().lines().map(::parse)

        points should haveSize(6)


        val grid = createGrid(points)
        with(grid) {
            topLeft shouldBe Point(1, 1)
            width shouldBe 8
            height shouldBe 9
            size() shouldBe width * height
            targets().toList() should haveSize(6)
        }
    }

}

fun createGrid(points: List<Point>): Grid {
    return Grid(points)
}

class Status(val isPossibleTarget: Boolean) {

}

class Grid(points: List<Point>) {

    val topLeft: Point
    val width: Int
    val height: Int

    private val grid: Map<Point, Status>

    init {
        val minX = points.map(Point::x).min()!!
        val minY = points.map(Point::y).min()!!
        val maxX = points.map(Point::x).max()!!
        val maxY = points.map(Point::y).max()!!
        topLeft = Point(minX, minY)
        width = maxX - minX + 1
        height = maxY - minY + 1

        grid = (minX..maxX).flatMap { x ->
            (minY..maxY).map { y -> Point(x, y) }
        }.map { point ->
            point to Status(points.contains(point))
        }.toMap()

    }

    fun size(): Int = grid.size

    fun stati(): Sequence<Status> = grid.asSequence().map { it.value }

    fun targets(): Sequence<Status> = stati().filter { it.isPossibleTarget }

}