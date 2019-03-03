#!/bin/bash
#ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "java -jar /home/ubuntu/app/curla-url-analyzer.jar $2" &
file="${13}"
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 'sh /home/ubuntu/app/scripts/change_config.sh' $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12}
