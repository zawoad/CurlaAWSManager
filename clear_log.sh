#!/bin/bash
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "rm /home/ubuntu/curla.log"
