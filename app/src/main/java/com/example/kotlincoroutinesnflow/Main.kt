package com.example.kotlincoroutinesnflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
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
    testWaitForLaunchJob()
}

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

    // Higher Order functions

    
}