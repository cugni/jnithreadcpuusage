AUTOMAKE_OPTIONS = gnu

SUBDIRS = java-src jni-src

test:
	@JAVA@ -classpath java-src/classes -Djava.library.path=jni-src/.libs -client -Xmx32M -Xcheck:jni net.appjax.jni.ThreadCPUUsage
