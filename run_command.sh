#!/bin/bash
#ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "java -jar /home/ubuntu/app/curla-url-analyzer.jar $2" &
ssh -o StrictHostKeyChecking=no -i sz-curla.pem ubuntu@$1 "sh /home/ubuntu/app/scripts/start_curla.sh $2" &

The availability of a big dataset introduces new challenges in digital
forensics investigations. The existing tools and infrastructures
cannot meet the expected response time when we investigate on
a big dataset. I systematically
analyze the big data forensics domain to explore the challenges
and issues in this forensics paradigm and proposed the first working definition of big data forensics.
 The two different
perspectives of big data forensics that are introduced
in this work can spawn future research works in this area. Moreover, my analysis can help researchers to focus on
specific research sub-problems of the big data forensics problem domain.