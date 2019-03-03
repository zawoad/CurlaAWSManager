#!/bin/bash
#ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "java -jar /home/ubuntu/app/curla-url-analyzer.jar $2" &
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "sh /home/ubuntu/bounceio-fetcher/run_fetcher.sh $2 $3 $4 $5" &
