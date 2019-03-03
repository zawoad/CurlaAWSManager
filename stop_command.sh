#!/bin/bash
ssh -o StrictHostKeyChecking=no  -i sz-curla.pem ubuntu@$1 "sh /home/ubuntu/app/scripts/stop_curla.sh"
#ssh -i sz-curla.pem ubuntu@$1 "rm -rf /home/ubuntu/curla/dl/*"
