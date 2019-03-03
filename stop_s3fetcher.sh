#!/bin/bash
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "sh /home/ubuntu/bounceio-fetcher/stop_fetcher.sh"
