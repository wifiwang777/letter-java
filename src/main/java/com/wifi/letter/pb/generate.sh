#!/bin/bash
cd ../../../../;

protoc --java_out=./ ./com/wifi/letter/pb/message.proto