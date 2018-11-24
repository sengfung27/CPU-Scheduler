using System;
using System.Threading;
using System.IO;
using System.Collections.Generic;

class LockSystem
{
    // Create a new Mutex. The creating thread does not own the mutex.
    private static Mutex mut = new Mutex();
    private const int numThreads = 3;

    static void Main()
    {
        Thread newThread1 = new Thread(() => ThreadProc("new1.dat"));
        newThread1.Name = String.Format("Thread {0}", 1);

        Thread newThread2 = new Thread(() => ThreadProc("new2.dat"));
        newThread2.Name = String.Format("Thread {0}", 2);

        Thread newThread3 = new Thread(() => ThreadProc("new3.dat"));
        newThread3.Name = String.Format("Thread {0}", 3);

        newThread1.Start();
        newThread2.Start();
        newThread3.Start();
        
        // The main thread exits, but the application continues to
        // run until all foreground threads have exited.
    }

    private static void ThreadProc(string subfile)
    {
        UseResource(subfile);
    }

    // This method represents a resource that must be synchronized
    // so that only one thread at a time can enter.
    private static void UseResource(string subfile)
    {
        // Wait until it is safe to enter.
        Console.WriteLine("{0} is requesting the mutex",
                          Thread.CurrentThread.Name);
        mut.WaitOne();

        Console.WriteLine("{0} has entered the critical region",
                          Thread.CurrentThread.Name);

        // Access the Critical Region


        List<int> unsorted_list = new List<int>();

        string path = "datafile.dat";

        // Open the file to read from.
        using (StreamReader sr = new StreamReader(path))
        {
            string s;
            while ((s = sr.ReadLine()) != null)
            {
                unsorted_list.Add(Int32.Parse(s));
            }
        }
        using (StreamReader sr = new StreamReader(subfile))
        {
            string s;
            while ((s = sr.ReadLine()) != null)
            {
                unsorted_list.Add(Int32.Parse(s));
            }
        }

        unsorted_list.Sort();

        // Sorted and write into the main file
        using (var sw = new StreamWriter(path))
        {

            foreach (int value in unsorted_list)
            {
                sw.WriteLine(value);
            }
        }

        Console.WriteLine("{0} is leaving the critical region",
            Thread.CurrentThread.Name);

        // Release the Mutex.
        mut.ReleaseMutex();
        Console.WriteLine("{0} has released the mutex",
            Thread.CurrentThread.Name);
    }
}
