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

#include "config.h" /* needed for _GNU_SOURCE on Linux */

#include <jni.h>
#include <stdio.h>

#include <sys/time.h>

/* For getrusage().  Note that on Linux _GNU_SOURCE needs to be defined
 * explicitly before including sys/resource.h otherwise RUSAGE_THREAD won't
 * be available.  The configure script should place _GNU_SOURCE in config.h
 * which is included above.  */
#include <sys/resource.h>

#include "net_appjax_jni_ThreadCPUUsage.h"

JNIEXPORT jobject JNICALL Java_net_appjax_jni_ThreadCPUUsage_getusage
  (JNIEnv *env, jclass cl)
{
  jmethodID constructorID = (*env)->GetMethodID(env, cl, "<init>", "()V");
  
  jfieldID userSecondsID = (*env)->GetFieldID(env, cl, "userSeconds", "J");
  jfieldID userMicrosecondsID = (*env)->GetFieldID(env, cl, "userMicroseconds", "J");
  jfieldID systemSecondsID = (*env)->GetFieldID(env, cl, "systemSeconds", "J");
  jfieldID systemMicrosecondsID = (*env)->GetFieldID(env, cl, "systemMicroseconds", "J");
  
  jobject cpuUsage = (*env)->NewObject(env, cl, constructorID);
  
#if defined(RUSAGE_THREAD)
  int who = RUSAGE_THREAD;
#elif defined(RUSAGE_LWP)
  int who = RUSAGE_LWP;
#else
  #error Neither RUSAGE_THREAD nor RUSAGE_LWP available.  Only Linux (>2.6.26) and Solaris are known to support getrusage() on a per-thread basis.
#endif

  struct rusage usage;
  int ret = getrusage(who, &usage);
  
  (*env)->SetLongField(env, cpuUsage, userSecondsID, usage.ru_utime.tv_sec);
  (*env)->SetLongField(env, cpuUsage, userMicrosecondsID, usage.ru_utime.tv_usec);
  (*env)->SetLongField(env, cpuUsage, systemSecondsID, usage.ru_stime.tv_sec);
  (*env)->SetLongField(env, cpuUsage, systemMicrosecondsID, usage.ru_stime.tv_usec);
  
  return(cpuUsage);
}
