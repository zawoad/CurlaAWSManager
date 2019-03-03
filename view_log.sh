#!/bin/bash
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "tail -n 50 /home/ubuntu/curla.log"
