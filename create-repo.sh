#!/bin/sh
# Create maven repository.

VERSION=${2:-1.6.6-7}

if [ -z "$1" ]
then
    echo Usage:
    echo "     $0 <path_to_repo_dir> [<VERSION>]"
    exit 1
fi

mvn deploy:deploy-file \
    -Durl=file://$1/repo/ \
    -Dfile=./href-library/target/href-library-${VERSION}.jar \
    -DgroupId=su.svn.href \
    -DartifactId=href-library \
    -Dpackaging=jar \
    -Dversion=${VERSION}
