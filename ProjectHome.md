jniThreadCPUUsage is a Java library to get the amount of user and system CPU time used per thread.  This is done via JNI, which calls getrusage() on the system.  Per-thread getrusage() is only available on Linux (>2.6.26) and Solaris.

One useful application of this is measuring the amount of CPU time used for a servlet request.

getrusage() CPU time reporting is better than using the real time clock because it measures actual CPU time used and disregards times when the thread is idle or waiting (such as when it is waiting for network I/O).

View the [README](http://code.google.com/p/jnithreadcpuusage/source/browse/trunk/README) file for installation and usage instructions.