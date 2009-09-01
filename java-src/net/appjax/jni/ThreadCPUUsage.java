/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the COPYING file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package net.appjax.jni;

/**
 * Get the user and system CPU time used per-thread via JNI and the system
 * getrusage() call.
 * <p>
 * Synopsis:<br/>
 *   <pre>
 *   ThreadCPUUsage cpuUsage = getCPUUsage();
 *   System.out.println(
 *     "totalUserCPUTime=" + cpuUsage.getTotalUserCPUTime() + ", " +
 *     "totalSystemCPUTime=" + cpuUsage.getTotalSystemCPUTime()
 *   );
 *   </pre>
 * <p>
 * Note that Java needs to be invoked with either a {@code LD_LIBRARY_PATH}
 * environment variable that points to the location of your {@code
 * libThreadCPUUsage.so} file, or you can use {@code -Djava.library.path} on
 * the command line instead.
 * <p>
 * Example: {@code libThreadCPUUsage.so} and {@code threadCPUUsage.jar}
 * installed in {@code /usr/local/jni/lib}.
 * <pre>
 * java -classpath /usr/local/jni/lib/threadCPUUsage.jar -Djava.library.path=/usr/local/jni/lib <YourClassName>
 * </pre>
 * 
 * @author Brian Koehmstedt
 */ 
public class ThreadCPUUsage
{
  private long userSeconds;
  private long userMicroseconds;
  private long systemSeconds;
  private long systemMicroseconds;
  
  private static final native ThreadCPUUsage getusage();
  
  /**
   * Private constructor.
   */
  private ThreadCPUUsage()
  {
  }
  
  /**
   * User CPU seconds used for this thread.
   * Equivalent to {@code struct rusage usage; usage.ru_utime.tv_sec;}.
   *
   * @return A long value of user cpu seconds used.
   */
  public long getUserCPUSeconds()
  {
    return(userSeconds);
  }
  
  /**
   * User CPU microseconds used for this thread.
   * Equivalent to {@code struct rusage usage; usage.ru_utime.tv_usec;}.
   * <p>
   * Note this is <b>not</b> the total user time in microseconds.  It is in
   * addition to getUserCPUSeconds().  Total user CPU time is
   * {@code getUserCPUSeconds() + (getUserCPUMicroseconds()/1000000D)},
   * or the same thing that is returned with {@code getTotalUserCPUTime()}.
   *
   * @return A long value of user cpu microseconds used (this is in addition
   *         to seconds used).
   */
  public long getUserCPUMicroseconds()
  {
    return(userMicroseconds);
  }
  
  /**
   * System CPU seconds used for this thread.
   * Equivalent to {@code struct rusage usage; usage.ru_stime.tv_sec;}.
   *
   * @return A long value of system cpu seconds used.
   */
  public long getSystemCPUSeconds()
  {
    return(systemSeconds);
  }

  /**
   * System CPU microseconds used for this thread.
   * Equivalent to {@code struct rusage usage; usage.ru_stime.tv_usec;}.
   * <p>
   * Note this is <b>not</b> the total user time in microseconds.  It is in
   * addition to getSystemCPUSeconds().  Total system CPU time is
   * {@code getSystemCPUSeconds() + (getSystemCPUMicroseconds()/1000000D)},
   * or the same thing that is returned with {@code getTotalSystemCPUTime()}.
   *
   * @return A long value of system cpu microseconds used (this is in
   *         addition to seconds used).
   */
  public long getSystemCPUMicroseconds()
  {
    return(systemMicroseconds);
  }
  
  /**
   * The total user CPU time used for this thread.  Equivalent to:
   * {@code getUserCPUSeconds() + (getUserCPUMicroseconds()/1000000D)}.
   *
   * @return A double value of user cpu time used.
   */
  public double getTotalUserCPUTime()
  {
    return(userSeconds + (userMicroseconds/1000000D));
  }

  /**
   * The total system CPU time used for this thread.  Equivalent to:
   * {@code getUserCPUSeconds() + (getUserCPUMicroseconds()/1000000D)}.
   *
   * @return A double value of system cpu time used.
   */
  public double getTotalSystemCPUTime()
  {
    return(systemSeconds + (systemMicroseconds/1000000D));
  }
  
  /* ensure native library isn't leaking anything */
  private static void garbageCollectionTest()
  {
    for(int i = 0; i < 30000; i++)
    {
      getCPUUsage();
      if(i % 1000 == 0)
      {
        Runtime.getRuntime().gc();
        System.err.println(
          "memory in use: " + 
          ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000) + "KB"
        );
      }
    }
  }
  
  /**
   * Test method that prints out thread CPU time used to the console.
   */
  public static void main(String[] args)
  {
    ThreadCPUUsage cpuUsage = ThreadCPUUsage.getCPUUsage();
    System.out.println(
      "userCPUSeconds=" + cpuUsage.getUserCPUSeconds() + ", " +
      "userCPUMicroseconds=" + cpuUsage.getUserCPUMicroseconds() + ", " +
      "totalUserCPUTime=" + cpuUsage.getTotalUserCPUTime() + ", " +
      "systemCPUSeconds=" + cpuUsage.getSystemCPUSeconds() + ", " +
      "systemCPUMicroseconds=" + cpuUsage.getSystemCPUMicroseconds() + ", " +
      "totalSystemCPUTime=" + cpuUsage.getTotalSystemCPUTime()
    );
  }
  
  /**
   * Get an instance of {@code ThreadCPUUsage} which will represent the CPU
   * time used for the thread at time of calling.  This uses JNI to call
   * {@code getrusage()} at the system level.
   *
   * @return A {@code ThreadCPUUsage} object containing user and system CPU
   *         time used for the current thread.
   */
  public static final ThreadCPUUsage getCPUUsage()
  {
    ThreadCPUUsage nativeCPUUsage = ThreadCPUUsage.getusage();
    return(nativeCPUUsage);
  }
  
  // load the JNI shared object
  static
  {
    System.loadLibrary("ThreadCPUUsage");
  }
}
