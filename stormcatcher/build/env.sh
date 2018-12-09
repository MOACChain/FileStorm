#!/bin/sh
#modified to work for SCS client
set -e

if [ ! -f "build/env.sh" ]; then
    echo "$0 must be run from the root of the repository."
    exit 2
fi

# Create fake Go workspace under build if it doesn't exist yet.
workspace="$PWD/build/_workspace"
# Record the root of the repository
root="$PWD"
existing_gopath=$GOPATH
echo $existing_gopath
moacdir="$workspace/src/github.com/innowells"
if [ ! -L "$moacdir/moac-scs" ]; then
    mkdir -p "$moacdir"
    cd "$moacdir"
    echo "Make" $moacdir
    ln -s ../../../../../. moac-scs
    #Add a library path
    ln -s ../../../../../../moac-lib moac-lib
    cd "$root"
fi

# Set up the environment to use the workspace.
GOPATH="$workspace:$PWD/vendor:$existing_gopath"
echo $GOPATH
export GOPATH
echo Set GOPATH $GOPATH
# Run the command inside the workspace.
cd "$moacdir/moac-scs"
PWD="$moacdir/moac-scs"
# Launch the arguments with the configured environment.
exec "$@"
