AUTOMAKE_OPTIONS = gnu

SUBDIRS = java-src jni-src

test:
	$(JAVA) -classpath java-src/classes -Djava.library.path=jni-src/.libs -client -Xmx32M -Xcheck:jni net.appjax.jni.ThreadCPUUsage

# this would be more for a source+binary distribution
#localbinsrcdist:
#	$(RM) /tmp/threadCPUUsage.jar
#	$(RM) ../jnithreadcpuusage-$(PACKAGE_VERSION).tar.gz
#	$(RM) -rf ../jnithreadcpuusage-$(PACKAGE_VERSION)
#	$(MKDIR_P) ../jnithreadcpuusage-$(PACKAGE_VERSION)
#	svn checkout https://jnithreadcpuusage.googlecode.com/svn/trunk/ ../jnithreadcpuusage-$(PACKAGE_VERSION) --username bkoehm
#	cd ../jnithreadcpuusage-$(PACKAGE_VERSION); \
#	  ./configure; \
#	  make; \
#	  $(CP) java-src/threadCPUUsage.jar /tmp
#	$(RM) -rf ../jnithreadcpuusage-$(PACKAGE_VERSION)
#	$(MKDIR_P) ../jnithreadcpuusage-$(PACKAGE_VERSION)
#	svn checkout https://jnithreadcpuusage.googlecode.com/svn/trunk/ ../jnithreadcpuusage-$(PACKAGE_VERSION) --username bkoehm
#	$(JAVADOC) -d ../jnithreadcpuusage-$(PACKAGE_VERSION)/apidocs -sourcepath ../jnithreadcpuusage-$(PACKAGE_VERSION)/java-src net.appjax.jni
#	$(MKDIR_P) ../jnithreadcpuusage-$(PACKAGE_VERSION)/lib
#	$(CP) /tmp/threadCPUUsage.jar ../jnithreadcpuusage-$(PACKAGE_VERSION)/lib
#	find ../jnithreadcpuusage-$(PACKAGE_VERSION) -name .svn | xargs $(RM) -rf
#	tar cfz ../jnithreadcpuusage-$(PACKAGE_VERSION).tar.gz ../jnithreadcpuusage-$(PACKAGE_VERSION)
#	$(RM) -rf ../jnithreadcpuusage-$(PACKAGE_VERSION)

localsrcdist:
	$(RM) ../jnithreadcpuusage-$(PACKAGE_VERSION)-src.tar.gz
	$(RM) -rf ../jnithreadcpuusage-$(PACKAGE_VERSION)
	$(MKDIR_P) ../jnithreadcpuusage-$(PACKAGE_VERSION)
	svn checkout https://jnithreadcpuusage.googlecode.com/svn/trunk/ ../jnithreadcpuusage-$(PACKAGE_VERSION) --username bkoehm
	find ../jnithreadcpuusage-$(PACKAGE_VERSION) -name .svn | xargs $(RM) -rf
	tar cfz ../jnithreadcpuusage-$(PACKAGE_VERSION)-src.tar.gz ../jnithreadcpuusage-$(PACKAGE_VERSION)
	$(RM) -rf ../jnithreadcpuusage-$(PACKAGE_VERSION)
