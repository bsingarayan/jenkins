#!/bin/bash

[[ `./hello.sh s2` = "hello s2!" ]] && (echo "test passed!"; exit 0) || (echo "test failed :-("; exit 1)
