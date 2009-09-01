#!/bin/sh

rm -rf configure config.status config.log
aclocal && autoheader && autoconf && automake --add-missing --copy
./configure --prefix $HOME/jni
