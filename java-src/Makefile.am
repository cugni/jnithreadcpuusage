AUTOMAKE_OPTIONS = gnu

BUILT_SOURCES = classes/net/appjax/jni/ThreadCPUUsage.class jni_headers
CLEANFILES = threadCPUUsage.jar

classes/net/appjax/jni/ThreadCPUUsage.class: Makefile net/appjax/jni/ThreadCPUUsage.java
	$(JAVAC) -d classes net/appjax/jni/ThreadCPUUsage.java
	
jni_headers: classes/net/appjax/jni/ThreadCPUUsage.class
	$(JAVAH) -d ../jni-src -classpath classes -jni net.appjax.jni.ThreadCPUUsage

all-local: classes/net/appjax/jni/ThreadCPUUsage.class
	$(JAR) cf threadCPUUsage.jar -C classes net

install-data-local:
	test -z "$(DESTDIR)$(libdir)" || ($(MKDIR_P) $(DESTDIR)$(libdir); $(CHMOD) 755 $(DESTDIR)$(libdir))
	$(INSTALL_DATA) threadCPUUsage.jar $(DESTDIR)$(libdir)
	test -z "$(DESTDIR)$(docdir)" || ($(MKDIR_P) $(DESTDIR)$(docdir); $(CHMOD) 755 $(DESTDIR)$(docdir))
	$(JAVADOC) -d $(DESTDIR)$(docdir) net.appjax.jni

clean-local:
	$(RM) -rf classes/net
	$(RM) -rf doc/*
