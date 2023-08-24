package com.example.kotlincoroutinesnflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

import kotlinx.coroutines.*
fun testCoroutines()=measureTimeMillis {
    runBlocking {
        for(i in 1..100000) {
            launch {
                delay(10000L)
            }
        }
    }
}

private suspend fun asyncOperation() {
    println("Thread is ${Thread.currentThread().name} AsyncOperation")
    println("Started async operation")
    delay(3000)
    println("Completed async operation")
}

private fun completionHandler() {
    println("Thread is ${Thread.currentThread().name} completionHandler")
    println("Running after async opearion")
}

suspend fun printHelloWorldWithCoroutineScope() = coroutineScope{
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}
fun printHelloWorldWithRunBlocking() = runBlocking{
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}

fun print1To100()=runBlocking {
    launch {
        println("1")
    }

    coroutineScope {
        launch {
            println("2")
        }

        println("3")
    }

    coroutineScope {
        launch {
            println("4")
        }

        println("5")
    }

    launch {
        println("6")
    }

    for (i in 7..100) {
        println(i.toString())
    }

    println("101")
}

fun print1To100WithoutCoroutineScope()=runBlocking {
    launch {
        println("1")
    }

    launch {
        println("2")
    }

    launch {
        println("3")
    }

    for (i in 4..100) {
        println(i.toString())
    }

    println("101")
}

fun testWaitForLaunchJob(){
    runBlocking {
        val job = launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            println("World!")
        }

        val job1 = launch(context = this.coroutineContext, start = CoroutineStart.LAZY, block = {
            delay(1000L)
            println("World100")
        })
        val job2 = launch(context = this.coroutineContext, start = CoroutineStart.DEFAULT, block = {
            delay(1000L)
            println("World200")
        })
        println("Hello")
        job1.join()
        job2.join()
        job.join() // wait until child coroutine completes
        println("Done")
    }

}

// Higher Order functions

//    If the last argument after default parameters is a lambda, you can pass it either as a named argument or outside the parentheses:
fun foo(
    bar: Int = 0,
    baz: Int = 1,
    qux: () -> Unit,
) {
    println("Inside foo method")
    println("bar = $bar")
    println("baz = $baz")
    println("qux = $qux")
}

//You can pass a variable number of arguments (vararg) with names using the spread operator:
fun concatStrings(vararg args:String,seperator:String = " "):String{
    var output = ""
    args.forEachIndexed { index, s ->
        if(index<args.size-1){
            output += s+seperator
        }else{
            output += s
        }
    }

    return output
}

//Single-expression functions
//When the function body consists of a single expression, the curly braces can be omitted and the body specified after an = symbol

fun square(x: Int): Int = x * x
fun triple(x: Int) = x * 3

fun allVariant1(type:String,vararg colors:String){
    colors.forEach {
        println("$it $type")
    }
}

fun allVariant2(vararg colors:String,type:String){
    colors.forEach {
        println("$it $type")
    }
}

/*
* Infix notation
Functions marked with the infix keyword can also be called using the infix notation (omitting the dot and the parentheses for the call).
* Infix functions must meet the following requirements:

They must be member functions or extension functions.

They must have a single parameter.

The parameter must not accept variable number of arguments and must have no default value.
* */

infix fun Int.plus(b:Int)= this+b

///Functions can have generic parameters, which are specified using angle brackets before the function name:
data class Student(
    val firstName : String,
    val lastName : String,
    val title:String,
)

data class Professor(
    val firstName : String,
    val lastName : String,
    val title:String,
    val position:String
)

fun <T> getDisplayName(item: T): String {
    return when(item){
        is Student-> "${item.title} ${item.firstName} ${item.lastName}"
        is Professor->"${item.title} ${item.firstName} ${item.lastName} (${item.position})"
        else->""
    }
}

// Coroutines and channels − tutorial

suspend fun loadData(): Int {
    println("loading...")
    delay(1000L)
    println("loaded!")
    return 42
}

fun main() {
    val items = listOf(1, 2, 3, 4, 5)
    items.fold(0) {
        // When a lambda has parameters, they go first, followed by '->'
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // The last expression in a lambda is considered the return value:
        result
    }
    val joinedToString = items.fold("Elements:") { acc, i -> "$acc $i" }
    print(joinedToString)
    /*runBlocking {
        val deferreds: List<Deferred<Int>> = (1..3).map {
            async {
                delay(1000L * it)
                println("Loading $it")
                it
            }
        }
        val sum = deferreds.awaitAll().sum()
        println("$sum")
    }*/
/*    runBlocking {
        val deferred: Deferred<Int> = async {
            loadData()
        }
        println("waiting...")
        println(deferred.await())
    }*/

    /*val student = Student("Praveen","Yadav","Mr.")
    val professor = Professor("Manoj","Kumar","Dr.","Professor,MCA")

    println(getDisplayName(student))
    println(getDisplayName(professor))*/
    // Two ways to call infix function
/*
    println(1.plus(4))
    print(1 plus 4)
*/

    /*allVariant1("Shirt","Red","Blue","Yellow")
    //allVariant2("Shirt","Red","Blue","Yellow")  // error no value passed for type
    // to all allVariant2 method we have two ways

    allVariant2(*arrayOf("Red","Blue","Yellow"), type = "Shirt")
    allVariant2("Red","Blue","Yellow", type = "Shirt")*/
    /*println(square(5))
    println(triple(5))*/
//    print(concatStrings("I","am","mobile","app","developer"))
    /*// Method 1
    foo() { println("hello") }     // Uses the default value baz = 1
    // Method 2
    foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
    // Method 3
    foo { println("hello") }
*/
//    print(testCoroutines())
    /*runBlocking {
        launch(Dispatchers.Default) {
            asyncOperation()       // this is running in bg thread
            launch(Dispatchers.IO) {
                completionHandler() // this is running in main thread.
            }
        }
    }*/
    /*runBlocking {
        printHelloWorldWithRunBlocking()
        println("Done")
    }*/

    /*
        runBlocking {
            printHelloWorldWithCoroutineScope()
            println("Done")
        }

    */

    /*
        GlobalScope.launch {
            printHelloWorldWithCoroutineScope()
        }
        println("DONE")

        GlobalScope.launch {
            printHelloWorldWithCoroutineScope()
        }
        println("DONE")
    */

//    print1To100()
//    print1To100WithoutCoroutineScope()
//    testWaitForLaunchJob()
}
